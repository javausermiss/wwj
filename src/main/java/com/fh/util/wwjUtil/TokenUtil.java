package com.fh.util.wwjUtil;

import net.sf.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

    /**
     * Token工具类
     */
    public class TokenUtil {

        private final static String key = "7234967990133438";

        /**
         * 加密Token
         *
         * @param jsonObject
         * @return
         * @throws Exception
         */
        private static String encrypt(JSONObject jsonObject) throws Exception {
            return AESUtils.encrypt(jsonObject.toString().trim(), key);
        }

        /**
         * 解密登录Token
         *
         * @param token
         * @return
         */
        public static Map<String, String> decryptToken(String token) {
            Map<String, String> map = new LinkedHashMap<>();
            try {
                String json = AESUtils.decrypt(token.trim(), key);
                JSONObject jsonObject = JSONObject.fromObject(json);
                map.put("id", jsonObject.get("id").toString());
            } catch (Exception e) {
            }
            return map;
        }

        /**
         * 解密ID
         *
         * @param token
         * @return
         */
        public static String getId(String token) {
            String phone = "";
            try {
                token = token.replace(" ", "+");
                String json = AESUtils.decrypt(token.trim(), key);
                JSONObject jsonObject = JSONObject.fromObject(json);
                phone = jsonObject.get("id").toString();
            } catch (Exception e) {
            }
            return phone;
        }

        /**
         * 获取token
         *
         * @param id
         * @return
         */
        public static String getToken(String id) {
            String token = null;
            try {
                token = TokenUtil.encrypt(new JSONObject().element("id", id));
            } catch (Exception e) {
            }
            return token;
        }

    }

