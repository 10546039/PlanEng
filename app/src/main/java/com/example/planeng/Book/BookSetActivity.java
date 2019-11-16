package com.example.planeng.Book;

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
import android.widget.Toast;

import com.example.planeng.MainActivity;
import com.example.planeng.NoteActivity;
import com.example.planeng.PlanActivity;
import com.example.planeng.R;
import com.example.planeng.ReviewActivity;

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
    String m_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_set);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        setTitle("設定書籍條件");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setTitle("新增書籍");
        Intent IDintent =getIntent();
        m_id = IDintent.getStringExtra("m_id");

        //Toast.makeText(BookSetActivity.this,m_id, Toast.LENGTH_LONG).show();




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
                long betweendays=(long) ((endDate.getTime()-startDate.getTime())/(1000*3600*24)+1);


                Bundle bundle = new Bundle();

                bundle.putLong("totalPlanDate", betweendays );
                bundle.putInt("totalChap", totalChap );
                bundle.putString("book_name", book_name);//傳遞String
                bundle.putString("startDate", CountDate.DateToString(startDate));
                bundle.putString("endDate", CountDate.DateToString(endDate));
                bundle.putString("m_id", m_id);
                editIntent.putExtras(bundle);

                startActivity(editIntent);
                BookSetActivity.this.finish();





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



    private void refresh() {
    	/*finish();
    	Intent intent = new Intent(RefreshActivityTest.this, RefreshActivityTest.class);
    	startActivity(intent);*/

        onCreate(null);
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
