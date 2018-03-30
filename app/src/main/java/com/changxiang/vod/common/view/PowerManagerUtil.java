package com.changxiang.vod.common.view;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by 15976 on 2016/12/29.
 */

public class PowerManagerUtil {

    private static PowerManager.WakeLock wakeLock;

    public static void keepScreenOn(Context context, boolean on) {
        if (on) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "==KeepScreenOn==");
            wakeLock.acquire();
        } else {
            if (wakeLock != null) {
                wakeLock.release();
                wakeLock = null;
            }
        }
    }
}
