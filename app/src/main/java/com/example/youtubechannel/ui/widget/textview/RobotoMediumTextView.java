package com.example.youtubechannel.ui.widget.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.youtubechannel.utils.Utils;

public class RobotoMediumTextView extends AppCompatTextView {
    public RobotoMediumTextView(Context context) {
        super(context);
        setFont();
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }


    private void setFont() {
        Typeface textViewTypeface = Utils.getRobotoMediumTypeFace(getContext());
        setTypeface(textViewTypeface);
    }
}
