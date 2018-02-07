package com.fh.util.wwjUtil;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fh.util.Logger;
import com.fh.util.PropertiesUtils;

import net.sf.json.JSONObject;

/**
 * 微信登录token效验
 */

public class TokenVerify {
	
	private static Logger logger = Logger.getLogger(TokenVerify.class);
	
    private final static String key = "Pooh4token";

    /**
     * 微8游戏平台
     * @param paramsMap
     * @return
     */
    public static String verifyForW8sdk(SortedMap<String, String> paramsMap) {
    	 String cid ="04BgZOnKvyR8l7DtGRTTDQadKHwg9gEx";
    	 StringBuffer basestring = new StringBuffer();
    	 Map<String, Object> sortedParams = new TreeMap<String, Object>(paramsMap);
         Set<Map.Entry<String, Object>> entrys = sortedParams.entrySet();
         // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
         for (Map.Entry<String, Object> param : entrys) {
             basestring.append(param.getKey()).append('=').append(param.getValue()).append('&');
         }
         basestring.append("appkey=").append(cid);
         logger.info(basestring.toString());
         return TokenVerify.md5(basestring.toString());
    }
    
    public static String verifyForALL(String acctoken) {
        String ckey = PropertiesUtils.getCurrProperty("api.i5.sdk.ckey");
        String cid =  PropertiesUtils.getCurrProperty("api.i5.skd.cid");
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String code = null;
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            String signatrue = md5(TokenVerify.getSignature(timestamp, ckey, cid, acctoken));
            //HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(PropertiesUtils.getCurrProperty("api.app.sdk.url") + "cid=" + cid +
                    "&timestamp=" + timestamp + "&access_token=" + acctoken + "&signatrue=" + signatrue);
            httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                //读返回数据
                String conResult = EntityUtils.toString(response.getEntity());
                JSONObject object = new JSONObject();
                object = object.fromObject(conResult);//将字符串转化为json对象
                code = String.valueOf(object.get("msg"));
                logger.info("verifyForH5 login result code = "+code);
            } else {
                return RespStatus.fail().toString();
            }
            response.close();
            httpClient.close();
            return code;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String verify(String acctoken) {
    	
         String ckey = PropertiesUtils.getCurrProperty("api.app.sdk.ckey");
         String cid =  PropertiesUtils.getCurrProperty("api.app.skd.cid");

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String code = null;
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            String signatrue = md5(TokenVerify.getSignature(timestamp, ckey, cid, acctoken));
            //HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(PropertiesUtils.getCurrProperty("api.app.sdk.url") + "cid=" + cid +
                    "&timestamp=" + timestamp + "&access_token=" + acctoken + "&signatrue=" + signatrue);
            httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                //读返回数据
                String conResult = EntityUtils.toString(response.getEntity());
                JSONObject object = new JSONObject();
                object = object.fromObject(conResult);//将字符串转化为json对象
                code = String.valueOf(object.get("msg"));
            } else {
                return RespStatus.fail().toString();
            }
            response.close();
            httpClient.close();
            return code;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getSignature(String timestamp, String secret, String cid, String access_token) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        HashMap<String, String> h = new HashMap<>();
        h.put("cid", cid);
        h.put("timestamp", timestamp);
        h.put("access_token", access_token);
        Map<String, String> sortedParams = new TreeMap<String, String>(h);
        Set<Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append('=').append(param.getValue()).append('&');
        }
        basestring.delete(basestring.length() - 1, basestring.length()).append(secret);
        return basestring.toString();

    }

    public static String md5(String text) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(text.getBytes("UTF8"));
            return hex(m.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String hex(byte[] bits) {
        StringBuffer sb = new StringBuffer();
        for (byte bt : bits) {
            sb.append(Integer.toHexString((bt & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }


    private static String getSRStoken(Map<String, Object> map) {
        Map<String, Object> sortedParams = new TreeMap<String, Object>(map);
        Set<Entry<String, Object>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Entry<String, Object> param : entrys) {
            basestring.append(param.getKey()).append('=').append(param.getValue()).append('&');
        }
        basestring.append("Key=").append(key);
        return md5(basestring.toString());

    }



    public static void main(String[] a) throws IOException {

   		SortedMap<String, String> paramsMap=new TreeMap<String, String>();
 		paramsMap.put("userId", "wx1505750717983412");
 		paramsMap.put("ctype", "W8SDK");
 		paramsMap.put("channel", "H5");
 		String sign= TokenVerify.verifyForW8sdk(paramsMap); //w8SDK
 		
 		System.out.println(sign);
    }


}
