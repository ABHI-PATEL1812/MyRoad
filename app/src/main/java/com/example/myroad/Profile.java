package com.example.myroad;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    ImageView i1;
    TextView t1,t2,t3;
    DatabaseReference databaseReference;
    String user= FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        t1=(TextView)findViewById(R.id.textView16);
        t2=(TextView)findViewById(R.id.textView18);
        t3=(TextView)findViewById(R.id.textView19);
        i1=(ImageView)findViewById(R.id.imageView2);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference=databaseReference.child("Users").child(user).child("name");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Name=dataSnapshot.getValue(String.class);
                t1.setText("Name: "+Name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference emailref=databaseReference.child("Users").child(user).child("email");
        emailref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Email=dataSnapshot.getValue(String.class);
                t2.setText("Email: "+Email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference phoneref=databaseReference.child("Users").child(user).child("phone");
        phoneref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Phone=dataSnapshot.getValue(String.class);
                t3.setText("Phone No.: "+Phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
