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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planeng.MainActivity;
import com.example.planeng.NoteActivity;
import com.example.planeng.PlanActivity;
import com.example.planeng.R;
import com.example.planeng.ReviewActivity;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout LLinear;
    List<String> chapNameList;//放置標題的集合
    List<String> chapFrontList;//放內容的集合
    String[] iv = {"apple","banana","melon","berry"};
    String m_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        Intent IDintent =getIntent();
        m_id = IDintent.getStringExtra("m_id");
        //Toast.makeText(BookListActivity.this,m_id, Toast.LENGTH_LONG).show();
        initView();
        initData();

        ImageButton addBook_bt = (ImageButton)findViewById(R.id.addbook_bt);
        addBook_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BookListActivity.this , BookSetActivity.class);
                intent.putExtra("m_id", m_id);
                startActivity(intent);
            }
        });
    }


    private void initView() {
        //要新增view的容器
        LLinear = findViewById(R.id.ListLinear);
        chapNameList = new ArrayList<>();
        chapFrontList = new ArrayList<>();

    }
    /**
     * 處理資料,可以是伺服器請求過來的,也可以是本地的
     */
    private void initData() {
        for (int i = 0; i < iv.length; i++) {
            chapFrontList.add(iv[i]);
            chapNameList.add("第" + (i+1) + "本");
        }
        //資料拿到之後去根據資料去動態新增View
        addView();
    }

    /**
     * 動態新增的具體實現
     */

    TextView bookName;
    TextView chapFront;

    private void addView() {
        //ivList集合有幾個元素就新增幾個
        for (int i = 0; i < chapFrontList.size(); i++) {
            //首先引入要新增的View
            View Listview = View.inflate(this, R.layout.book, null);
            //找到裡面需要動態改變的控制元件
            bookName = Listview.findViewById(R.id.bookName);
            chapFront = Listview.findViewById(R.id.chapFront);
            //給控制元件賦值
            bookName.setText(chapNameList.get(i));
            chapFront.setText(chapFrontList.get(i));


            //把所有動態建立的view都新增到容器裡面
            LLinear.addView(Listview);
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
        getMenuInflater().inflate(R.menu.book_list, menu);
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
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("m_id", m_id);
            startActivity(intent);
        } else if (id == R.id.nav_book) {
            Intent intent = new Intent(this, BookListActivity.class);
            intent.putExtra("m_id", m_id);
            startActivity(intent);


        } else if (id == R.id.nav_note) {
            Intent intent = new Intent(this, NoteActivity.class);
            intent.putExtra("m_id", m_id);
            startActivity(intent);
        } else if (id == R.id.nav_review) {
            Intent intent = new Intent(this, ReviewActivity.class);
            intent.putExtra("m_id", m_id);
            startActivity(intent);
        } else if (id == R.id.nav_plan) {
            Intent intent = new Intent(this, PlanActivity.class);
            intent.putExtra("m_id", m_id);
            startActivity(intent);

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}