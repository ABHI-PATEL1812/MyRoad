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

import com.google.firebase.auth.FirebaseAuth;
import android.widget.Button;
public class ThankYou extends AppCompatActivity {
    ImageView i1;
    TextView t1,t2,t3,t4,t5;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        i1=(ImageView)findViewById(R.id.thankyou);
        t1=(TextView)findViewById(R.id.textView14);
        t2=(TextView)findViewById(R.id.textView15);
        t3=(TextView)findViewById(R.id.textView50);
        //t4=(TextView)findViewById(R.id.textView51);
        t5=(TextView)findViewById(R.id.textView17);
        b1=(Button)findViewById(R.id.button3);
        Bundle bundle=getIntent().getExtras();
        String Comp=bundle.getString("Comp");
        t5.setText(Comp);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThankYou.this,HomePage.class));
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                startActivity(new Intent(ThankYou.this,HomePage.class));
                break;
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                //finish();
                startActivity(new Intent(ThankYou.this,MainActivity.class));
                finish();
                break;
            case R.id.profile:
                startActivity(new Intent(ThankYou.this,Profile.class));
                break;
            case R.id.registeredcomplaint:
                startActivity(new Intent(ThankYou.this,CheckStatus.class));
            case R.id.feedback:
                startActivity(new Intent(ThankYou.this,FeedBack.class));
                break;
        }
        return true;
    }

}
