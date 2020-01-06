package com.jacklinsir.hm.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/5 23:11
 */
@Slf4j
public class WebFileUtils {


    /**
     * 处理文件进行删除
     *
     * @param headImageURL
     * @throws IOException
     */
    public static void disFile(String headImageURL) throws IOException {
        //将添加失败用户的文件进行删除
        String[] split = headImageURL.split("/");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (i == split.length - 1) {
                builder.append(split[i]);
            } else {
                if (i > 3) {
                    builder.append(split[i] + "\\");
                }
            }
        }
        //获取文件存储路径
        Properties properties = new Properties();
        properties.load(ClassUtils.getDefaultClassLoader().getResourceAsStream("file.properties"));
        String targetURL = properties.getProperty("fileParent") + "\\" + properties.getProperty("FileProject") + "\\" + builder.toString();
        log.info("文件输出目录:{}", targetURL);
        FileUtils.deleteFile(targetURL);
    }
}
