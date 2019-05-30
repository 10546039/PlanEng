package com.example.planeng;

import android.content.Intent;
import android.print.PrintAttributes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        //設定隱藏狀態
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);


        ImageButton login_signupbt = (ImageButton)findViewById(R.id.login_signupbt);

        ImageButton nextPageBtn = (ImageButton)findViewById(R.id.login_signupbt);
        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this , SignupActivity.class);
                startActivity(intent);
            }
        });

    }
    public void login(View v){
        EditText login_username = (EditText) findViewById(R.id.login_username);
        EditText login_passwd = (EditText) findViewById(R.id.login_passwd);
        String uid = login_username.getText().toString();
        String pw = login_passwd.getText().toString();

        //設定帳號驗證，jack帳密為假設

        if (uid.equals("jack") && pw.equals("1234")){ //登入成功

            Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();

            finish();


        }else{ //登入失敗
            Toast toastfailed = Toast.makeText(this, "登入失敗，輸入的帳號或密碼錯誤", Toast.LENGTH_LONG);
            toastfailed.show();
            toastfailed.setGravity(Gravity.TOP,0,550);
            login_username.setText("");
            login_passwd.setText("");
        }
    }



}
