package com.fh.util.wwjUtil;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class FaceImageUtil {
    private static final String PATH = "/usr/local/tomcat/webapps/faceImage/";

    public static String downloadImage(String url) throws IOException {
        String faceName="";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet();
        httpget.setURI(URI.create(url));
        CloseableHttpResponse response = httpClient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            byte[] byteArray = EntityUtils.toByteArray(entity);
            faceName = MyUUID.createSessionId();
            FileOutputStream fos = new FileOutputStream(PATH + faceName + ".png");
            fos.write(byteArray);
            fos.flush();
            fos.close();
        }
        response.close();
        httpClient.close();
        return "/"+faceName+".png";
    }


}
