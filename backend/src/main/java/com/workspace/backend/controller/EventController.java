package com.workspace.backend.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.workspace.backend.commom.R;
import com.workspace.backend.entity.GameUser;
import com.workspace.backend.entity.GameUserStateEnum;
import com.workspace.backend.entity.User;
import com.workspace.backend.entity.UserEnum;
import com.workspace.backend.service.CompareService;
import com.workspace.backend.service.GameUserService;
import com.workspace.backend.service.UserService;
import com.workspace.backend.utils.PasswordUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/v1")
public class EventController {

    private GameUserService gameUserService;

    @Autowired
    public void setGameUserService(GameUserService gameUserService) {
        this.gameUserService = gameUserService;
    }

    private boolean isCurrentUser(HttpServletRequest request, String uuid) {
        return uuid != null && uuid.equals(request.getAttribute("authenticatedUserUuid"));
    }

    @PostMapping("/info")
    public R gameUserInfo(@RequestParam String uuid, HttpServletRequest request) {
        if (!isCurrentUser(request, uuid)) {
            return R.error().code(401).message("Unauthorized");
        }
        GameUser gameUser = gameUserService.getOne(new QueryWrapper<GameUser>().eq("user_uuid", uuid));
        if (gameUser == null) {
            return R.error().message("Game user not found");
        }
        return R.ok().data("gameUserInfo", gameUser);
    }

    @PostMapping("/play/start")
    public R playStart(@RequestParam String uuid, HttpServletRequest request) {
        if (!isCurrentUser(request, uuid)) {
            return R.error().code(401).message("Unauthorized");
        }
        GameUser gameUser = gameUserService.getOne(new QueryWrapper<GameUser>().eq("user_uuid", uuid));
        if (gameUser == null) {
            return R.error().message("Game user not found");
        }
        gameUser.setState(GameUserStateEnum.PRACTICE_LEVEL.getStateCode());
        gameUserService.updateById(gameUser);
        return R.ok();
    }

    @PostMapping("/play/stop")
    public R playStop(@RequestParam String uuid, HttpServletRequest request) {
        if (!isCurrentUser(request, uuid)) {
            return R.error().code(401).message("Unauthorized");
        }
        GameUser gameUser = gameUserService.getOne(new QueryWrapper<GameUser>().eq("user_uuid", uuid));
        if (gameUser == null) {
            return R.error().message("Game user not found");
        }
        gameUser.setState(GameUserStateEnum.NOTHING.getStateCode());
        gameUserService.updateById(gameUser);
        return R.ok();
    }

    @PostMapping("/level/call")
    public R call(@RequestParam String uuid, HttpServletRequest request) {
        if (!isCurrentUser(request, uuid)) {
            return R.error().code(401).message("Unauthorized");
        }
        GameUser gameUser = gameUserService.getOne(new QueryWrapper<GameUser>().eq("user_uuid", uuid));
        if (gameUser == null) {
            return R.error().message("Game user not found");
        }
        if (gameUser.getState().equals(GameUserStateEnum.PRACTICE_LEVEL.getStateCode())) {
            gameUser.setEmpiricalValue(gameUser.getEmpiricalValue() + 10);
            if (gameUser.getEmpiricalValue() / gameUser.getLevel() > 10) {
                gameUser.setLevel(gameUser.getLevel() + 1);
                gameUser.setBlood((gameUser.getBlood() + gameUser.getLevel()));
                gameUser.setPhysicalAttack(gameUser.getPhysicalAttack() + gameUser.getLevel());
                gameUser.setEmpiricalValue(0.0);
            }
            gameUserService.updateById(gameUser);
        } else {
            return R.ok().code(203).message("未启动").data("gameUserInfo", gameUser);
        }


        return R.ok().data("gameUserInfo", gameUser);
    }

}
