package com.liu.ljwallet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.liu.ljwallet.R;

import java.util.Arrays;
import java.util.List;

public class BackUpSeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_up_seed);
        initView();
    }

    public void initView(){
        Intent intent = getIntent();
        String seed = intent.getStringExtra("seed");
        List<String> seeds = Arrays.asList(seed.split(" "));
        System.out.println(seeds);


    }
}