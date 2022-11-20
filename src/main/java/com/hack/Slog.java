package com.hack;

import android.util.Log;

public class Slog {
    public static void print(String tag, String format) {
        Log.d(tag, format);
    }

    public static void d(String tag, String format) {
        Log.d(tag, format);
    }

    public static void v(String tag, String format) {
        Log.v(tag, format);
    }

    public static void e(String tag, String format) {
        Log.e(tag, format);
    }

    public static void w(String tag, String format) {
        Log.w(tag, format);
    }

    public static void i(String tag, String format) {
        Log.i(tag, format);
    }

    public static void e(String tag, String msg, Object... format) {
        Log.e(tag, String.format(msg, format));
    }

    public static void w(String tag, String msg, Object... format) {
        Log.w(tag, String.format(msg, format));
    }

    public static void i(String tag, String msg, Object... format) {
        Log.i(tag, String.format(msg, format));
    }

    public static void d(String tag, String msg, Object... format) {
        Log.d(tag, String.format(msg, format));
    }

    public static void v(String tag, String msg, Object... format) {
        Log.v(tag, String.format(msg, format));
    }

    public static boolean iSloggable(String tag, int verbose) {
        if (Features.DEBUG) {
            return true;
        }
        return false;
    }
}
