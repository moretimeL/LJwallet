package com.liu.ljwallet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.liu.ljwallet.R;
import com.liu.ljwallet.db.DbController;
import com.liu.ljwallet.entity.MyWallet;
import com.liu.ljwallet.entity.Transaction;
import com.liu.ljwallet.layout.CopyButtonLibrary;
import com.liu.ljwallet.util.BTCWalletUtil;
import com.liu.ljwallet.util.Contents;
import com.liu.ljwallet.util.ETHWalletUtil;
import com.liu.ljwallet.util.QRCodeEncoder;

import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.Wallet;

import java.util.List;

import jnr.ffi.annotations.In;

public class MyWalletActivity extends AppCompatActivity {
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initView();
    }
    
    
    public void initView(){
        LinearLayout linearLayout = findViewById(R.id.transactionList);
        TextView address = findViewById(R.id.address);
        TextView balance = findViewById(R.id.balance);
        TextView goSend = findViewById(R.id.goSend);
        DbController dbController = DbController.getInstance(MyWalletActivity.this);
        List<Transaction> transactions = dbController.searchAllTransaction();
        for (Transaction transaction : transactions) {
            LinearLayout one = new LinearLayout(MyWalletActivity.this);
            one.setOrientation(LinearLayout.VERTICAL);
            TextView to = new TextView(MyWalletActivity.this);
            TextView hash = new TextView(MyWalletActivity.this);
            TextView fee = new TextView(MyWalletActivity.this);
            TextView num = new TextView(MyWalletActivity.this);
            to.setText("to:"+transaction.getTo());
            hash.setText("hash:"+transaction.getTransactionHash());
            fee.setText("fee:"+transaction.getGasUsed());
            num.setText("num:"+transaction.getNum());
            to.setOnClickListener(e->{
                //传入需要复制的文字的控件
                CopyButtonLibrary copyButtonLibrary = new CopyButtonLibrary(getApplicationContext(),to);
                copyButtonLibrary.init();
            });
            hash.setOnClickListener(e->{
                //传入需要复制的文字的控件
                CopyButtonLibrary copyButtonLibrary = new CopyButtonLibrary(getApplicationContext(),hash);
                copyButtonLibrary.init();
            });
            one.addView(to, new LinearLayout.LayoutParams(MP,0,1));
            one.addView(hash, new LinearLayout.LayoutParams(MP,0,2));
            one.addView(fee, new LinearLayout.LayoutParams(MP,0,1));
            one.addView(num, new LinearLayout.LayoutParams(MP,0,1));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MP,0,1);
            params.setMargins(50,10,50,10);
            linearLayout.addView(one, params);

        }
        MyWallet myWallet1 = dbController.getById(1L);
        // BTC流程
        /*WalletAppKit appKit =  BTCWalletUtil.getWalletKit(MyWalletActivity.this, myWallet1.getSeedCode());
        balance.setText("￥"+appKit.wallet().getBalance().value+"");*/
        ETHWalletUtil.createWalletBySeed(myWallet1.getSeedCode());
        balance.setText(ETHWalletUtil.getBalance(myWallet1.getAddress()).toString());
        address.setText(myWallet1.getAddress());
        ImageView imageView = findViewById(R.id.image_QR_code);
        Bitmap bitmap = null;
        System.out.println(myWallet1.getSeedCode());
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(myWallet1.getAddress(), null
                , Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), 800);
        try {
            bitmap = qrCodeEncoder.encodeAsBitmap();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);
        address.setOnClickListener(e->{
            //传入需要复制的文字的控件
            CopyButtonLibrary copyButtonLibrary = new CopyButtonLibrary(getApplicationContext(),address);
            copyButtonLibrary.init();
        });
        goSend.setOnClickListener(e->{
            Intent intent = new Intent(MyWalletActivity.this, SendActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}