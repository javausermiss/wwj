package com.fh.util.wwjUtil;

import net.sf.json.JSONObject;

public class RespStatus {
    //0:成功 1：失败

    /**
     * 成功
     *
     * @param return_msg
     * @return
     */
    public static JSONObject success(String return_msg) {
        JSONObject obj = new JSONObject();
        obj.element("return_code", ReturnCode.SUCCESS);
        obj.element("return_msg", return_msg);
        return obj;
    }

    /**
     * 成功
     *
     * @return
     */
    public static JSONObject successs() {
        JSONObject obj = new JSONObject();
        obj.element("code", ReturnCode.SUCCESS);
        obj.element("msg", "success");
        return obj;
    }

    /**
     * 失败
     *
     * @param return_msg
     * @return
     */
    public static JSONObject fail(String return_msg) {
        JSONObject obj = new JSONObject();
        obj.element("code", ReturnCode.FAIL);
        obj.element("msg", return_msg);
        return obj;
    }

    /**
     * 失败
     *
     * @return
     */
    public static JSONObject fail() {
        JSONObject obj = new JSONObject();
        obj.element("code", ReturnCode.FAIL);
        obj.element("msg", "fail");
        return obj;
    }

    /**
     * 异常
     *
     * @param exception
     * @return
     */
    public static JSONObject exception(Exception exception) {
        JSONObject obj = new JSONObject();
        obj.element("code", ReturnCode.EXCE);
        obj.element("msg", exception);
        return obj;


    }

    /**
     * 异常
     *
     * @return
     */

    public static JSONObject exception() {
        JSONObject obj = new JSONObject();
        obj.element("code", ReturnCode.EXCE);
        obj.element("msg", "exception");
        return obj;
    }

    /**
     *自定义返回信息
     * @param return_code
     * @param return_msg
     * @return
     */
    public static JSONObject resp(int return_code, String return_msg) {
        JSONObject obj = new JSONObject();
        obj.element("code", return_code);
        obj.element("msg", return_msg);
        return obj;
    }


}
