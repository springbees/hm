package com.jacklinsir.hm.common.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * @author linSir
 * @version V1.0
 * @Description: (文件工具类)
 * @Date 2020/1/2 22:03
 */
public class FileUtils {

    /**
     * 通用web文件上传工具类
     * 存储方式需要配置:
     * file.properties文件
     * 规定存储方式是:
     * 文件指定目录+项目指定目录+时间+时间+随机数.文件后缀名
     *
     * @param file
     * @return
     */
    public static String webUploadFile(MultipartFile uploadFile) {

        InputStream fileStream = null;
        try {
            fileStream = FileUtils.class.getClassLoader().getResourceAsStream("file.properties");
            Properties properties = new Properties();
            properties.load(fileStream);
            //获取文件名
            String FileName = uploadFile.getOriginalFilename();
            //获取文件路径
            String filePath = getFilePath(FileName);
            //将路径\\替换成引用路径/
            String url = StringUtils.replace(StringUtils.substringAfterLast(filePath, properties.getProperty("fileParent")), "\\", "/");
            String baseUrl = properties.getProperty("fileIpAddress") + url;
            //==============真实上传文件开始===============
            File newFile = new File(filePath);
            //写入磁盘
            //文件流转移到newFile对象
            uploadFile.transferTo(newFile);
            //==============真实上传文件结束===============
            //返回上传成功图片访问url地址
            return baseUrl;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 保存文件
     *
     * @param file     保存目标文件内容和文件名对象
     * @param pathName 保存在指定文件目录
     * @return
     */
    public static String saveFile(MultipartFile file, String pathName) {
        try {
            //目标文件
            File targetFile = new File(pathName);
            if (targetFile.exists()) {
                //存在直接返回目录路径
                return pathName;
            }
            //判断父目录是否存在，不存在进行创建，
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            //然后将文件放入指定位置
            file.transferTo(targetFile);
            return pathName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param pathname
     * @return
     */
    public static boolean deleteFile(String pathname) {
        File file = new File(pathname);
        if (file.exists()) {
            boolean flag = file.delete();

            if (flag) {
                File[] files = file.getParentFile().listFiles();
                if (files == null || files.length == 0) {
                    file.getParentFile().delete();
                }
            }

            return flag;
        }

        return false;
    }

    /**
     * 将文本写入文件
     *
     * @param value
     * @param path
     */
    public static void saveTextFile(String value, String path) {
        FileWriter writer = null;
        try {
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            writer = new FileWriter(file);
            writer.write(value);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件中文本内容
     *
     * @param path
     * @return
     */
    public static String getText(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        try {
            return getText(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 从输入流中获取文本内容
     *
     * @param inputStream
     * @return
     */
    public static String getText(InputStream inputStream) {
        InputStreamReader isr = null;
        BufferedReader bufferedReader = null;
        try {
            isr = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(isr);
            StringBuilder builder = new StringBuilder();
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                string = string + "\n";
                builder.append(string);
            }

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * 对输入流进行加密
     *
     * @param inputStream
     * @return
     */
    public static String fileMd5(InputStream inputStream) {
        try {
            return DigestUtils.md5DigestAsHex(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 创建指定文件目录
     * 按项目名-年-月-日-文件名
     *
     * @param sourceFileName
     * @return
     */

    //获取文件路径
    private static String getFilePath(String sourceFileName) {
        //指定文件目录 + 项目名
        InputStream fileStream = null;
        try {
            fileStream = FileUtils.class.getClassLoader().getResourceAsStream("file.properties");
            Properties properties = new Properties();
            properties.load(fileStream);
            String baseFolder = properties.getProperty("fileParent") + File.separator + properties.getProperty("FileProject");
            Date nowDate = new Date();
            //拼接文件路径+文件名 yyyy/MM/dd
            String fileFolder = baseFolder + File.separator + new DateTime(nowDate).toString("yyyy")
                    + File.separator + new DateTime(nowDate)
                    .toString("MM") + File.separator + new DateTime(nowDate).toString("dd");
            File file = new File(fileFolder);
            if (!file.isDirectory()) {
                //如果目录不存在则创建多层目录
                file.mkdirs();
            }
            //生成新的文件名
            String fileName = new DateTime(nowDate).toString("yyyyMMddhhmmssSSSS")
                    + RandomUtils.nextInt(100, 999) + "." + StringUtils.substringAfterLast(sourceFileName, ".");
            //返回新的文件路径和文件名
            return fileFolder + File.separator + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileStream != null) {
                try {
                    fileStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }
}
