package com.example.myroad;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TakeFeedBack extends AppCompatActivity {
    String Comp;
    TextView t1,t2;
    EditText e1;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_feed_back);
        Intent intent=getIntent();
        String Complaintid=intent.getStringExtra("ComplaintID").trim();
        Comp="ComplaintID: "+Complaintid;
        t1=(TextView)findViewById(R.id.textView29);
        t2=(TextView)findViewById(R.id.textView30);
        e1=(EditText)findViewById(R.id.editText10);
        b1=(Button)findViewById(R.id.button14);
        t1.setText(Comp);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Complaints").child(Comp).child("complaintStatus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String cs=dataSnapshot.getValue(String.class);
                t2.setText("Complaint Status: "+cs);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String feedback=e1.getText().toString().trim();
                        FirebaseDatabase.getInstance().getReference("FeedBack").child(Comp).setValue(feedback);
                        startActivity(new Intent(TakeFeedBack.this,HomePage.class));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
