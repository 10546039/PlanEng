package com.example.planeng;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class NewsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String jsonResult;
    private String url = "http://108401.000webhostapp.com/information_details.php";
    private String url1="http://108401.000webhostapp.com/information_details_toeic.php";
    private String url2="http://108401.000webhostapp.com/information_details_ielts.php";
    private String url3="http://108401.000webhostapp.com/information_details_toefl.php";

    private ListView listView;
    public ArrayList<String> content = new ArrayList<String>();
    public ArrayList<String> Title = new ArrayList<>();
    public ArrayList<String> Date = new ArrayList<>();
    private ImageButton btn1;
    private ImageButton btn2;
    private ImageButton btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setTitle("");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        listView = (ListView) findViewById(R.id.listView1);
        accessWebService(url);
        btn1 = (ImageButton) findViewById(R.id.imageButton10);
        btn2 = (ImageButton) findViewById(R.id.imageButton11);
        btn3 = (ImageButton) findViewById(R.id.imageButton12);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accessWebService(url1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accessWebService(url2);

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accessWebService(url3);

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
            // Handle the camera action
        } else if (id == R.id.nav_book) {

        } else if (id == R.id.nav_note) {

        } else if (id == R.id.nav_review) {

        } else if (id == R.id.nav_plan) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
            }

            catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            }

            catch (IOException e) {
                // e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            ListDrwaer();
        }
    }// end async task

    public void accessWebService(String url) {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[] { url });
    }

    // build hash set for list view
    public void ListDrwaer() {
        final List<Map<String, String>> informationList = new ArrayList<Map<String, String>>();
        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("information");
            content.clear();
            Title.clear();
            Date.clear();
            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                String title = jsonChildNode.optString("i_title");
                content.add(jsonChildNode.optString("i_content"));
                Title.add(jsonChildNode.optString("i_title"));
                Date.add(jsonChildNode.optString("SUBSTR(i_date,1,10)"));

                String outPut =title;


                informationList.add(createInformation("informations", outPut));


            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }

        final SimpleAdapter test = new SimpleAdapter(this, informationList,
                R.layout.listitem2,
                new String[] { "informations"}, new int[] { android.R.id.text1});

        listView.setAdapter(test);
        listView = (ListView) findViewById(R.id.listView1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                content1(position);
            }

        });

    }

    private HashMap<String, String> createInformation(String title, String date) {
        HashMap<String, String>informationTitleDate = new HashMap<String, String>();
        informationTitleDate.put(title, date);
        return informationTitleDate;
    }

    public void content1(int position) {

        Log.d("content", content.get(position));
        Log.d("Title",Title.get(position));
        Log.d("date",Date.get(position));
        String Templistview = content.get(position).toString();
        String Templistview2 = Title.get(position).toString();
        String Templistview3 = Date.get(position).toString();
        Intent intent = new Intent(this, NewsContentActivity.class);
        intent.putExtra("Listviewclickvalue", Templistview);
        intent.putExtra("Listviewclickvalue2", Templistview2);
        intent.putExtra("Listviewclickvalue3", Templistview3);
        startActivity(intent);
    }


}
