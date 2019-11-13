package com.example.planeng.Book;

import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;





public class BookEditActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView booktitle;
    TextView CountTotalDay;
    Date startDate;
    Date endDate;
    private LinearLayout mLinear;
    //放置標題的集合
    private List<String> chapNum;
    List<String> chapName;
    List<Integer> textid;
    List<Integer> chapPlanDayid;
    int avDay;
    String SavDay;
    int totalChap;
    Long planDay;
    List<Integer> IeachChap;
    String m_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setTitle("新增書籍");
        initView();
        initData();

        Intent editIntent =getIntent();
        Bundle bundle = editIntent.getExtras();
        m_id=bundle.getString("m_id",null);
        //Toast.makeText(BookEditActivity.this,m_id, Toast.LENGTH_LONG).show();
        String bookname = bundle.getString("book_name",null);
        booktitle = findViewById(R.id.booktitle);
        booktitle.setText(bookname);
        totalChap = bundle.getInt("totalChap",1);
        final TextView totalPlanDate = findViewById(R.id.planDay);
        planDay = bundle.getLong("totalPlanDate");
        totalPlanDate.setText(planDay.toString());
        CountTotalDay = findViewById(R.id.CountPlanDay);
        Integer countTotal = 0;
        for (int i = 0; i < totalChap; i++) {
            EditText EchapDay = findViewById(chapPlanDayid.get(i));
            String Schapday = EchapDay.getText().toString();
            countTotal = countTotal + Integer.parseInt(Schapday);
        }
        CountTotalDay.setText(countTotal.toString());
        t.start();


        final String tStartDate = bundle.getString("startDate",null);
        startDate = CountDate.DateDemo(tStartDate);
        String tEndDate = bundle.getString("endDate",null);
        endDate = CountDate.DateDemo(tEndDate);

        ImageButton saveBook = findViewById(R.id.saveBook);
        saveBook.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                getStartDate();
                int IcountTotal=Integer.parseInt(CountTotalDay.getText().toString());
                int IplanTotal=Integer.parseInt(totalPlanDate.getText().toString());
                if (IcountTotal<IplanTotal){
                    showDialog(changeEndDay);
                }else if (IcountTotal>IplanTotal){
                    showDialog(changeEndDay);

                }else{
                    showDialog(check);
                    //Toast.makeText(BookEditActivity.this,""+chapName.get(0)+"", Toast.LENGTH_LONG).show();


                }



            }
        });



    }


    public Long getPlanDay() {
        Intent editIntent =getIntent();
        Bundle bundle = editIntent.getExtras();
        planDay = bundle.getLong("totalPlanDate");
        return planDay;
    }
    public int getTotalChap() {
        Intent editIntent =getIntent();
        Bundle bundle = editIntent.getExtras();
        totalChap = bundle.getInt("totalChap",0);
        return totalChap;
    }
    public int getAvChapday() {
        int AvChapday=(int)Math.floor( getPlanDay()/getTotalChap());
        return AvChapday;
    }

    String tEndDate;
    String tStartDate;

    public Date getStartDate() {
        Intent editIntent =getIntent();
        Bundle bundle = editIntent.getExtras();
        tStartDate = bundle.getString("startDate",null);
        startDate = CountDate.DateDemo(tStartDate);
        tEndDate = bundle.getString("endDate",null);
        endDate = CountDate.DateDemo(tEndDate);
        return startDate;
    }
    public Date getEndDate() {
        Intent editIntent =getIntent();
        Bundle bundle = editIntent.getExtras();
        tStartDate = bundle.getString("startDate",null);
        startDate = CountDate.DateDemo(tStartDate);
        tEndDate = bundle.getString("endDate",null);
        endDate = CountDate.DateDemo(tEndDate);
        return endDate;
    }



    private void initView() {
        //要新增view的容器
        mLinear = findViewById(R.id.linear);
        chapNum = new ArrayList<>();
        textid =new ArrayList<>();
        chapPlanDayid = new ArrayList<>();
        getAvChapday();
        getTotalChap();
    }

    /**
     * 處理資料,可以是伺服器請求過來的,也可以是本地的
     */

    private void initData() {
        getTotalChap();
        getAvChapday();
        for (int i = 0; i < totalChap; i++) {
            chapNum.add(""+(i+1)+"");
            textid.add(i);
        }

        //資料拿到之後去根據資料去動態新增View
        addView();
    }

    /**
     * 動態新增的具體實現
     */
    EditText chName;
    EditText chDay;
    private void addView() {
        //ivList集合有幾個元素就新增幾個

        getTotalChap();
        avDay=getAvChapday();
        SavDay=Integer.toString(avDay);
        for (int i = 0; i < totalChap; i++) {
            //首先引入要新增的View
            View view = View.inflate(this, R.layout.chap, null);
            //找到裡面需要動態改變的控制元件
            chDay=view.findViewById(R.id.ch_day);
            chDay.setText(SavDay);

            chDay.setId(i+200);
            chapPlanDayid.add(chDay.getId());

            TextView chapter =  view.findViewById(R.id.chapter);

            chName= view.findViewById(R.id.chapDetail);
            chName.setId(textid.get(i));

            //給控制元件賦值
            //取得章節
            chapter.setText(chapNum.get(i));
            //動態給每個View設定margin,也可以在xml裡面設定,xml裡面設定後每個view之間的間距都是一樣的
            //動態設定可以給每個view之間的間距設定的不一樣 params.setMargins(int left, int top, int right, int bottom);
            //
            //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
             //       (LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            //view.setLayoutParams(params);
            //params.setMargins(24,0,24,0);
            //view.setTag(i);
            //把所有動態建立的view都新增到容器裡面
            mLinear.addView(view);
        }

    }


    String SchapDay;
    //已安排天數即時更新
    Thread t = new Thread() {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Thread.sleep(100);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // update TextView here!
                            countTotal = 0;
                            for (int i = 0; i < totalChap; i++) {

                                EditText EchapDay = findViewById(chapPlanDayid.get(i));

                                if (EchapDay.getText().toString().equals("")){
                                    SchapDay="0";
                                    //EchapDay.setText("0");
                                }else {
                                    SchapDay=EchapDay.getText().toString();
                                }
                                countTotal = countTotal + Integer.parseInt(SchapDay);
                            }
                            CountTotalDay.setText(countTotal.toString());


                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    };

    Integer countTotal;

    //儲存書籍確認訊息

    final int check = 1000;
    final int changeEndDay = 2000;
    @Override
    protected Dialog onCreateDialog(int id)  //初始化對話方塊，透過 showDialog(ID) 觸發
    {
        Dialog dialog = null;
        chapName =new ArrayList<>();

        //取得章節內容
        for (int i = 0; i < totalChap; i++) {
            EditText chap = findViewById(textid.get(i));
            String Schap =chap.getText().toString();
            chapName.add("第"+(i+1)+"章 "+Schap);
        }


        switch(id) //判斷所傳入的ID，啟動相應的對話方塊
        {
            case check:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                getEndDate();
                getStartDate();
                builder.setTitle("儲存書籍") //設定標題文字
                        .setMessage("確認加入 [ "+booktitle.getText().toString()+" ] "+System.getProperty("line.separator")
                                    +"閱讀日期： "+tStartDate+" ~ "+tEndDate) //設定內容文字
                        .setPositiveButton("確認", new DialogInterface.OnClickListener()
                        { //設定確定按鈕
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // TODO Auto-generated method stub
                                booklist();
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

            case changeEndDay:
                getEndDate();
                getStartDate();
                AlertDialog.Builder changeBuilder = new AlertDialog.Builder(this);
                int countTotal = 0;
                for (int i = 0; i < totalChap; i++) {

                    EditText EchapDay = findViewById(chapPlanDayid.get(i));
                    String tchapDay;
                    if (EchapDay.getText().toString().equals("")){
                        tchapDay="0";
                        //EchapDay.setText("0");
                    }else {
                        tchapDay=EchapDay.getText().toString();
                    }
                    countTotal = countTotal + Integer.parseInt(tchapDay);
                }
                String sEndDate = CountDate.DateToString(CountDate.DatePlusInt(startDate,countTotal-1));

                changeBuilder.setTitle("已安排天數 不足/超過") //設定標題文字
                        .setMessage(
                                "閱讀日期將修改成： "+System.getProperty("line.separator")
                                +System.getProperty("line.separator")
                                +"  "+tStartDate+" ~ "+sEndDate+System.getProperty("line.separator")
                                +System.getProperty("line.separator")
                                +"是否確認加入 [ "+booktitle.getText().toString()+" ] ？") //設定內容文字
                        .setPositiveButton("確認", new DialogInterface.OnClickListener()
                        { //設定確定按鈕
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // TODO Auto-generated method stub
                                booklist();
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

                dialog = changeBuilder.create(); //建立對話方塊並存成 dialog
                break;

            default:
                break;
        }
        return dialog;
    }



    private void booklist() {
        //m_id
        //bookname
        //startdate
        //enddate
        //totalChap
        //chapname


        for (int j = 0; j < totalChap; j++)
        {

            getStartDate();
            chapName = new ArrayList<>();
            for (int i = 0; i < totalChap; i++) {
                EditText chap = findViewById(textid.get(i));
                String Schap = chap.getText().toString();
                chapName.add("第" + (i + 1) + "章 " + Schap);
            }



            //String m_id = "163";
            String bookname = booktitle.getText().toString();
            String startdate = CountDate.DateToString(startDate);
            String enddate = CountDate.DateToString(CountDate.DatePlusInt(startDate,countTotal-1));
            String totalchap = String.valueOf(totalChap);
            String chapname = chapName.get(j);



            Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                @Override
                public void onResponse(String response1) {
                    try {
                        JSONObject jsonResponse2 = new JSONObject(response1);
                        boolean success = jsonResponse2.getBoolean("success");
                        if (success) {

                            //Intent intent2 = new Intent(BookEditActivity.this, BookSetActivity.class);
                            //BookEditActivity.this.startActivity(intent2);



                        } else {
                            android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(BookEditActivity.this);
                            builder1.setMessage("Register Failed")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            addBooklist addList = new addBooklist(m_id, bookname, startdate, enddate,totalchap,chapname, responseListener2);
            RequestQueue queue2 = Volley.newRequestQueue(BookEditActivity.this);
            queue2.add(addList);



        }



    }




private void send() {



    Date day = CountDate.DateM(startDate);
    for (int j = 0; j < totalChap; j++)
    {
        //各章節天數
        IeachChap = new ArrayList<>();
        for (int i = 0; i < totalChap; i++) {
            EditText EchapDay = findViewById(chapPlanDayid.get(i));
            IeachChap.add(Integer.parseInt(EchapDay.getText().toString()));
        }



        for (int k = 0; k < IeachChap.get(j); k++) {

            getStartDate();
            chapName = new ArrayList<>();
            for (int i = 0; i < totalChap; i++) {
                EditText chap = findViewById(textid.get(i));
                String Schap = chap.getText().toString();
                chapName.add("第" + (i + 1) + "章 " + Schap);
            }

            day = CountDate.DatePlusInt(day, 1);

            //String m_id = "163";
            String bookname = booktitle.getText().toString();
            String date = CountDate.DateToString(day);
            String chap = chapName.get(j);


            Response.Listener<String> responseListener1 = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {

                            Intent intent1 = new Intent(BookEditActivity.this, BookSetActivity.class);

                            BookEditActivity.this.startActivity(intent1);



                        } else {
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(BookEditActivity.this);
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

            addBook save = new addBook(m_id, bookname, date, chap, responseListener1);
            RequestQueue queue1 = Volley.newRequestQueue(BookEditActivity.this);
            queue1.add(save);





        }
    }
    Intent intent = new Intent(this, BookSetActivity.class);

    intent.putExtra("m_id", m_id);

    startActivity(intent);
    BookEditActivity.this.finish();
    String bookname = booktitle.getText().toString();
    Toast.makeText(BookEditActivity.this,"[ "+bookname+" ] "+"書籍新增成功！", Toast.LENGTH_LONG).show();


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
    //側邊MENU
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
