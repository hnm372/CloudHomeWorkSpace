package com.workspace.backend.commom;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class Utils {
    /**
     * 判断字符串中是否全是数字
     * 利用Java的character.isDigit方法进行处理
     */
    public static boolean checkStrIsNum(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 获取属性名数组
     */
    public static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /* 根据属性名获取属性值
     * */
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * 上传文件
     *
     * @param multiFile      文件
     * @param uploadPath     服务器上要存储文件的路径
     * @param uploadFileName 服务器上要存储的文件的名称
     */
    public static boolean uploadToServer(MultipartFile multiFile, String uploadPath, String uploadFileName) {
        //构建文件对象
        File file = new File(uploadPath);
        //文件目录不存在则递归创建目录
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if (!mkdirs) {
                log.error("创建文件夹异常");
                return false;
            }
        }
        InputStream ins = null;
        FileOutputStream outs = null;
        try {
            //获取文件输入流
            ins = multiFile.getInputStream();
            //构建文件输出流
            outs = new FileOutputStream(uploadPath + uploadFileName);
            int len;
            byte[] bytes = new byte[1024];
            //读取一个bytes的文件内容
            while ((len = ins.read(bytes)) != -1) {
                outs.write(bytes, 0, len);
            }
            outs.close();
            log.info("上传成功：{}", uploadPath + uploadFileName);
            return true;
        } catch (IOException e) {
            log.error("文件上传异常");
            e.printStackTrace();
        } finally {
            try {
                if (outs != null) {
                    outs.close();
                }
                if (ins != null) {
                    ins.close();
                }
            } catch (IOException e) {
                log.error("关闭流异常");
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 新文件上传
     *
     * @param multiFile      文件
     * @param uploadPath     服务器上要存储文件的路径
     * @param uploadFileName 服务器上要存储的文件的名称
     */
    public static boolean newUploadToServer(MultipartFile multiFile, String uploadPath, String uploadFileName) {
        //构建文件对象
        File file = new File(uploadPath);
        //文件目录不存在则递归创建目录
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if (!mkdirs) {
                log.error("创建文件夹异常");
                return false;
            }
        }
        try {
            //获取文件输入流
            InputStream inputStream = multiFile.getInputStream();
            //构建文件输出流
            FileOutputStream outputStream = new FileOutputStream(uploadPath + uploadFileName);
            int copy = FileCopyUtils.copy(inputStream, outputStream);
            log.info("上传成功,文件大小：{}", copy);
            return true;
        } catch (IOException e) {
            log.error("文件上传异常", e);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 下载文件到服务器
     *
     * @param downloadUrl      要下载的文件的地址
     * @param downloadPath     服务器上存储的文件路径
     * @param downloadFileName 服务器上存储的文件名称
     */
    public static boolean downloadToServer(String downloadUrl, String downloadPath, String downloadFileName) {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        boolean flag = false;
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            bis = new BufferedInputStream(connection.getInputStream());
            File file = new File(downloadPath);
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                if (!mkdirs) {
                    log.error("创建文件目录失败");
                    return false;
                }
            }
            String filePathName = downloadPath + File.separator + downloadFileName;
            byte[] buf = new byte[1024];
            int size;
            fos = new FileOutputStream(filePathName);
            while ((size = bis.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
            flag = true;
            log.info("文件下载成功,文件路径[" + filePathName + "]");
        } catch (Exception e) {
            log.error("下载文件异常", e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                log.error("关流异常", e);
                e.printStackTrace();
            }
        }
        return flag;
    }

    /***
     * Object to Map<String, Object>
     * @param object
     * @return
     */
    public static Map<String, Object> objectToMap(Object object) {
        Map<String, Object> dataMap = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                dataMap.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return dataMap;
    }

    public static Boolean isObjectNotEmpty(Object obj) {
        String str = ObjectUtils.toString(obj, "");
        return StringUtils.isNotBlank(str);
    }


    public static void setFieldValueByName(Object obj, String fieldName, Object value) {
        try {
            // 获取obj类的字节文件对象
            Class<?> c = obj.getClass();
            // 获取该类的成员变量
            Field f = c.getDeclaredField(fieldName);
            // 取消语言访问检查
            f.setAccessible(true);
            // 给变量赋值
            f.set(obj, value);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
