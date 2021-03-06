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
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.planeng.Book.BookListActivity;
import com.example.planeng.Book.CountDate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlanActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView date;
    CalendarView calendar;

    String dbDate;

    String bookname;
    String chap;
    String m_id;



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
        setTitle("讀書計畫");
        Intent IDintent =getIntent();
        m_id = IDintent.getStringExtra("m_id");
        //Toast.makeText(PlanActivity.this,m_id, Toast.LENGTH_LONG).show();
        ImageButton booklistbtn = findViewById(R.id.plan_edit_bt);
        booklistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PlanActivity.this, BookListActivity.class);
                intent.putExtra("m_id", m_id);
                startActivity(intent);

            }
        });
        date = findViewById(R.id.selected_date);
        calendar = findViewById(R.id.calendar);
        calendar.getDate();
        Calendar TodayDate = Calendar.getInstance();
        int year = TodayDate.get(Calendar.YEAR); //透過Calendar取的資料
        int dayOfMonth = TodayDate.get(Calendar.DATE);       //一開啟軟體即取得年的數值
        int month  = TodayDate.get(Calendar.MONTH)+1 ;
        date.setText(new StringBuilder().
                append(month).append(" / ").append(dayOfMonth));

        dbDate=year+"/"+month+"/"+dayOfMonth;
        dbDate= CountDate.DateToString(CountDate.DateDemo(dbDate));

        TextView task = findViewById(R.id.task);
        task.setMovementMethod(ScrollingMovementMethod.getInstance());
        task.setText(
                "");
        getTask();



        calendar.setOnDateChangeListener(new OnDateChangeListener() {


            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub

                TextView task = findViewById(R.id.task);
                task.setMovementMethod(ScrollingMovementMethod.getInstance());
                task.setText("");
                s="";

                date.setText(new StringBuilder().
                        append(month+1).append(" / ").append(dayOfMonth));
                dbDate=year+"/"+(month+1)+"/"+dayOfMonth;
                dbDate= CountDate.DateToString(CountDate.DateDemo(dbDate));
                getTask();

                /*
                TextView task = findViewById(R.id.task);
                task.setText(
                        "");
                */

            }
        });

        t.start();



    }






    Thread t = new Thread() {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Thread.sleep(10);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // update TextView here!


                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    };



    String s;

    public void getTask() {


        // Response received from the server
        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse1 = new JSONObject(response);
                    boolean success = jsonResponse1.getBoolean("success");

                    if (success) {
                        List<String> Abook=new ArrayList<>();
                        List<String> Achap=new ArrayList<>();
                        TextView task = findViewById(R.id.task);
                        task.setMovementMethod(ScrollingMovementMethod.getInstance());
                        task.setText("");
                        String s="";
                        int j=Integer.parseInt(jsonResponse1.getString("i"));

                        for (int i = 0; i < j; i++) {

                            Abook.add(jsonResponse1.getString("bookname["+i+"]"));
                            Achap.add(jsonResponse1.getString("chap["+i+"]"));

                        }
                        for (int i = 0; i < j; i++) {
                            s=s+"► "+Abook.get(i)+
                                    " -\n"+"    "+Achap.get(i)+"\n";

                        }

                        task.setText(s);



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
        //String m_id ="163";

        //Toast.makeText(PlanActivity.this,dbDate, Toast.LENGTH_SHORT).show();

        getTask get = new getTask(m_id,dbDate, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(PlanActivity.this);
        queue1.add(get);




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