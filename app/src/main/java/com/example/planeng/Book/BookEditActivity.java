package com.example.planeng.Book;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.planeng.MainActivity;
import com.example.planeng.PlanActivity;
import com.example.planeng.R;

import java.util.ArrayList;
import java.util.Date;
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

        Intent editIntent =getIntent();
        Bundle bundle = editIntent.getExtras();
        String bookname = bundle.getString("book_name",null);
        TextView booktitle = findViewById(R.id.booktitle);
        booktitle.setText(bookname);
        totalChap = bundle.getInt("totalChap",1);
        TextView totalPlanDate = findViewById(R.id.planDay);
        planDay = bundle.getLong("totalPlanDate");
        totalPlanDate.setText(planDay.toString());
        //EditText chDay = findViewById(R.id.ch_day);
        //chDay.setHint(Integer.toString(getAvChapday()));
        String tStartDate = bundle.getString("startDate",null);
        startDate = CountDate.DateDemo(tStartDate);
        String tEndDate = bundle.getString("endDate",null);
        endDate = CountDate.DateDemo(tEndDate);

        ImageButton saveBook = findViewById(R.id.saveBook);
        //saveBook.setOnClickListener(new saveBookOnClickListener());




    }



    Date startDate;
    Date endDate;
    private LinearLayout mLinear;
    //放置標題的集合
    private List<String> chapNum;

    int avDay;
    String SavDay;
    int totalChap;
    Long planDay;




    public Long getPlanDay() {
        Intent editIntent =getIntent();
        Bundle bundle = editIntent.getExtras();
        planDay = bundle.getLong("totalPlanDate");
        return planDay;
    }

    public int getTotalChap() {
        Intent editIntent =getIntent();
        Bundle bundle = editIntent.getExtras();
        totalChap = bundle.getInt("totalChap",0);
        return totalChap;
    }

    public int getAvChapday() {
        int AvChapday=(int)Math.floor( getPlanDay()/getTotalChap());
        return AvChapday;
    }

    String tEndDate;
    String tStartDate;
    public Date getStartDate() {
        Intent editIntent =getIntent();
        Bundle bundle = editIntent.getExtras();
        tStartDate = bundle.getString("startDate",null);
        startDate = CountDate.DateDemo(tStartDate);
        tEndDate = bundle.getString("endDate",null);
        endDate = CountDate.DateDemo(tEndDate);

        return startDate;
    }



    private void initView() {
        //要新增view的容器
        mLinear = findViewById(R.id.linear);
        chapNum = new ArrayList<>();
        getAvChapday();
        getTotalChap();
    }

    /**
     * 處理資料,可以是伺服器請求過來的,也可以是本地的
     */

    private void initData() {

        getTotalChap();
        getAvChapday();

        for (int i = 0; i < totalChap; i++) {

            chapNum.add(""+(i+1)+"");

        }

        //資料拿到之後去根據資料去動態新增View
        addView();

    }


    /**
     * 動態新增的具體實現
     */
    EditText chName;
    private  List<Integer> textid;


    private void addView() {
        //ivList集合有幾個元素就新增幾個

        getTotalChap();
        avDay=getAvChapday();
        SavDay=Integer.toString(avDay);
        for (int i = 0; i < totalChap; i++) {
            //首先引入要新增的View
            View view = View.inflate(this, R.layout.chap, null);
            //找到裡面需要動態改變的控制元件
            EditText chDay=view.findViewById(R.id.ch_day);
            chDay.setText(SavDay);
            TextView chapter =  view.findViewById(R.id.chapter);
            chName= view.findViewById(R.id.chapDetail);

            //給控制元件賦值
            chapter.setText(chapNum.get(i));


            //動態給每個View設定margin,也可以在xml裡面設定,xml裡面設定後每個view之間的間距都是一樣的
            //動態設定可以給每個view之間的間距設定的不一樣 params.setMargins(int left, int top, int right, int bottom);
            //
            //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
             //       (LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            //view.setLayoutParams(params);
            //params.setMargins(24,0,24,0);
            //view.setTag(i);
            //把所有動態建立的view都新增到容器裡面
            mLinear.addView(view);
        }

    }
/*
    private void addChapName(){
        View view = View.inflate(this, R.layout.chap, null);
        EditText chName= view.findViewById(R.id.chapDetail);
        chapName.add(chName.getText().toString());
    }

    ImageButton saveBook = findViewById(R.id.saveBook);


*/
/*
     List<String> chapName;

    private class saveBookOnClickListener implements View.OnClickListener {
        public void onClick(View view){


            chapName.add(chName.getText().toString());
            getStartDate();

            Toast.makeText(BookEditActivity.this,chapName.get(1),Toast.LENGTH_LONG).show();

        }
    }


*/












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
    //側邊MENU
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_book) {
            Intent intent = new Intent(this, BookSetActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_note) {

        } else if (id == R.id.nav_review) {

        } else if (id == R.id.nav_plan) {
            Intent intent = new Intent(this, PlanActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
