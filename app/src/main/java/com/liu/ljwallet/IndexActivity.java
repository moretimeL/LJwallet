package com.liu.ljwallet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.liu.ljwallet.activity.CreateWalletActivity;
import com.liu.ljwallet.activity.ImportWalletActivity;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
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