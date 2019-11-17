package com.example.myroad;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminComplaints extends AppCompatActivity {
    ListView listView;
    DatabaseReference db;
    List<ComplaintDetails> complaintlist;
    String name;
    String user= FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaints);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView=(ListView)findViewById(R.id.listview1);
        complaintlist=new ArrayList<>();
       /*Toast.makeText(getApplicationContext(),user,Toast.LENGTH_LONG).show();
       Log.i("user",user);
        if(user=="prAQ6iAPI2XEctWOvlqDHBHE9pk2")
            name="Bhubneshwar";
        else if(user=="eqmEJltI31fOkQwC34ktqBAMf1Y2")
            name="Andman Nicobar";
        else if(user=="e2tlUwRxGeX0iAHO5lY8HsiIRjX2")
            name="Assam";
         Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();*/
        db= FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("All Complaints");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ComplaintDetails cp=complaintlist.get(position);
                Intent intent=new Intent(getApplicationContext(),AdminMaps.class);
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
                admincomplaintshow adapter =new admincomplaintshow(AdminComplaints.this,complaintlist);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
