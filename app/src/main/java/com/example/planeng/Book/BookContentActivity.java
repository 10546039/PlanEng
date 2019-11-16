package com.example.planeng.Book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
import com.example.planeng.getContent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BookContentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String m_id;
    String Booktitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_content);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        setTitle("參考書詳細資訊");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Intent bookIntent =getIntent();
        Bundle bundle = bookIntent.getExtras();
        m_id=bundle.getString("m_id",null);

        Booktitle = bundle.getString("booktitle",null);
        TextView BookTitle = findViewById(R.id.BookTitle);
        BookTitle.setText(Booktitle);
        getContent();


    }

    public List<String> chapDetail=new ArrayList<>();

        public void getContent() {


            // Response received from the server
            Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse3 = new JSONObject(response);
                        boolean success1 = jsonResponse3.getBoolean("success");

                        if (success1) {

                            TextView Contenttask = findViewById(R.id.ChapDetail);
                            Contenttask.setMovementMethod(ScrollingMovementMethod.getInstance());
                            Contenttask.setText("");
                            String s="";
                            int j=Integer.parseInt(jsonResponse3.getString("i"));

                            for (int i = 0; i < j; i++) {

                                chapDetail.add(jsonResponse3.getString("chapName["+i+"]"));
                                //Toast.makeText(BookContentActivity.this,chapDetail.get(0), Toast.LENGTH_SHORT).show();
                                //chapDetail.add("abc");

                            }
                            for (int i = 0; i < j; i++) {
                                s=s+chapDetail.get(i)+
                                        " -\n";

                            }

                            Contenttask.setText(s);



                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(BookContentActivity.this);
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
            //String m_id ="6";

            //Toast.makeText(PlanActivity.this,dbDate, Toast.LENGTH_SHORT).show();

            getContent get = new getContent(m_id,Booktitle, responseListener3);
            RequestQueue queue1 = Volley.newRequestQueue(BookContentActivity.this);
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
        getMenuInflater().inflate(R.menu.book_content, menu);
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