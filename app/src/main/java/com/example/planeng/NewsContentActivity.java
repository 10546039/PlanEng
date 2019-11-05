package com.example.planeng;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NewsContentActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        TextView tv1=(TextView)findViewById(R.id.txtitem);
        String Tempholder=getIntent().getStringExtra("Listviewclickvalue");
        tv1.setText(Tempholder);

        TextView tv2=(TextView)findViewById(R.id.txttitle);
        String Tempholder2=getIntent().getStringExtra("Listviewclickvalue2");
        tv2.setText(Tempholder2);
    }
}
