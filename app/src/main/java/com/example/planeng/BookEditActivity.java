package com.example.planeng;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookEditActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        initView();
        initData();






    }


    private int totalChap;
    private LinearLayout mLinear;
    private List<String> bookList;//放置標題的集合
    private List<String> chapNum;



    private void initView() {
        //要新增view的容器

        mLinear = findViewById(R.id.linear);
        bookList = new ArrayList<>();
        chapNum = new ArrayList<>();
        totalChap = 10;

    }

    /**
     * 處理資料,可以是伺服器請求過來的,也可以是本地的
     */

    private void initData() {
        for (int i = 0; i < totalChap; i++) {

            chapNum.add(""+(i+1)+"");

        }
        //資料拿到之後去根據資料去動態新增View
        addView();
    }





    /**
     * 動態新增的具體實現
     */
    private void addView() {
        //ivList集合有幾個元素就新增幾個
        for (int i = 0; i < totalChap; i++) {
            //首先引入要新增的View
            View view = View.inflate(this, R.layout.chap, null);
            //找到裡面需要動態改變的控制元件

            TextView chapter =  view.findViewById(R.id.chapter);

            //給控制元件賦值
            chapter.setText(chapNum.get(i));
            /*
            動態給每個View設定margin,也可以在xml裡面設定,xml裡面設定後每個view之間的間距都是一樣的
            動態設定可以給每個view之間的間距設定的不一樣 params.setMargins(int left, int top, int right, int bottom);
             */
            //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
             //       (LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            //view.setLayoutParams(params);
            //params.setMargins(24,0,24,0);
            //view.setTag(i);

            //把所有動態建立的view都新增到容器裡面
            mLinear.addView(view);
        }

    }








    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_book) {

        } else if (id == R.id.nav_note) {

        } else if (id == R.id.nav_review) {

        } else if (id == R.id.nav_plan) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }











}
