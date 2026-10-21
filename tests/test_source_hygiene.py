"""Fast standard-library checks for dangerous regressions in source releases.

Not a dependency vulnerability scan or a proof of runtime correctness.
"""
from pathlib import Path
import re
import unittest

ROOT = Path(__file__).resolve().parents[1]


class SourceHygieneTests(unittest.TestCase):
    def check_source(self, relative_path, forbidden):
        source = (ROOT / relative_path).read_text(encoding='utf-8')
        for pattern in forbidden:
            self.assertIsNone(re.search(pattern, source, re.IGNORECASE),
                              f"Prohibited pattern in {relative_path}: {pattern}")

    def test_no_untracked_runtime_bundles(self):
        self.assertFalse((ROOT / 'frontend/node_modules').exists())
        self.assertFalse((ROOT / 'frontend/unpackage').exists())

    def test_runtime_secrets_are_not_hardcoded(self):
        for profile in ('dev', 'prod'):
            conf = (ROOT / f'backend/src/main/resources/application-{profile}.yml').read_text()
            for name in ('DB_URL', 'DB_USERNAME', 'DB_PASSWORD', 'APP_JWT_SECRET', 'BAIDU_OCR_API_KEY'):
                self.assertIn('${' + name, conf)
        self.check_source('backend/src/main/java/com/workspace/backend/service/impl/CompareServiceImpl.java',
                          (r'\bAPI_KEY\s*=\s*"', r'\bSECRET_KEY\s*=\s*"'))
        self.check_source('backend/src/main/java/com/workspace/backend/utils/TokenUtils.java',
                          (r'TOKEN_SECRET\s*=\s*"',))

    def test_unsafe_backdoors_removed(self):
        for file in ('backend/src/main/java/com/workspace/backend/controller/PublicController.java',
                     'backend/src/main/java/com/workspace/backend/controller/EventController.java'):
            self.check_source(file, (r'/gadmin', r'/gguser'))
        self.assertFalse((ROOT / 'backend/src/main/java/com/workspace/backend/controller/TestController.java').exists())
        self.check_source('backend/src/main/java/com/workspace/backend/controller/LoginController.java',
                          (r'testCode',))
        self.check_source('backend/src/main/java/com/workspace/backend/BackendApplication.java',
                          (r'CronUtil\.start\(',))

    def test_sample_data_not_exported(self):
        self.check_source('backend/src/main/resources/db/workspace.sql', (r'(?m)^\s*INSERT\s+INTO\s+',))

    def test_frontend_url_not_fixed_to_production_ip(self):
        source = (ROOT / 'frontend/config/request.js').read_text()
        self.assertIn('VUE_APP_API_BASE_URL', source)
        self.assertNotRegex(source, r'\b(?:\d{1,3}\.){3}\d{1,3}\b')

    def test_license_decision_is_not_faked(self):
        self.assertTrue((ROOT / 'docs/PUBLIC_RELEASE_CHECKLIST.md').exists())
        self.assertFalse((ROOT / 'LICENSE').exists(), 'Choose license explicitly before publishing')


if __name__ == '__main__':
    unittest.main()
