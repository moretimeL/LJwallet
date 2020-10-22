package com.liu.ljwallet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liu.ljwallet.R;
import com.liu.ljwallet.util.RxToast;

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
            String username = usernameText.getText().toString();
            String passWord = passText.getText().toString();
            String rePassword = rePassText.getText().toString();
            if (!isEmpty(username) && !isEmpty(passWord) && !isEmpty(rePassword)){
                createButton.setBackgroundResource(R.drawable.shape_create_on);
                createButton.setEnabled(true);
                createButton.setOnClickListener(e->{
                    // TODO 点击创建后的逻辑
                    if (!passWord.equals(rePassword)){
                        RxToast.warning("两次密码不一致！");
                    }
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