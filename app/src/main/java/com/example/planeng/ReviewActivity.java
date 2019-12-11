package com.example.planeng;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.planeng.Book.BookListActivity;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout LLinear;
    String m_id;
    public List<String> r_type;
    public List<String> r_test_type;
    public List<String> r_test_score;
    public List<String> r_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
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

        ImageButton addreview_bt = (ImageButton)findViewById(R.id.imageButton13);
        addreview_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ReviewActivity.this , Review_add_Activity.class);
                intent.putExtra("m_id", m_id);
                startActivity(intent);
            }
        });
        ImageButton rplanbt = (ImageButton)findViewById(R.id.rpp);
        rplanbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ReviewActivity.this , planreviewActivity.class);
                intent.putExtra("m_id", m_id);
                startActivity(intent);
            }
        });
        ImageButton rtestbt = (ImageButton)findViewById(R.id.rtt);
        rtestbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ReviewActivity.this , testreviewActivity.class);
                intent.putExtra("m_id", m_id);
                startActivity(intent);
            }
        });
    }



    private void initView() {
        //要新增view的容器
        LLinear = findViewById(R.id.parentLinearLayout);
        r_type = new ArrayList<>();
        r_test_type = new ArrayList<>();
        r_test_score = new ArrayList<>();
        r_data = new ArrayList<>();
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

                            r_type.add(jsonResponse2.getString("r_type["+i+"]"));
                            r_test_type.add(jsonResponse2.getString("r_test_type["+i+"]"));
                            r_test_score.add(jsonResponse2.getString("r_test_score["+i+"]"));
                            r_data.add(jsonResponse2.getString("r_data["+i+"]"));

                        }
                    } else {

                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
                        builder.setMessage("獲取讀書心得失敗")
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
        getReview get = new getReview( m_id , responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(ReviewActivity.this);
        queue1.add(get);

    }
    private void getBook2() {

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

                            r_type.add(jsonResponse2.getString("r_type["+i+"]"));
                            r_test_type.add(jsonResponse2.getString("r_test_type["+i+"]"));
                            r_test_score.add(jsonResponse2.getString("r_test_score["+i+"]"));
                            r_data.add(jsonResponse2.getString("r_data["+i+"]"));

                        }
                    } else {

                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
                        builder.setMessage("獲取讀書心得失敗")
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
        String m_id ="6";
        getRplan get = new getRplan( m_id , responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(ReviewActivity.this);
        queue1.add(get);

    }
    int k;
    /**
     * 動態新增的具體實現
     */
    private void addView() {

        //ivList集合有幾個元素就新增幾個
        for (  k = 0; k < r_data.size(); k++) {
            final int b=k;
            //首先引入要新增的View
            View Listview = View.inflate(this, R.layout.reviewdp, null);
            //找到裡面需要動態改變的控制元件
            TextView reviewTitleView = Listview.findViewById(R.id.reveiwID);
            TextView reviewTitleView1 = Listview.findViewById(R.id.type);
            TextView reviewTitleView2 = Listview.findViewById(R.id.test);
            TextView reviewTitleView3 = Listview.findViewById(R.id.score);
            ImageButton MoreBt = Listview.findViewById(R.id.morerbt);
            //給控制元件賦值
            reviewTitleView.setText(r_data.get(k));
            reviewTitleView1.setText(r_type.get(k));
            reviewTitleView2.setText(r_test_type.get(k));
            reviewTitleView3.setText(r_test_score.get(k));
            //傳送資料到BookContentActivity

            MoreBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent bookIntent = new Intent();
                    bookIntent.setClass(ReviewActivity.this,Review_out_Activity.class);
                    //new一個Bundle物件，並將要傳遞的資料傳入

                    String data = r_data.get(b);
                    String type = r_type.get(b);
                    String ttype = r_test_type.get(b);
                    String tscore = r_test_score.get(b);
                    Bundle bundle = new Bundle();

                    bundle.putString("r_data", data);//傳遞String
                    bundle.putString("r_type", type);
                    bundle.putString("r_test_type", ttype);
                    bundle.putString("r_test_score", tscore);
                    bundle.putString("m_id", m_id);
                    bookIntent.putExtras(bundle);

                    startActivity(bookIntent);
                    ReviewActivity.this.finish();
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
        getMenuInflater().inflate(R.menu.review, menu);
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