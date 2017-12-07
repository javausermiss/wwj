package com.fh.util.wwjUtil;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 萤石云平台工具类
 * @author wjy
 * 2017/11/03
 */

public class CameraUtils {
    private static final String AppKey = "06fb051f38cb43d8b3d18397e006d13d";
    private static final String Secret = "9b544119a2a9dbfd8adf4f263f0dd565";

    public static String getAccessToken() {
        String accessToken = "";
        long expireTime ;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("https://open.ys7.com/api/lapp/token/get?" + "appKey=" + AppKey + "&appSecret=" + Secret);
            httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
            HttpResponse response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                //读返回数据
                String conResult = EntityUtils.toString(response.getEntity());
                JSONObject object = new JSONObject();
                object = object.fromObject(conResult);//将字符串转化为json对象
                String s1 = object.getString("data");
                JSONObject object1 = new JSONObject();
                object1 = object1.fromObject(s1);//将data转化为json对象
                accessToken = object1.getString("accessToken");
                expireTime =(long)object1.get("expireTime");
                String NX = "NX";
                String PX = "PX";
                RedisUtil.getRu().setems("accessToken",accessToken,NX,PX,expireTime);
            } else {
               return RespStatus.fail().toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    /**
     * 获取摄像头在线状态码
     * @param deviceSerial
     * @return
     */
    public static String getCameraState(String deviceSerial){
        String success = "1";
        String fail = "0";
        String  accessToken = getAccessToken();
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpsp = new HttpPost("https://open.ys7.com/api/lapp/device/status/get?" +
                    "deviceSerial=" + deviceSerial + "&accessToken=" + accessToken
            );
            httpsp.addHeader("Content-type", "application/x-www-form-urlencoded");
            HttpResponse responsest = httpclient.execute(httpsp);
            if (responsest.getStatusLine().getStatusCode() == 200) {
                System.out.println("返回200，则发送请求成功，则判摄像头在线");
                return success;
            }else {
                System.out.print("返回其他数据，发送请求失败，则判为不在线");
                return fail;
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }

    public static void main(String[] strings){



    }

}
