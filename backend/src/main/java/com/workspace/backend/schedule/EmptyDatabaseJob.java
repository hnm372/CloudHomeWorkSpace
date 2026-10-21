package com.workspace.backend.schedule;

import cn.hutool.core.date.DateTime;
import com.workspace.backend.service.UserLogService;
import com.workspace.backend.service.UserService;
import com.workspace.backend.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class EmptyDatabaseJob {
    private final Logger uniLogger = LogUtils.getUnifiedLogger();


    private final UserService userService = (UserService) ApplicationContextUtils.getBean("userService");
    private final UserLogService userLogService = (UserLogService) ApplicationContextUtils.getBean("userLogService");


    public void user() {
        int userCount = userService.list().size();
//        userService.removeByIds(userService.list());
        uniLogger.info("开始定时清理用户数据， 时间： " + DateTime.now() + "，共清理" + userCount + "条数据。");
    }

    public void userLog() {
        int userLogCount = userLogService.list().size();
//        userLogService.removeByIds(userLogService.list());
        uniLogger.info("开始定时清理用户登录数据， 时间： " + DateTime.now() + "，共清理" + userLogCount + "条数据。");
    }

}
