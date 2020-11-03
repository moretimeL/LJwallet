package com.liu.ljwallet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.liu.ljwallet.R;
import com.liu.ljwallet.util.RxToast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ConfirmSeedActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private static int[] mnemonicCodes = {R.id.mnemonicCode1,R.id.mnemonicCode2,
            R.id.mnemonicCode3,R.id.mnemonicCode4,R.id.mnemonicCode5,R.id.mnemonicCode6
            ,R.id.mnemonicCode7,R.id.mnemonicCode8,R.id.mnemonicCode9,R.id.mnemonicCode10
            ,R.id.mnemonicCode11,R.id.mnemonicCode12};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_seed);
        RxToast.setContext(this);
        initView();
    }

    public void initView(){
        TextView sureBackUp = findViewById(R.id.sureBackUp);
        AtomicInteger index = new AtomicInteger();
        Intent intent = getIntent();
        String seed = intent.getStringExtra("seed");
        List<String> seeds = Arrays.asList(seed.split(" "));
        List<String> oldSeeds = Arrays.asList(seed.split(" "));;
        Collections.shuffle(seeds);
        tableLayout = findViewById(R.id.pointTable);
        //清除所有表格
        tableLayout.removeAllViews();
        //全部列自动填充空白处
        tableLayout.setStretchAllColumns(true);
        TableRow tableRow = new TableRow(ConfirmSeedActivity.this);
        for(int i=0;i<seeds.size();i++){
            TextView tv = new TextView(ConfirmSeedActivity.this);
            tv.setGravity(Gravity.CENTER);
            tv.setText(seeds.get(i));
            tableRow.addView(tv);
            if((i+1)%3==0){
                tableLayout.addView(tableRow, new TableLayout.LayoutParams(MP, WC,1));
                tableRow = new TableRow(ConfirmSeedActivity.this);
            }
            tv.setOnClickListener(e->{
                if (tv.getText().toString().equals(oldSeeds.get(index.get()))){
                    TextView mnemonicCodeButton = findViewById(mnemonicCodes[index.get()]);
                    mnemonicCodeButton.setText(tv.getText().toString());
                    index.getAndIncrement();
                    tv.setText("");
                    tv.setEnabled(false);
                }else {
                    RxToast.error("助记词顺序错误，请重试！");
                }
                if (index.get() > 11){
                    sureBackUp.setBackgroundResource(R.drawable.shape_create_on);
                    sureBackUp.setOnClickListener(a->{

                    });
                }
            });
        }

    }
}