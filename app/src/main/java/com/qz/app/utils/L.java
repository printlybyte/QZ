package com.qz.app.utils;

import android.util.Log;

/**
 * Created by win7 on 2016/6/27.
 */
public class L {
    //    private static final boolean KEY_REALEASE = true;
    private static final boolean KEY_REALEASE = false;

    public static void v(String clazz, Object... message) {
        if (!KEY_REALEASE) {
            if (null != clazz && null != message) {
                Log.v(clazz,messageArrayToString(message));
            }
        }
    }

    public static void i(String clazz, Object... message) {
        if (!KEY_REALEASE) {
            if (null != clazz && null != message) {
                Log.i(clazz,messageArrayToString(message));
            }
        }
    }

    public static void d(String clazz, Object... message) {
        if (!KEY_REALEASE) {
            if (null != clazz && null != message) {
                Log.d(clazz,messageArrayToString(message));
            }
        }
    }

    public static void w(String clazz, Object... message) {
        if (!KEY_REALEASE) {
            if (null != clazz && null != message) {
                Log.w(clazz, messageArrayToString(message));
            }
        }
    }

    public static void e(String clazz, Object... message) {
        if (!KEY_REALEASE) {
            if (null != clazz && null != message) {
                Log.e(clazz, messageArrayToString(message));
            }
        }
    }

    public static String messageArrayToString(Object[] message) {
        String messages = "";
        if (null != message) {
            for (int a = 0; a < message.length; a++) {
                messages += ","+message[a];
            }
        }
        return messages;
    }
}
