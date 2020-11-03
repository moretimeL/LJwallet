package com.liu.ljwallet.layout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.liu.ljwallet.R;

public class IndexTitleBar extends LinearLayout{


    public IndexTitleBar(Context context) {
        super(context);
    }

    public IndexTitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.index_title_bar, this);
        ImageView menu = findViewById(R.id.indexMenu);
        ImageView getQR = findViewById(R.id.getQR);
        getQR.setOnClickListener(e->{
            // TODO 呼起二维码
        });
    }

    public IndexTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public IndexTitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
