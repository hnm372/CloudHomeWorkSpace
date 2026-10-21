package com.workspace.backend.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.workspace.backend.commom.R;
import com.workspace.backend.commom.RE;
import com.workspace.backend.commom.Utils;
import com.workspace.backend.entity.User;
import com.workspace.backend.entity.UserEnum;
import com.workspace.backend.service.UserService;
import com.workspace.backend.utils.PasswordUtils;
import com.workspace.backend.vo.AccountRegVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {
    private UserService userService;

    @Autowired
    public void setUserService (UserService userService) {
        this.userService = userService;
    }


    //获取登录二维码、放入Token
    @GetMapping(value = "/qrcode")
    public void generateQrcode(HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");

        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        try {
            String uuid = IdUtil.objectId();
            response.setHeader("uuid", uuid);
            QrCodeUtil.generate(uuid, 300, 300, "jpg", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/generatePassword")
    public R generatePassword(@RequestParam int len) {
        if (len < 12 || len > 128) {
            return R.error().message("密码长度必须在12到128位之间");
        }
        return R.ok().data("password", PasswordUtils.getNormalPassword(len));
    }

    //    @ApiOperation(value = "验证码", produces = "image/png")
    @GetMapping("/kaptcha")
    @Operation(summary = "验证码")
    public void kaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(150, 40, 5, 4);
        //图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write(response.getOutputStream());
        response.setContentType("image/png");
        //获取验证码中的文字内容
        String verifyCode = captcha.getCode();
        request.getSession().setAttribute("verifyCode", verifyCode);
    }


    @PostMapping("/reg")
    public R register(@RequestBody @Validated AccountRegVO accountRegVO, HttpSession session) {
        if (!Utils.isObjectNotEmpty(accountRegVO)) {
            return R.error().message("注册信息不完整！");
        }
        Object verificationCode = session.getAttribute("verifyCode");
        if (verificationCode == null || !accountRegVO.getKaptcha().equalsIgnoreCase(verificationCode.toString())) {
            return R.error().message("验证码不正确！");
        }

        if (!PasswordUtils.passwordCheckV2(accountRegVO.getPassword())) {
            return R.error().message("密码中必须包含字母、数字、特殊字符，至少8个字符，最多16个字符。");
        }
        Wrapper<User> queryWrapper = new QueryWrapper<>(new User().setUserAccount(accountRegVO.getUserAccount()));

        List<User> user = userService.list(queryWrapper);
        if (user.size() > 0) {
            return R.error().message("账号已存在！");
        }

        session.removeAttribute("verifyCode");
        LocalDateTime localDateTime = LocalDateTime.now();
        User newUser = new User()
                .setUuid(IdUtil.objectId())
                .setUserAccount(accountRegVO.getUserAccount())
                .setPassword(PasswordUtils.hashPassword(accountRegVO.getPassword()))
                .setNickName(accountRegVO.getNickName())
                .setEmail(accountRegVO.getEmail())
                .setAvatarUrl(accountRegVO.getAvatarUrl())
                .setVip(1)
                .setVersion(0)
                .setRegDate(localDateTime)
                .setVDate(localDateTime);
        try {
            userService.save(newUser);
        } catch (Exception e) {
            return R.error().code(RE.INTERNAL_ERROR.getCode()).message("注册失败，因为：" + e);
        }
        return R.ok().message("注册成功！");
    }
}
