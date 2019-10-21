package com.example.planeng;

import android.app.AlertDialog;
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
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.planeng.Book.BookSetActivity;
import com.example.planeng.Book.CountDate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class PlanActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView date;
    CalendarView calendar;
    int month,dayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        date = findViewById(R.id.selected_date);
        calendar = findViewById(R.id.calendar);
        Calendar TodayDate = Calendar.getInstance();    //透過Calendar取的資料
        int dayOfMonth = TodayDate.get(Calendar.DATE);       //一開啟軟體即取得年的數值
        int month  = TodayDate.get(Calendar.MONTH) + 1;
        date.setText(new StringBuilder().
                append(month).append(" / ").append(dayOfMonth));
        dbDate = CountDate.DateToString(TodayDate.getTime());


        calendar.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub

                month = month;
                dayOfMonth = dayOfMonth;

                date.setText(new StringBuilder().
                        append(month+1).append(" / ").append(dayOfMonth));
                dbDate= year+"/"+month+"/"+dayOfMonth;
                //StringBuilder可以將字串連續的加入

            }
        });

        TextView task = findViewById(R.id.task);
        task.setText("► TOEIC多益新制全真試題\n" +
                "     -第1章 多益考試介紹");




    }

    String dbDate;

    private void getTask() {

        String bookname;
        String chap;



        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse1 = new JSONObject(response);
                    boolean success = jsonResponse1.getBoolean("success");

                    if (success) {
                        String bookname = jsonResponse1.getString("bookname");
                        String chap = jsonResponse1.getString("chap");

                        Intent intent = new Intent(PlanActivity.this, MainActivity.class);
                        intent.putExtra("bookname" , bookname);
                        intent.putExtra("chap", chap);
                        PlanActivity.this.startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PlanActivity.this);
                        builder.setMessage("獲取讀書計畫失敗")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        getTask get = new getTask(dbDate, responseListener);
        RequestQueue queue = Volley.newRequestQueue(PlanActivity.this);
        queue.add(get);
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
