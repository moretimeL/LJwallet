package com.liu.ljwallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.liu.ljwallet.activity.CreateWalletActivity;
import com.liu.ljwallet.activity.ImportWalletActivity;
import com.liu.ljwallet.application.ExitApplication;

public class IndexActivity extends AppCompatActivity {

    private long exitTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出程序
     */
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            // 退出程序关键代码
            ExitApplication.getInstance().exit();
        }
    }


    public void initView(){
        Button crateButton = findViewById(R.id.create_wallet);
        Button importButton = findViewById(R.id.import_wallet);
        TextView textView = findViewById(R.id.about_me);
        crateButton.setOnClickListener(e->{
            Intent intent = new Intent(this, CreateWalletActivity.class);
            startActivity(intent);
        });
        importButton.setOnClickListener(e->{
            Intent intent = new Intent(this, ImportWalletActivity.class);
            startActivity(intent);
        });
        textView.setOnClickListener(e->{
            // TODO 关于我们，阅读协议
        });
    }
}