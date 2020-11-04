package com.liu.ljwallet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.liu.ljwallet.R;
import com.liu.ljwallet.db.DbController;
import com.liu.ljwallet.entity.MyWallet;
import com.liu.ljwallet.util.BTCWalletUtil;
import com.liu.ljwallet.util.RxToast;

import org.bitcoinj.wallet.Wallet;

import java.util.Arrays;
import java.util.List;

public class ImportWalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_wallet);
        init();
    }
    public void init(){
        TextView createButton = findViewById(R.id.importSub);
        EditText usernameText = findViewById(R.id.imp_username);
        EditText passText = findViewById(R.id.imp_password);
        EditText rePassText = findViewById(R.id.imp_rePassword);
        EditText questionText = findViewById(R.id.imp_passQuestion);
        createButton.setEnabled(false);
        usernameText.addTextChangedListener(new ImportWalletActivity.onEditorListener(createButton,usernameText,passText,rePassText,questionText));
        passText.addTextChangedListener(new ImportWalletActivity.onEditorListener(createButton,usernameText,passText,rePassText,questionText));
        rePassText.addTextChangedListener(new ImportWalletActivity.onEditorListener(createButton,usernameText,passText,rePassText,questionText));
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
            DbController dbController = DbController.getInstance(ImportWalletActivity.this);
            final String[] seedCode = {usernameText.getText().toString()};
            String passWord = passText.getText().toString();
            String rePassword = rePassText.getText().toString();
            if (!isEmpty(seedCode[0]) && !isEmpty(passWord) && !isEmpty(rePassword)){
                createButton.setBackgroundResource(R.drawable.shape_create_on);
                createButton.setEnabled(true);
                createButton.setOnClickListener(e->{
                    if (!passWord.equals(rePassword)){
                        RxToast.warning("两次密码不一致！");
                        return;
                    }
                    List<String> wd = Arrays.asList(seedCode[0].split("\\ "));
                    if (wd == null || wd.size() < 12){
                        RxToast.error("钱包格式错误！");
                        return;
                    }
                    MyWallet myWallet1 = dbController.getById(1L);
                    if (myWallet1 == null){
                        Wallet wallet = BTCWalletUtil.getFromSpeed(seedCode[0]);
                        MyWallet myWallet = new MyWallet();
                        myWallet.setAddress(wallet.currentReceiveAddress().toBase58());
                        myWallet.setId(1L);
                        myWallet.setIsBackup(false);
                        myWallet.setIsCurrent(true);
                        myWallet.setUserName("user");
                        myWallet.setPassword(passWord);
                        myWallet.setSeedCode(seedCode[0]);
                        dbController.insertOrReplace(myWallet);
                    }
                    Intent intent = new Intent(ImportWalletActivity.this, MyWalletActivity.class);
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