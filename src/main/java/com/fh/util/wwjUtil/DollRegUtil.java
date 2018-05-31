package com.fh.util.wwjUtil;

import com.fh.util.MD5;

/**
 * 娃娃机SN验证码生成算法
 *
 * @wjy 2017/11/06
 */
public class DollRegUtil {


    public static String getSNCode(String sn) {
        String sn1 = sn.trim().substring(8, 12);//取倒数第6 5 4 3 位数值
        String sn2 = sn.trim().substring(12, 14);//取倒数第2 1 位数值
        int i1 = Integer.parseInt(sn1);
        int i2 = Integer.parseInt(sn2);
        int sum = i1 + i2;
        String a = Long.toBinaryString(sum);//取二进制
        char[] cc = a.toCharArray();
        for (int j = 1; j < cc.length; j++) {
            if (cc[j] == '1') {
                cc[j] = '0';
            } else {
                cc[j] = '1';
            }
        }
        String mm = String.valueOf(cc);
        int gg = Integer.valueOf(mm, 2);
        String code = Integer.toString(gg);
        return code;

    }

    /**
     * 二次MD5加密取得验证码方法
     *
     * @param sn
     * @return
     */
    public static String getCode(String sn) {
        String[] s = sn.trim().split("\\:");
        String newSn = s[2] + ":" + s[4] + ":" + s[0] + ":" + s[5] + ":" + s[1] + ":" + s[3];
        String md5 = MD5.md5(MD5.md5(newSn));
        System.out.println(md5);
        String code = md5.substring(md5.length() - 6, md5.length());
        System.out.println(code);
        return code;

    }


    public static void main(String[] a) {
        String sn = "尹聪,13687632490,上海市虹口区欧阳路196号";
        String[] s = sn.trim().split("\\,");
        String newSn = s[0];
        String s2 = s[1];
        String s3 = s[2];

        System.out.println(newSn);
        System.out.println(s2);
        System.out.println(s3);
        //B0:A3:51:2E:D8:6C
        String c  =  DollRegUtil.getCode("B0:A3:51:2E:D8:6C");
        System.out.print(c);



    }


}
