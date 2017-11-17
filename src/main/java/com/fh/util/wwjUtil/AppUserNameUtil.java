package com.fh.util.wwjUtil;

public class AppUserNameUtil {

    public static String getUserName(String phone){

        String  name=  phone.substring(phone.length()-4,phone.length());
        return  name;
    }

    public static  void main(String[] strings){
        System.out.println(getUserName("18337102021"));

    }


}
