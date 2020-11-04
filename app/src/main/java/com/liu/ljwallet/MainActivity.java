package com.liu.ljwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.liu.ljwallet.activity.MyWalletActivity;
import com.liu.ljwallet.db.DbController;
import com.liu.ljwallet.entity.MyWallet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBackground();
    }

    public void initBackground(){
        try {
            DbController dbController = DbController.getInstance(MainActivity.this);
            MyWallet myWallet1 = dbController.getById(1L);
            Thread.sleep(2000);
            if (myWallet1 == null){
                Intent intent = new Intent(this, IndexActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else {
                Intent intent = new Intent(this, MyWalletActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}