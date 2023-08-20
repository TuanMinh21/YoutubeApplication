package com.example.youtubechannel.utils;

import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.youtubechannel.R;

import java.io.File;

public class GlideUtils {

    public static void loadUrl(String url, ImageView imageView) {
        if (StringUtil.isEmpty(url)) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.drawable.img_no_photo)
                .dontAnimate()
                .into(imageView);
    }

    public static void loadFile(File file, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(file)
                .into(imageView);
    }

    public static void loadBase64(String base64, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(Base64.decode(base64, Base64.DEFAULT))
                .into(imageView);
    }
}