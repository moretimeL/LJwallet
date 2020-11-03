package com.liu.ljwallet.layout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.liu.ljwallet.R;
import com.liu.ljwallet.enums.ActivityEnum;

public class TitleBar extends LinearLayout{


    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.title_bar, this);
        Button backButton = findViewById(R.id.backButton);
        TextView titleView = findViewById(R.id.titleView);
        if (context.getClass().getSimpleName().equals("CreateWalletActivity")){
            titleView.setText("创建钱包");
        }
        if (context.getClass().getSimpleName().equals("ImportWalletActivity")){
            titleView.setText("导入钱包");
        }
        if (context.getClass().getSimpleName().equals("BackUpSeedActivity")){
            titleView.setText("备份助记词");
        }
        if (context.getClass().getSimpleName().equals("ConfirmSeedActivity")){
            titleView.setText("确认助记词");
        }
        backButton.setOnClickListener(e->{
            ((Activity) context).finish();
        });
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
