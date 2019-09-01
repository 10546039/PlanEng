package com.example.planeng;

import android.app.DatePickerDialog;
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
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

//載入calendar package


public class BookSetActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //日期選擇
    Calendar c = Calendar.getInstance();
    TextView DisplayStartDate;
    TextView DisplayEndDate;
    int cday, cmonth, cyear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_set);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);







//日期選擇

        ImageButton changeDateStart = (ImageButton) findViewById(R.id.choose_start_bt);
        DisplayStartDate = (TextView) findViewById(R.id.start_date);
        ImageButton changeDateEnd = (ImageButton) findViewById(R.id.choose_end_bt);
        DisplayEndDate = (TextView) findViewById(R.id.end_date);





        final DatePickerDialog.OnDateSetListener d1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                cday = dayOfMonth;
                cmonth = monthOfYear + 1;
                cyear = year;


                DisplayStartDate.setText( cyear + "/" + cmonth + "/" + cday);
            }
        };

        final DatePickerDialog.OnDateSetListener d2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                cday = dayOfMonth;
                cmonth = monthOfYear + 1;
                cyear = year;
                DisplayEndDate.setText( cyear + "/" + cmonth + "/" + cday);

            }


        };


        //跳到BookEdit頁

        final ImageButton GoBtn;



        GoBtn  = findViewById(R.id.go_bt);

        GoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent();
                editIntent.setClass(BookSetActivity.this,BookEditActivity.class);
                //new一個Bundle物件，並將要傳遞的資料傳入
                EditText bookName = (EditText)findViewById(R.id.book_name);
                EditText chapNum = (EditText)findViewById(R.id.chaptnumber);
                int totalChap = Integer.parseInt(chapNum.getText().toString());
                String book_name = bookName.getText().toString();
                Date startDate = CountDate.DateDemo(DisplayStartDate.getText().toString());
                Date endDate = CountDate.DateDemo(DisplayEndDate.getText().toString());
                long betweendays=(long) ((endDate.getTime()-startDate.getTime())/(1000*3600*24));


                Bundle bundle = new Bundle();
                bundle.putLong("totalPlanDate", betweendays );
                bundle.putInt("totalChap", totalChap );
                bundle.putString("book_name", book_name);//傳遞String
                editIntent.putExtras(bundle);

                startActivity(editIntent);
            }
        });


        changeDateStart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                new DatePickerDialog(BookSetActivity.this, d1,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        changeDateEnd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                new DatePickerDialog(BookSetActivity.this, d2,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });










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
        getMenuInflater().inflate(R.menu.book_set, menu);
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
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;





    }
}
