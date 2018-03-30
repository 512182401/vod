package com.changxiang.vod.module.engine.base;

import android.os.Message;

/**
 * 用于ui和其他之间的信息调用
 * 
 * @author admin
 * 
 */
public interface MsgHelper {
	void sendEmpteyMsg(int what);

	void sendMsg(Message msg);
}
