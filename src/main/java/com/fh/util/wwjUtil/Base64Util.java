package com.fh.util.wwjUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {
    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }


    public static void main(String[] as){

        String a = "15833802210";
        try {
           String a1 =  Base64Util.encryptBASE64(a.getBytes());
           System.out.println(a1);
           System.out.println(new String(Base64Util.decryptBASE64(a1)));
        }catch (Exception e){
            e.toString();
        }



    }

}
