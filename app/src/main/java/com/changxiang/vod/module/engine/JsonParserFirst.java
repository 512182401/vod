package com.changxiang.vod.module.engine;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParserFirst {

//	public static List<HotTJ> parser_HotTJ(String json) {
//		List<HotTJ> list = new ArrayList<HotTJ>();
//
//		return list;
//	}

    /**
     * 获取返回码
     *
     * @param json
     * @return
     */
    public static int getRetCode(String json) {
        int code = -1;
//		String msg = null;
        if (json == null || json.isEmpty()) {
            return code;
        }
        try {
            JSONObject obj = new JSONObject(json);
//			JSONObject status = new JSONObject(obj.getString("status"));
//			code = Integer.parseInt(status.getString("code"));
            code = Integer.parseInt(obj.getString("status"));//0和1都是请求成功，至于结果对不对不管

//            if (code > 1 && code < 4) {
//                LogUtils.sysout("2222222222222222222 4.响应代码 提示登录");
////            if (code == 1) {
////                LoginedDialog.loginedcheck(getActivity());
//                LoginedDialog.loginedOpen();
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return code;
        }
    }

    /**
     * 获取返回的文字详细信息
     *
     * @param json
     * @return
     */
    public static String getRetMsg(String json) {
        String msg = "";
        if (json == null || json.isEmpty()) {
            return msg;
        }
        try {
            JSONObject obj = new JSONObject(json);
//			JSONObject status = new JSONObject(obj.getString("status"));
            msg = obj.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return msg;
        }
    }

    /**
     * 获取返回的文字详细信息
     * 如：提交图片
     *
     * @param json
     * @return
     */
    public static String getRetMsgByKey(String json, String key) {
        String msg = "";
        if (json == null || json.isEmpty()) {
            return msg;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject status = new JSONObject(obj.getString("data"));
            msg = status.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return msg;
        }
    }

    /**
     * 获取返回的文字详细信息
     * 如：提交图片
     *
     * @param json
     * @return
     */
    public static int getRetDataByKey(String json, String key) {
        int msg = 0;
        if (json == null || json.isEmpty()) {
            return msg;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject status = new JSONObject(obj.getString("data"));
            msg = status.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return msg;
        }
    }


}
