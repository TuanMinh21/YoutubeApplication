package com.example.youtubechannel.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.afollestad.materialdialogs.MaterialDialog;

public class Utils {

    private static Typeface robotoBoldTypeFace;
    private static Typeface robotoItalicTypeFace;
    private static Typeface robotoLightTypeFace;
    private static Typeface robotoLightItalicTypeFace;
    private static Typeface robotoMediumTypeFace;
    private static Typeface robotoRegularTypeFace;

    public static MaterialDialog createProgress(Context context, String title) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content("Please wait")
                .progress(true, 0)
                .build();
    }

    public static MaterialDialog createAlertDialog(Context context, String title) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .positiveText("OK")
                .build();
    }

    public static boolean isConnectivityAvailable(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnectedOrConnecting();
    }

    public static int stringToInt(String s) {
        if (StringUtil.isEmpty(s)) {
            return 0;
        }
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

    public static Typeface getRobotoBoldTypeFace(Context context) {
        if (robotoBoldTypeFace == null) {
            robotoBoldTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
        }
        return robotoBoldTypeFace;
    }

    public static Typeface getRobotoItalicTypeFace(Context context) {
        if (robotoItalicTypeFace == null) {
            robotoItalicTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Italic.ttf");
        }
        return robotoItalicTypeFace;
    }

    public static Typeface getRobotoLightTypeFace(Context context) {
        if (robotoLightTypeFace == null) {
            robotoLightTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        }
        return robotoLightTypeFace;
    }

    public static Typeface getRobotoLightItalicTypeFace(Context context) {
        if (robotoLightItalicTypeFace == null) {
            robotoLightItalicTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-LightItalic.ttf");
        }
        return robotoLightItalicTypeFace;
    }

    public static Typeface getRobotoMediumTypeFace(Context context) {
        if (robotoMediumTypeFace == null) {
            robotoMediumTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        }
        return robotoMediumTypeFace;
    }

    public static Typeface getRobotoRegularTypeFace(Context context) {
        if (robotoRegularTypeFace == null) {
            robotoRegularTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        }
        return robotoRegularTypeFace;
    }
}
