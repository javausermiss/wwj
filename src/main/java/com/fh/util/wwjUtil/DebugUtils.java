package com.fh.util.wwjUtil;

import javax.ws.rs.client.Client;
import java.io.InputStreamReader;
import java.util.Properties;

public class DebugUtils {
    /**
     * 获取配置文件
     * @return
     */
    public static Properties getProperties(){
        try {
            Properties properties = new Properties();
            properties.load(new InputStreamReader(Client.class.getClassLoader().getResourceAsStream("application.properties"), "UTF-8"));
            return properties;
        } catch (Exception e) {
            return null;
        }
    }

}
