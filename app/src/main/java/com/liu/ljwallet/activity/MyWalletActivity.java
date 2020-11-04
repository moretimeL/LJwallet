package com.liu.ljwallet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.liu.ljwallet.R;
import com.liu.ljwallet.db.DbController;
import com.liu.ljwallet.entity.MyWallet;
import com.liu.ljwallet.util.BTCWalletUtil;
import com.liu.ljwallet.util.Contents;
import com.liu.ljwallet.util.QRCodeEncoder;

import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.Wallet;

public class MyWalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        initView();
    }
    
    
    public void initView(){
        TextView address = findViewById(R.id.address);
        TextView balance = findViewById(R.id.balance);
        DbController dbController = DbController.getInstance(MyWalletActivity.this);
        MyWallet myWallet1 = dbController.getById(1L);
        WalletAppKit appKit =  BTCWalletUtil.getWalletKit(MyWalletActivity.this, myWallet1.getSeedCode());
        balance.setText("￥"+appKit.wallet().getBalance().value+"");
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}