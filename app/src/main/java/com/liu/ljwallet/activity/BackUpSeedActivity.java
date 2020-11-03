package com.liu.ljwallet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.liu.ljwallet.R;

import java.util.Arrays;
import java.util.List;

public class BackUpSeedActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_up_seed);
        initView();
    }

    public void initView(){
        TextView backUpSeed = findViewById(R.id.backUpSeed);
        Intent intent = getIntent();
        String seed = intent.getStringExtra("seed");
        List<String> seeds = Arrays.asList(seed.split(" "));
        tableLayout = findViewById(R.id.table1);
        //清除所有表格
        tableLayout.removeAllViews();
        //全部列自动填充空白处
        tableLayout.setStretchAllColumns(true);
        TableRow tableRow = new TableRow(BackUpSeedActivity.this);
        for(int i=0;i<seeds.size();i++){
            TextView tv = new TextView(BackUpSeedActivity.this);
            tv.setGravity(Gravity.CENTER);
            tv.setText(i+1+". "+seeds.get(i));
            tableRow.addView(tv);
            if((i+1)%3==0){
                tableLayout.addView(tableRow, new TableLayout.LayoutParams(MP, WC,1));
                tableRow=new TableRow(BackUpSeedActivity.this);
            }
        }
        backUpSeed.setOnClickListener(e->{
            Intent intent1 = new Intent(BackUpSeedActivity.this, ConfirmSeedActivity.class);
            intent1.putExtra("seed", seed);
            startActivity(intent1);
        });


    }
}