package com.example.planeng;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.planeng.Book.BookListActivity;
import com.example.planeng.Book.BookSetActivity;
import com.example.planeng.Book.CountDate;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //判斷是否登入
    boolean logon = false;
    //boolean logon = true;

    private String jsonResult;
    private String url = "http://108401.000webhostapp.com/information_main.php";
    private ListView listView;
    public ArrayList<String> content = new ArrayList<String>();
    public ArrayList<String> Title = new ArrayList<>();
    public ArrayList<String> Date = new ArrayList<>();
    String m_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        Intent IDintent =getIntent();
        m_id = IDintent.getStringExtra("m_id");


        listView = (ListView) findViewById(R.id.listView10);
        accessWebService();

        //判斷是否登入
        if (m_id==null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        ImageButton planPageBtn = (ImageButton)findViewById(R.id.b1);
        planPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this , PlanActivity.class);
                intent.putExtra("m_id", m_id);
                startActivity(intent);
            }
        });
        ImageButton notePageBtn = (ImageButton)findViewById(R.id.b2);
        notePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this , NoteActivity.class);
                intent.putExtra("m_id", m_id);
                startActivity(intent);
            }
        });

        ImageButton newsPageBtn = (ImageButton)findViewById(R.id.b3);
        newsPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this ,NewsActivity.class);

                startActivity(intent);
            }
        });

        ImageButton reviewPageBtn = (ImageButton)findViewById(R.id.b4);
        reviewPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this , ReviewActivity.class);
                intent.putExtra("m_id", m_id);
                startActivity(intent);
            }
        });
        getTask();








    }


    public void getTask() {
        Calendar mCal = Calendar.getInstance();

        String dbDate= CountDate.DateToString(CountDate.DateDemo(CountDate.DateToString(mCal.getTime())));

        if (m_id==null){
            TextView task = findViewById(R.id.todayTask);
            task.setText("尚未登入");
        }


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
                        TextView task = findViewById(R.id.todayTask);
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

        //Toast.makeText(MainActivity.this,m_id, Toast.LENGTH_LONG).show();
        //Toast.makeText(PlanActivity.this,dbDate, Toast.LENGTH_SHORT).show();

        getTask get = new getTask(m_id,dbDate, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this);
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
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void accessWebService() {
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

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String title = jsonChildNode.optString("i_title");
                content.add(jsonChildNode.optString("i_content"));
                Title.add(jsonChildNode.optString("i_title"));
                Date.add(jsonChildNode.optString("SUBSTR(i_date,1,10)"));
                String outPut = title ;


                informationList.add(createInformation("informations", outPut));

            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }

        final SimpleAdapter test = new SimpleAdapter(this, informationList,
                R.layout.listitem1,
                new String[] { "informations" }, new int[] { android.R.id.text1 }
        );

        listView.setAdapter(test);
        listView = (ListView) findViewById(R.id.listView10);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                content1(position);
            }

        });

    }

    private HashMap<String, String> createInformation(String title, String date) {
        HashMap<String, String> informationTitleDate = new HashMap<String, String>();
        informationTitleDate.put(title, date);
        return informationTitleDate;
    }

    public void content1(int position) {

        Log.d("content", content.get(position));
        Log.d("Title",Title.get(position));
        Log.d("Date",Date.get(position));
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