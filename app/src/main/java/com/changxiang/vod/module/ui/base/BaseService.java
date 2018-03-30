package com.changxiang.vod.module.ui.base;

import android.app.Service;
import android.os.Handler;
import android.os.Message;

public abstract class BaseService extends Service {
    protected Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            handMsg(msg);
        }
    };

    public abstract void handMsg(Message msg);

    public void finishServic() {
        stopSelf();//停止service
    }
}
