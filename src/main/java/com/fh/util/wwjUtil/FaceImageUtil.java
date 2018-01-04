package com.fh.util.wwjUtil;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fh.util.FastDFSClient;

public class FaceImageUtil {
   
	/**
	 * 下载用户注册的头像，上传到文件服务器
	 * @param url
	 * @return
	 * @throws IOException
	 */
    public static String downloadImage(String url) throws IOException {
        String faceName="";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet();
        CloseableHttpResponse response=null;
        
        try{
        	//请求远程图片
        	httpget.setURI(URI.create(url));
            response = httpClient.execute(httpget);
            
            //获取文件流
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                byte[] byteArray = EntityUtils.toByteArray(entity);
                faceName=FastDFSClient.uploadFile(byteArray, "default.png"); //上传到文件服务器
            }
        }catch(Exception ex){
        	ex.printStackTrace();
        }finally{
        	   response.close();
               httpClient.close();
        }
        return faceName;
    }
}
