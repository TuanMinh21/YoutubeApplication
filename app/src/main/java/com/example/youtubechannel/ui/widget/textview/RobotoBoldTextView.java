package com.example.youtubechannel.ui.widget.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.youtubechannel.utils.Utils;

public class RobotoBoldTextView extends AppCompatTextView {
    public RobotoBoldTextView(Context context) {
        super(context);
        setFont();
    }

    public RobotoBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public RobotoBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }


    private void setFont() {
        Typeface textViewTypeface = Utils.getRobotoBoldTypeFace(getContext());
        setTypeface(textViewTypeface);
    }
}
