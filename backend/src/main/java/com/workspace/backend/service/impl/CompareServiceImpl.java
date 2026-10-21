package com.workspace.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.ocr.AipOcr;
import com.workspace.backend.component.AppProperties;
import com.workspace.backend.service.CompareService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CompareServiceImpl implements CompareService {
    @Autowired
    private AppProperties appProperties;
    private AipOcr aipOcrClient;

    public String ocrImage(List<String> imagePathList) {
        if (aipOcrClient == null) {
            String appId = appProperties.getBaiduOcrAppId();
            String apiKey = appProperties.getBaiduOcrApiKey();
            String secretKey = appProperties.getBaiduOcrSecretKey();
            if (appId == null || appId.isBlank() || apiKey == null || apiKey.isBlank()
                    || secretKey == null || secretKey.isBlank()) {
                throw new IllegalStateException("Baidu OCR credentials are not configured");
            }
            aipOcrClient = new AipOcr(appId, apiKey, secretKey);
            // 可选：设置网络连接参数
            aipOcrClient.setConnectionTimeoutInMillis(2000);
            aipOcrClient.setSocketTimeoutInMillis(60000);
            // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

            // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
            // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
        }

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");
//        options.put("recognize_granularity", "big");
//        options.put("vertexes_location", "true");


        HashMap<String, Object> resMap = new HashMap<>();
        for (String s : imagePathList) {

            JSONObject res = aipOcrClient.basicGeneral(s, options);
            resMap.put("fileName", new java.io.File(s).getName());
            resMap.put("ocrResult", res);


        }

        return  JSON.toJSONString(resMap);

            // 参数为本地图片二进制数组
//            byte[] file = readImageFile(image);
//            res = aipOcrClient.basicGeneral(file, options);
//            System.out.println(res.toString(2));


//            // 通用文字识别, 图片参数为远程url图片
//            JSONObject res = aipOcrClient.basicGeneralUrl(url, options);
//            System.out.println(res.toString(2));
    }


}

