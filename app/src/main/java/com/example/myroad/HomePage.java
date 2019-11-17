package com.example.myroad;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class HomePage extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6;
    ImageView i1,i2,i3,i4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        t1=(TextView)findViewById(R.id.textView8);
        t2=(TextView)findViewById(R.id.textView9);
        t3=(TextView)findViewById(R.id.textView10);
        t4=(TextView)findViewById(R.id.textView11);
        t5=(TextView)findViewById(R.id.textView12);
        t6=(TextView)findViewById(R.id.textView13);
        i1=(ImageView)findViewById(R.id.imageView9);
        i2=(ImageView)findViewById(R.id.imageView10);
        i3=(ImageView)findViewById(R.id.imageView12);
        i4=(ImageView)findViewById(R.id.imageView13);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(HomePage.this,ComplaintPage.class);
                startActivity(i1);
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,CheckStatus.class));
            }
        });
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,FeedBack.class));
            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,Intermidiate.class));
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomePage.this,MainActivity.class));
                finish();
                break;
            case R.id.profile:
                startActivity(new Intent(HomePage.this,Profile.class));
                break;
            case R.id.registeredcomplaint:
                startActivity(new Intent(HomePage.this,CheckStatus.class));
                break;
            case R.id.feedback:
                startActivity(new Intent(HomePage.this,FeedBack.class));
                break;
        }
        return true;
    }
}
