package com.liu.ljwallet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liu.ljwallet.IndexActivity;
import com.liu.ljwallet.R;
import com.liu.ljwallet.db.DbController;
import com.liu.ljwallet.entity.MyWallet;
import com.liu.ljwallet.util.BTCWalletUtil;
import com.liu.ljwallet.util.RxToast;

import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.Wallet;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class CreateWalletActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
        RxToast.setContext(this);
        init();
    }

    public void init(){
        TextView createButton = findViewById(R.id.createSub);
        EditText usernameText = findViewById(R.id.username);
        EditText passText = findViewById(R.id.password);
        EditText rePassText = findViewById(R.id.rePassword);
        EditText questionText = findViewById(R.id.passQuestion);
        createButton.setEnabled(false);
        usernameText.addTextChangedListener(new onEditorListener(createButton,usernameText,passText,rePassText,questionText));
        passText.addTextChangedListener(new onEditorListener(createButton,usernameText,passText,rePassText,questionText));
        rePassText.addTextChangedListener(new onEditorListener(createButton,usernameText,passText,rePassText,questionText));
    }

    class onEditorListener implements TextWatcher {
        private TextView createButton;
        private EditText usernameText;
        private EditText passText;
        private EditText rePassText;
        private EditText questionText;

        public onEditorListener(TextView createButton, EditText usernameText, EditText passText, EditText rePassText, EditText questionText) {
            this.createButton = createButton;
            this.usernameText = usernameText;
            this.passText = passText;
            this.rePassText = rePassText;
            this.questionText = questionText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            DbController dbController = DbController.getInstance(CreateWalletActivity.this);
            String username = usernameText.getText().toString();
            String passWord = passText.getText().toString();
            String rePassword = rePassText.getText().toString();
            if (!isEmpty(username) && !isEmpty(passWord) && !isEmpty(rePassword)){
                createButton.setBackgroundResource(R.drawable.shape_create_on);
                createButton.setEnabled(true);
                createButton.setOnClickListener(e->{
                    if (!passWord.equals(rePassword)){
                        RxToast.warning("两次密码不一致！");
                        return;
                    }
                    List<String> wd = BTCWalletUtil.generateMnemonic(e.getContext());
                    String seedCode = "";
                    if (wd == null || wd.size() < 12){
                        RxToast.error("创建钱包失败！");
                        return;
                    }
                    for (String s1 : wd) {
                        seedCode += s1 + " ";
                    }
                    seedCode = seedCode.substring(0,seedCode.length() - 1);
                    MyWallet myWallet1 = dbController.getById(1L);
                    if (myWallet1 == null){
                        Wallet wallet = BTCWalletUtil.getFromSpeed(seedCode);
                        MyWallet myWallet = new MyWallet();
                        myWallet.setAddress(wallet.currentReceiveAddress().toBase58());
                        myWallet.setId(1L);
                        myWallet.setIsBackup(false);
                        myWallet.setIsCurrent(true);
                        myWallet.setUserName(username);
                        myWallet.setPassword(passWord);
                        myWallet.setSeedCode(seedCode);
                        dbController.insertOrReplace(myWallet);
                    }
                    Intent intent = new Intent(CreateWalletActivity.this, BackUpSeedActivity.class);
                    intent.putExtra("seed", seedCode);
                    startActivity(intent);
                });
            }else {
                createButton.setBackgroundResource(R.drawable.shape_create_close);
                createButton.setEnabled(false);
            }
        }
    }

    private Boolean isEmpty(String s){
        return s == null || s.equals("");
    }
}