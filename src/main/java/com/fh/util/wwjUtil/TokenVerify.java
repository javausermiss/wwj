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
    	 String cid = PropertiesUtils.getCurrProperty("api.app.w8sdk.cid");
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


//uid=wx1517989889685161&nickName=%E5%AE%89%E5%90%91%E9%98%B3&imageUrl=http%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FgADIIoI3oMvH6hpdIhw3Z30xNF1Nso4hwc4eVAak3y2GNzdvJN3wXQqfALBFZJaD8kaO7T6www4LgNmeImSCzg%2F132&ctype=W8SDK&channel=H5
    public static void main(String[] a) throws IOException {
// 		SortedMap<String, String> paramsMap=new TreeMap<String, String>();
// 		paramsMap.put("uid", "wx1517989889685161");
// 		paramsMap.put("nickName", "%E5%AE%89%E5%90%91%E9%98%B3");
// 		paramsMap.put("imageUrl", "");
// 		paramsMap.put("ctype", "W8SDK");
// 		paramsMap.put("channel", "H5");
// 		String sign= TokenVerify.verifyForW8sdk(paramsMap); //w8SDK
// 		System.out.println(sign);
    	
    	String cid="aed34f22d80e430a868c083da0e4de07";
    	
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("chid", "20180320171737403000009");
        map.put("order_no", "20180320171737403000009");
        map.put("subject", "s1");
        map.put("cid", "aed34f22d80e430a868c083da0e4de07");
        map.put("amount", "10");
        map.put("money", "10");
        map.put("balance", "10");
        map.put("vmoney", "10");
        map.put("time", "1520578242775");
        map.put("user_id", "f905a833349e4b999ddbea21b3567223");
        map.put("out_trade_no", "20180320171737403000009");
        map.put("trade_status", "SUCCESS");
        map.put("payment_id", "gf_wechat");
        map.put("extra", "test1");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue() == null || entry.getValue().toString().length() == 0) {
                map.put(key, "null");
            }
        }
        
        Map<String, Object> sortedParams = new TreeMap<String, Object>(map);
        Set<Map.Entry<String, Object>> entrys = sortedParams.entrySet();
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, Object> param : entrys) {
            basestring.append(param.getKey()).append('=').append(param.getValue()).append('&');
        }
        basestring.delete(basestring.length() - 1, basestring.length()).append("rcWhucD6efT=");
        System.out.println(basestring.toString());
        String ss = TokenVerify.md5(basestring.toString());
        
        System.out.println("sign_type=MD5&sign="+ss);
    }


}
