package com.workspace.backend.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.workspace.backend.commom.R;
import com.workspace.backend.commom.Utils;
import com.workspace.backend.entity.User;
import com.workspace.backend.entity.UserLog;
import com.workspace.backend.entity.UserLogEnum;
import com.workspace.backend.service.UserLogService;
import com.workspace.backend.service.UserService;
import com.workspace.backend.utils.TokenUtils;
import com.workspace.backend.utils.PasswordUtils;
import com.workspace.backend.vo.AccountLoginVO;
import com.workspace.backend.vo.CodeLoginVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    private UserService userService;
    private final TokenUtils tokenUtils;

    public LoginController(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/account")
    public R accountLogin(@RequestBody @Validated AccountLoginVO accountLoginVO, HttpSession session) {

        if (!Utils.isObjectNotEmpty(accountLoginVO)) {
            return R.error().message("登录信息不完整！");
        }
        Object verificationCode = session.getAttribute("verifyCode");
        if (verificationCode == null || !accountLoginVO.getKaptcha().equalsIgnoreCase(verificationCode.toString())) {
            return R.error().message("验证码不正确！");
        }
        User user = new User().setUserAccount(accountLoginVO.getUserAccount());
        Wrapper<User> queryWrapper = new QueryWrapper<>(user)
                .orderByDesc("version")
                .orderByDesc("v_date");
        List<User> userList = userService.list(queryWrapper);
        if (userList.size() == 0) {
            return R.error().message("账号不存在！");
        }
        User matchedUser = userList.get(0);
        if (PasswordUtils.matches(accountLoginVO.getPassword(), matchedUser.getPassword())) {
            // Migrate pre-existing plaintext passwords on successful login.
            if (!PasswordUtils.isHashed(matchedUser.getPassword())) {
                matchedUser.setPassword(PasswordUtils.hashPassword(accountLoginVO.getPassword()));
                userService.updateById(matchedUser);
            }
            session.removeAttribute("verifyCode");
            Map<String, Object> map = new HashMap<>();
            map.put("message", "登录成功！");
            map.put("token", tokenUtils.sign(matchedUser, UserLogEnum.ACCOUNT_LOGIN.getCode()));

            return R.ok().data(map);
        } else {
            return R.error().message("密码错误！");
        }
    }

    /** SMS login is deliberately disabled until a real verification provider is implemented. */
    @PostMapping("/code")
    public R msgCodeLogin(@RequestBody @Validated CodeLoginVO ignored) {
        return R.error().message("SMS login is not available");
    }

    @GetMapping("/logout")
    public R logout() {
        return R.ok();
    }
}
