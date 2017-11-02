package com.fh.util.wwjUtil;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;



import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class TestDollStateUtils {

        public static void main(String[] a) {
            String AppKey = "081e953ec0c8413c9c218936062de1dc";
            String Secret = "d094f908d5048ae2c6aebc60e5039916";
            String sss = testA(AppKey, Secret);
            System.out.println(sss);


        }

        public static String testA(String AppKey, String Secret) {

            String strResult = "";
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
                    String accessToken = object1.getString("accessToken");
                    String code = object.getString("code");
                    String msg = object.getString("msg");
                    if (code.equals("200")) {
                        strResult += msg;
                        if (accessToken != null) {
                            try {
                                String deviceSerial = "104647328";
                                HttpClient httpst = new DefaultHttpClient();
                                HttpPost httpsp = new HttpPost("https://open.ys7.com/api/lapp/device/status/get?" +
                                        "deviceSerial=" + deviceSerial + "&accessToken=" + accessToken
                                );
                                httpsp.addHeader("Content-type", "application/x-www-form-urlencoded");
                                HttpResponse responsest = httpst.execute(httpsp);
                                if (responsest.getStatusLine().getStatusCode() == 200) {
                                    System.out.println("获取状态成功");
                                    String conResult1 = EntityUtils.toString(responsest.getEntity());
                                    JSONObject objectst = new JSONObject();
                                    System.out.println("获取状态成功2");
                                    objectst = objectst.fromObject(conResult1);
                                    String statu4 = objectst.getString("data");
                                    System.out.print(statu4);
                                } else if (responsest.getStatusLine().getStatusCode() == 20007) {
                                    System.out.println("设备不在线");
                                } else {
                                    System.out.print(responsest.getStatusLine().getStatusCode());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(accessToken);
                    } else {
                        System.out.println(code);
                        strResult += "发送失败";
                    }
                    System.out.println(conResult);
                } else {
                    String err = response.getStatusLine().getStatusCode() + "";
                    strResult += "发送失败:" + err;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return strResult;

        }


    }



