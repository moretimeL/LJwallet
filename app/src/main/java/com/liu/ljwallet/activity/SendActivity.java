package com.liu.ljwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.liu.ljwallet.R;
import com.liu.ljwallet.db.DbController;
import com.liu.ljwallet.entity.MyWallet;
import com.liu.ljwallet.entity.Transaction;
import com.liu.ljwallet.util.ETHWalletUtil;
import com.liu.ljwallet.util.RxToast;

import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class SendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        RxToast.setContext(this);
        initView();
    }

    public void initView(){
        TextView sendSubBut = findViewById(R.id.sendSub);
        EditText toAddressText = findViewById(R.id.toAddress);
        EditText tranNumText = findViewById(R.id.tranNum);
        EditText remarksText = findViewById(R.id.remarks);
        sendSubBut.setEnabled(false);
        toAddressText.addTextChangedListener(new SendActivity.onEditorListener(sendSubBut,toAddressText,remarksText,tranNumText));
        tranNumText.addTextChangedListener(new SendActivity.onEditorListener(sendSubBut,toAddressText,remarksText,tranNumText));
        remarksText.addTextChangedListener(new SendActivity.onEditorListener(sendSubBut,toAddressText,remarksText,tranNumText));
    }


    class onEditorListener implements TextWatcher {
        private TextView sendSubBut;
        private EditText toAddressText;
        private EditText remarksText;
        private EditText tranNumText;

        public onEditorListener(TextView sendSubBut, EditText toAddressText, EditText remarksText, EditText tranNumText) {
            this.sendSubBut = sendSubBut;
            this.toAddressText = toAddressText;
            this.remarksText = remarksText;
            this.tranNumText = tranNumText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            DbController dbController = DbController.getInstance(SendActivity.this);
            String toAddress = toAddressText.getText().toString();
            String remarks = remarksText.getText().toString();
            String tranNum = tranNumText.getText().toString();
            if (!isEmpty(toAddress) && !isEmpty(tranNum)){
                sendSubBut.setBackgroundResource(R.drawable.shape_create_on);
                sendSubBut.setEnabled(true);
                sendSubBut.setOnClickListener(e->{
                    Transaction transaction = ETHWalletUtil.sendCoin(toAddress, tranNum);
                    if (transaction!=null && transaction.getTransactionHash()!=null){
                        dbController.insert(transaction);
                    }
                    Intent intent = new Intent(SendActivity.this, MyWalletActivity.class);
                    startActivity(intent);
                });
            }else {
                sendSubBut.setBackgroundResource(R.drawable.shape_create_close);
                sendSubBut.setEnabled(false);
            }
        }
    }

    private Boolean isEmpty(String s){
        return s == null || s.equals("");
    }
}