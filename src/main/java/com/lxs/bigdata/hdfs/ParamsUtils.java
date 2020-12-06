package com.lxs.bigdata.hdfs;

import java.io.IOException;
import java.util.Properties;

/**
 *  读取配置文件
 *  @author Mr.Li
 * @date 2020/6/22 - 22:41
 */
public class ParamsUtils {

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(ParamsUtils.class.getClassLoader().getResourceAsStream("wc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties(){
        return properties;
    }

    public static void main(String[] args) {
        System.out.println(ParamsUtils.getProperties().getProperty("INPUT_PATH"));
    }
}
