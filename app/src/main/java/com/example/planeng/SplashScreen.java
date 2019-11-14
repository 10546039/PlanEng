package com.example.planeng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.planeng.Book.BookEditActivity;
import com.example.planeng.Book.BookListActivity;
import com.example.planeng.Book.BookSetActivity;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        //以下撰寫跳轉畫面的程式碼
        //啟動執行序
        new Thread(new Runnable() {
    @Override
    public void run() {
        try{
        Thread.sleep(2000);
        startActivity(new Intent().setClass(SplashScreen.this, BookListActivity.class));
        }catch (InterruptedException e){
        e.printStackTrace();
        }
        }
        }).start();
        }}
