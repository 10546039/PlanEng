package com.example.planeng.Book;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.planeng.MainActivity;
import com.example.planeng.NoteActivity;
import com.example.planeng.PlanActivity;
import com.example.planeng.R;
import com.example.planeng.ReviewActivity;
import com.example.planeng.getBook;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout LLinear;
    String m_id;
    public List<String> BookTitle;
    public List<String> startDate;
    public List<String> endDate;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        setTitle("");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        Intent IDintent =getIntent();
        m_id = IDintent.getStringExtra("m_id");
        initView();
        getBook();
        //addView();

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
        BookTitle = new ArrayList<>();
        startDate = new ArrayList<>();
        endDate = new ArrayList<>();

    }



    private void getBook() {

        // Response received from the server
        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse2 = new JSONObject(response);
                    boolean success = jsonResponse2.getBoolean("success");

                    if (success) {

                        int j=Integer.parseInt(jsonResponse2.getString("i"));

                        for (int i = 0; i < j; i++) {

                            BookTitle.add(jsonResponse2.getString("booktitle["+i+"]"));
                            startDate.add(jsonResponse2.getString("startDate["+i+"]"));
                            endDate.add(jsonResponse2.getString("endDate["+i+"]"));

                        }



                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(BookListActivity.this);
                        builder.setMessage("獲取讀書計畫失敗")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //資料拿到之後去根據資料去動態新增View
                addView();
            }
        };
        //String m_id ="6";



        getBook get = new getBook(m_id, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(BookListActivity.this);
        queue1.add(get);




    }
int k;
    /**
     * 動態新增的具體實現
     */
    private void addView() {

        //ivList集合有幾個元素就新增幾個
        for (  k = 0; k < BookTitle.size(); k++) {
            final int b=k;
            //首先引入要新增的View
            View Listview = View.inflate(this, R.layout.book, null);
            //找到裡面需要動態改變的控制元件
            TextView BookTitleView = Listview.findViewById(R.id.listBookName);
            TextView BookPeriodView = Listview.findViewById(R.id.listBookTime);
            ImageButton MoreBt = Listview.findViewById(R.id.MoreBt);
            //給控制元件賦值
            BookTitleView.setText(BookTitle.get(k));
            BookPeriodView.setText("期間："+startDate.get(k)+"~"+endDate.get(k));
            //傳送資料到BookContentActivity

            MoreBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent bookIntent = new Intent();
                    bookIntent.setClass(BookListActivity.this,BookContentActivity.class);
                    //new一個Bundle物件，並將要傳遞的資料傳入

                    String StartDate = startDate.get(b);
                    String EndDate = endDate.get(b);

                    Bundle bundle = new Bundle();

                    bundle.putString("booktitle", BookTitle.get(b));//傳遞String
                    bundle.putString("startDate", StartDate);
                    bundle.putString("endDate", EndDate);
                    bundle.putString("m_id", m_id);
                    bookIntent.putExtras(bundle);

                    startActivity(bookIntent);
                    BookListActivity.this.finish();
                }
            });

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