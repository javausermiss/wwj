package com.fh.util.wwjUtil;

import java.io.InputStreamReader;
import java.util.Properties;

import javax.ws.rs.client.Client;

/**
 * 查询application.properties配置文件参数值
 */
public class ConfigUtil {
    private ConfigUtil() {
    }

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(new InputStreamReader(Client.class.getClassLoader().getResourceAsStream("conf/system.properties"),
                    "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }


    }


    public static String getStr(String key) {
        return prop.get(key.trim()).toString().trim();
    }

    public static long getLong(String key) {
        return Long.parseLong(getStr(key.trim()));
    }

    public static int getInt(String key) {
        return Integer.parseInt(getStr(key.trim()));
    }

    public static boolean getBool(String key) {
        return Boolean.parseBoolean(getStr(key.trim()));
    }


}

