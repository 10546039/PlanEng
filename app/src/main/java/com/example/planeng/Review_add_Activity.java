package com.example.planeng;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Review_add_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText etReview;
    ImageButton bReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_add_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        etReview = findViewById(R.id.reviewdata);




        ImageButton saveBook = findViewById(R.id.savereview);
        saveBook.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText data = findViewById(R.id.reviewdata);
                if (data.equals("")){
                    showDialog(datacheck);
                }else{
                    showDialog(check);
                }
            }
        });


    }
    final int check = 1000;
    final int datacheck = 2000;
    @Override
    protected Dialog onCreateDialog(int id)  //初始化對話方塊，透過 showDialog(ID) 觸發
    {
        Dialog dialog = null;

        switch(id) //判斷所傳入的ID，啟動相應的對話方塊
        {
            case check:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("儲存心得") //設定標題文字
                        .setMessage("確認發表") //設定內容文字
                        .setPositiveButton("確認", new DialogInterface.OnClickListener()
                        { //設定確定按鈕
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // TODO Auto-generated method stub

                                send();

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener()
                        { //設定取消按鈕
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // TODO Auto-generated method stub
                            }
                        });

                dialog = builder.create(); //建立對話方塊並存成 dialog
                break;

            case datacheck:
                AlertDialog.Builder changeBuilder = new AlertDialog.Builder(this);
                dialog = changeBuilder.create(); //建立對話方塊並存成 dialog
                break;

            default:
                break;
        }
        return dialog;
    }

    private void send() {
        String r_id = "22";
        String review =etReview.getText().toString();

        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                        Intent intent1 = new Intent(Review_add_Activity.this, ReviewActivity.class);
                        Review_add_Activity.this.startActivity(intent1);
                    } else {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Review_add_Activity.this);
                        builder.setMessage("Register Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Reviewadd save = new Reviewadd(r_id, review, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(Review_add_Activity.this);
        queue1.add(save);







        Intent intent = new Intent(this, Review_add_Activity.class);
        startActivity(intent);
        Review_add_Activity.this.finish();
        Toast.makeText(Review_add_Activity.this,"新增成功！", Toast.LENGTH_LONG).show();


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
        getMenuInflater().inflate(R.menu.review_add_, menu);
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