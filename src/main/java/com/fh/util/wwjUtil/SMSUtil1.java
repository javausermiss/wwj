package com.fh.util.wwjUtil;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;


public class SMSUtil1 {
//    public static String sendPost(String url, String param) {
//        PrintWriter out = null;
//        BufferedReader in = null;
//        String result = "";
//        try {
//            URL realUrl = new URL(url);
//            // 打开和URL之间的连接
//            URLConnection conn = realUrl.openConnection();
//            // 设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            // 发送POST请求必须设置如下两行
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            // 获取URLConnection对象对应的输出流
//            out = new PrintWriter(conn.getOutputStream());
//            // 发送请求参数
//            out.print(param);
//            // flush输出流的缓冲
//            out.flush();
//            // 定义BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//        } catch (Exception e) {
//            System.out.println("发送 POST 请求出现异常！"+e);
//            e.printStackTrace();
//        }
//        //使用finally块来关闭输出流、输入流
//        finally{
//            try{
//                if(out!=null){
//                    out.close();
//                }
//                if(in!=null){
//                    in.close();
//                }
//            }
//            catch(IOException ex){
//                ex.printStackTrace();
//            }
//        }
//        return result;
//    }


    //调用方式
    public static void main(String[] args) {

        //短信内容
        try{
            String phones = "18337102021";
            String username="38580643";
            String password="E142620406F41F48B7311561F8B96CD3";
            String content =URLEncoder.encode("【大地在线】您的验证码是23423","UTF-8");
            String postData = "type=send&username="+username+"&password=+"+password+"&gwid=***&mobile="+phones+"&message="+content+"";




        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }


        //拼接参数


        String url="http://jk.106api.cn/smsUTF8.aspx";

        //发送并把结果赋给result,返回一个XML信息,解析xml 信息判断
        //String result=HttpRequest.sendPost(url, postData);

    }




}
