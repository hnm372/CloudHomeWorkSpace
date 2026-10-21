package com.workspace.backend.controller;

import com.workspace.backend.component.AppProperties;
import com.workspace.backend.service.CompareService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private CompareService compareService;


    public Pair<Boolean, String> checkFile(MultipartFile multiFile) {
        if (multiFile.isEmpty()) {
            return Pair.of(false, "文件为空");
        }
        //获取
        String filename = multiFile.getOriginalFilename();
        String contentType = multiFile.getContentType();
        if (StringUtils.isBlank(filename)) {
            return Pair.of(false, "文件名为空");
        }
        long size = multiFile.getSize();//字节
        log.info("收到的请求文件信息：原生文件名：{},文件类型：{},文件大小：{}", filename, contentType, size);
        //获取文件后缀
        int dot = filename.lastIndexOf(".");
        if (dot < 0) {
            return Pair.of(false, "文件类型不受支持");
        }
        String suffix = filename.substring(dot).toLowerCase(java.util.Locale.ROOT);
        //判断配置的文件列表里是否支持该文件类型
        if (!ArrayUtils.contains(appProperties.getFileTypeArray(), suffix)) {
            return Pair.of(false, "不支持该类型文件上传");
        }
        double fileSize = size / 1024.0;//单位kb
        if (fileSize > appProperties.getMaxFileSize()) {
            return Pair.of(false, "文件大小超过限制");
        }

        if (suffix.equals(".doc")) {
            return Pair.of(true, "DOC");
        } else if (suffix.equals(".docx")) {
            return Pair.of(true, "DOCX");
        }
        return Pair.of(true, "验证通过");
    }

    @PostMapping(value = "/download")
    public void downloadToClient(HttpServletResponse response, @RequestParam("fileName") String fileName)
            throws IOException {
        if (fileName == null || fileName.isBlank() || fileName.contains("/") || fileName.contains("\\")
                || ".".equals(fileName) || "..".equals(fileName)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid file name");
            return;
        }
        java.nio.file.Path base = Paths.get(appProperties.getDownloadPath()).toAbsolutePath().normalize();
        java.nio.file.Path path = base.resolve(fileName).normalize();
        if (!path.startsWith(base) || !Files.isRegularFile(path) || !path.toRealPath().startsWith(base.toRealPath())) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''"
                + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        Files.copy(path, response.getOutputStream());
    }
}
