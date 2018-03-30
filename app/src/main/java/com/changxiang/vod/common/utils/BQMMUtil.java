//
//package com.changxiang.vod.common.utils;
//
//import com.melink.bqmmsdk.bean.Emoji;
//import com.melink.bqmmsdk.resourceutil.BQMMConstant;
//import com.melink.bqmmsdk.widget.BQMMMessageText;
//import com.quchangkeji.tosingpk.module.ui.origin.bqmm.BqMessage;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
//public class BQMMUtil {
//
//	public static void showTextContent(BQMMMessageText showBQMM, String inputStr){
//		final JSONArray sendCodes = changCodesFromMixedMsg( inputStr );
////		LogUtils.sysout( "转换成 List<String> sendCodes：" + sendCodes.toString() );
//		BqMessage messagesend = new BqMessage( BqMessage.MSG_TYPE_MIXTURE,
//				BqMessage.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry",
//				"avatar", sendCodes, true, true, new Date() );
////		LogUtils.sysout( "转换成 JSONArray mArray：" + sendCodes.toString() );
////		showBQMM.showMessage(messagesend.getId()+"", BQMMMessageHelper.getMsgCodeString(messagesend.getContentArray()), BQMMMessageText.EMOJITYPE,messagesend.getContentArray());
//		showBQMM.showMessage(messagesend.getContentArray() );
//
//	}
//
//	static JSONArray mArray = null;
//	public static JSONArray changCodesFromMixedMsg(String message) {
////		LogUtils.sysout( "输入参数 message：" + message );
//		ArrayList var1 = new ArrayList();
//		mArray = new JSONArray();
//		int mystart = 0;
//		try {
//			Pattern var2 = Pattern.compile( "\\[([^\\[\\]]+)\\]");
//			Matcher var3 = var2.matcher( message );
////			LogUtils.sysout( "分割后数据 var3：" + var3.toString() );
//			JSONObject itemObj = null;
////            data = new JSONArray();
//			while (var3.find()) {
//				if(mystart<var3.start()){
//					var1.add( message.substring( mystart, var3.start() ) );
//					JSONArray var8 = new JSONArray();
//					var8.put(message.substring( mystart, var3.start() ).toString());
//					var8.put("0");
////                    itemObj = new JSONObject();
////                    itemObj.put(  message.substring( mystart, var3.start() ),"0" );
////                    mArray.put("[\""+message.substring( mystart, var3.start() )+"\","+"\"0\""+"]" );
//				}
//				JSONArray var8 = new JSONArray();
//				var1.add( message.substring( var3.start() + 1, var3.end() - 1 ) );
//				var8.put(message.substring( var3.start() + 1, var3.end() - 1 ).toString());
//				var8.put("2");
////                itemObj = new JSONObject();
////                itemObj.put( message.substring( var3.start() + 1, var3.end() - 1 ),"2" );
////                String str1 ="[\""+message.substring( var3.start() + 1, var3.end() - 1 )+"\","+"\"2\""+"]";
//				mArray.put(var8);
//				mystart = var3.end();
////				LogUtils.sysout( "var3.start()：" + var3.start()+"++++++++var3.end():"+var3.end() );
//			}
//			if(message.length()>mystart){//表示还有内容
//				var1.add( message.substring( mystart, message.length() ) );
//				JSONArray var8 = new JSONArray();
//				var8.put(message.substring( mystart, message.length() ).toString());
//				var8.put("0");
////                itemObj = new JSONObject();
////                itemObj.put(message.substring( mystart, message.length() ),"0" );
////                String str = "[\""+message.substring( mystart, message.length() ).toString()+"\","+"\"0\""+"]";
//				mArray.put(var8);//\"
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mArray;
//	}
//
//
//
//
//
//
//	public static JSONArray getMixedMessageData(List<Object> content, String type) {
//		ArrayList var1 = new ArrayList();
//
//		for(int var2 = 0; var2 < content.size(); ++var2) {
//			JSONArray var3 = new JSONArray();
//			if(content.get(var2) instanceof Emoji) {
//				var3.put(((Emoji)content.get(var2)).getEmoCode());
//				if(BQMMConstant.BQMM_EDITTYPE == "bqmm_cm") {
//					if(((Emoji)content.get(var2)).isEmoji()) {
//						var3.put("1");
//					} else {
//						var3.put("2");
//					}
//				} else {
//					var3.put("1");
//				}
//			} else {
//				var3.put(content.get(var2).toString());
//				var3.put(type);
//			}
//
//			var1.add(var3);
//		}
//
//		return new JSONArray(var1);
//	}
//
//
//}
//
//
