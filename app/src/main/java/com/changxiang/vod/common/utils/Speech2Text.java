
package com.changxiang.vod.common.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.changxiang.vod.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class Speech2Text {
    private static String mEngineType = SpeechConstant.TYPE_CLOUD;
    private static Toast mToast;
    private static HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private static EditText content;//  emoji_edit  输入内容
    private static int ret = 0;

    private static void showTip(final String str) {
        mToast.setText( str );
        mToast.show();
    }


    public static void getSearchVoice(Context ct, EditText edcontent, SpeechRecognizer mIat, SharedPreferences mSharedPreferences, RecognizerDialog mIatDialog) {
        // 移动数据分析，收集开始听写事件
        FlowerCollector.onEvent( ct, "iat_recognize" );
        content = edcontent;
        content.setText( null );// 清空显示内容
        mIatResults.clear();
        // 设置参数
        Speech2Text.setParam( mIat, mSharedPreferences );
        boolean isShowDialog = mSharedPreferences.getBoolean(
                ct.getString( R.string.pref_key_iat_show ), true );
        if (isShowDialog) {
            // 显示听写对话框
            mIatDialog.setListener( mRecognizerDialogListener );
            mIatDialog.show();
            showTip( ct.getString( R.string.text_begin ) );
        } else {
            // 不显示听写对话框
            ret = mIat.startListening( mRecognizerListener );
            if (ret != ErrorCode.SUCCESS) {
                showTip( "听写失败,错误码：" + ret );
            } else {
                showTip( ct.getString( R.string.text_begin ) );
            }
        }
    }


    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d( "TAG", "SpeechRecognizer init() code = " + code );
            if (code != ErrorCode.SUCCESS) {
                showTip( "初始化失败，错误码：" + code );
            }
        }
    };
    /**
     * 听写UI监听器
     */
    private static RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            Speech2Text.printResult( results, mIatResults, content );
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            showTip( error.getPlainDescription( true ) );
        }

    };

    /**
     * 听写监听器。
     */
    public static RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip( "开始说话" );
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            showTip( error.getPlainDescription( true ) );
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip( "结束说话" );
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d( "TAG", results.getResultString() );
            printResult( results, mIatResults, content );

            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip( "当前正在说话，音量大小：" + volume );
            Log.d( "TAG", "返回音频数据：" + data.length );
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };


    public static void printResult(RecognizerResult results, HashMap<String, String> mIatResults, EditText content) {
        String text = JsonParser.parseIatResult( results.getResultString() );

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject( results.getResultString() );
            sn = resultJson.optString( "sn" );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put( sn, text );

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append( mIatResults.get( key ) );
        }

        content.setText( resultBuffer.toString() );
        content.setSelection( content.length() );
    }


    /**
     * 设置参数
     *
     * @return
     */
    public static void setParam(SpeechRecognizer mIat, SharedPreferences mSharedPreferences) {
        // 清空参数
        mIat.setParameter( SpeechConstant.PARAMS, null );

        // 设置听写引擎
        mIat.setParameter( SpeechConstant.ENGINE_TYPE, mEngineType );
        // 设置返回结果格式
        mIat.setParameter( SpeechConstant.RESULT_TYPE, "json" );

        String lag = mSharedPreferences.getString( "iat_language_preference",
                "mandarin" );
        if (lag.equals( "en_us" )) {
            // 设置语言
            mIat.setParameter( SpeechConstant.LANGUAGE, "en_us" );
        } else {
            // 设置语言
            mIat.setParameter( SpeechConstant.LANGUAGE, "zh_cn" );
            // 设置语言区域
            mIat.setParameter( SpeechConstant.ACCENT, lag );
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter( SpeechConstant.VAD_BOS, mSharedPreferences.getString( "iat_vadbos_preference", "4000" ) );

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter( SpeechConstant.VAD_EOS, mSharedPreferences.getString( "iat_vadeos_preference", "1000" ) );

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter( SpeechConstant.ASR_PTT, mSharedPreferences.getString( "iat_punc_preference", "0" ) );

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter( SpeechConstant.AUDIO_FORMAT, "wav" );
        mIat.setParameter( SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav" );
    }

}
