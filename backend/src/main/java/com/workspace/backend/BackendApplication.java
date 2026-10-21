package com.workspace.backend;

import com.workspace.backend.utils.LogUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.workspace.backend.mapper")
public class BackendApplication {
    public static void main(String[] args) {

        Logger logger = LogUtils.getUnifiedLogger();

        SpringApplication.run(BackendApplication.class, args);
        logger.info("swagger: http://localhost:8088/doc.html");
        // Scheduled tasks are disabled until individually reviewed and explicitly enabled.
    }

}
