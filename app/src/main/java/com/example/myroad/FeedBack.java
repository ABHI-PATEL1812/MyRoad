package com.example.myroad;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FeedBack extends AppCompatActivity {
    ListView listView;
    DatabaseReference db;
    String user= FirebaseAuth.getInstance().getCurrentUser().getUid();
    List<ComplaintDetails> complaintlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_status);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView=(ListView)findViewById(R.id.listview);
        complaintlist=new ArrayList<>();
        db=FirebaseDatabase.getInstance().getReference("Users/"+user+"/Complaints");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ComplaintDetails cp=complaintlist.get(position);
                Intent intent=new Intent(getApplicationContext(),TakeFeedBack.class);
                intent.putExtra("ComplaintID",cp.getComplaintId());
                intent.putExtra("ComplaintType",cp.getComplaintType());
                intent.putExtra("Latitude",cp.getLattitude());
                intent.putExtra("Longitude",cp.getLongitude());
                intent.putExtra("Description",cp.getDescription());
                intent.putExtra("User",user);
                startActivity(intent);
            }
        });


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            startActivity(new Intent(FeedBack.this,HomePage.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                complaintlist.clear();
                for(DataSnapshot complaintSnapshot : dataSnapshot.getChildren()){
                    ComplaintDetails complaintDetails=complaintSnapshot.getValue(ComplaintDetails.class);
                    complaintlist.add(complaintDetails);
                }
                ComplaintList adapter =new ComplaintList(FeedBack.this,complaintlist);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
