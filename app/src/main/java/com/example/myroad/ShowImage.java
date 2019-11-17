package com.example.myroad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowImage extends AppCompatActivity {
    ImageView i1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        i1=(ImageView)findViewById(R.id.imageView7);
        String User= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent intent=getIntent();
        String Comp=intent.getStringExtra("Comp");
        FirebaseDatabase.getInstance().getReference("Users/"+User+"/Complaints").child(Comp)
        .child("SolvedImage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String encodedimage=dataSnapshot.getValue(String.class);
                Log.i("Encoded",encodedimage);
                byte[] decodedString = Base64.decode(encodedimage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                i1.setImageBitmap(decodedByte);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
