package com.fh.util.wwjUtil;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import com.fh.util.MD5;
import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.MessageDigest;

/**
 * 微信登录token效验
 */

public class TokenVerify {
    private final static String ckey = "V6EVHaRqrSkTmnEF";
    private final static String cid = "1106514672";


    public static String verify(String acctoken) {
        String code = null ;
        try {
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            String signatrue = TokenVerify.getSignature(timestamp,ckey,cid,acctoken);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://test.api.hxwolf.com:7002/userVisit?cid="+cid+
            "&timestamp"+timestamp+"&signatrue"+signatrue+"&access_token"+acctoken);
            httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
            HttpResponse response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                //读返回数据
                String conResult = EntityUtils.toString(response.getEntity());
                JSONObject object = new JSONObject();
                object = object.fromObject(conResult);//将字符串转化为json对象
                code =String.valueOf ((int)object.get("code"));
            } else {
                return RespStatus.fail().toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;

    }

    public static String getSignature(String timestamp,String secret,String cid ,String access_token) throws IOException
    {
        // 先将参数以其参数名的字典序升序进行排序
        HashMap<String,String> h = new HashMap<>();
        h.put("cid",cid);
        h.put("timestamp",timestamp);
        h.put("access_token",access_token);
        Map<String, String> sortedParams = new TreeMap<String, String>(h);
        Set<Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append('=').append(param.getValue()).append('&');
        }
        basestring.delete(basestring.length()-1,basestring.length()).append(secret);
        System.out.println("===================="+basestring+"=======================");
        // 使用MD5对待签名串求签
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }
        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }


}
