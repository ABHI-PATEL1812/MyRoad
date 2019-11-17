package com.example.myroad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Button;
public class UserComplaint extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15;
    ImageView i1;
    Button b1;
    String Comp;
    double lat,lon;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_complaint);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        t1=(TextView)findViewById(R.id.textView21);
        t2=(TextView)findViewById(R.id.textView22);
        t3=(TextView)findViewById(R.id.locoation);
        t4=(TextView)findViewById(R.id.locoation2);
        t5=(TextView)findViewById(R.id.locoation3);
        t6=(TextView)findViewById(R.id.locoation4);
        t11=(TextView)findViewById(R.id.locoation10);
        t12=(TextView)findViewById(R.id.locoation6);
        t13=(TextView)findViewById(R.id.locoation12);
        t14=(TextView)findViewById(R.id.locoation13);
        t15=(TextView)findViewById(R.id.locoation7);
        i1=(ImageView)findViewById(R.id.imageView6);
        b1=(Button)findViewById(R.id.button12);
        Intent intent=getIntent();
        String Complaintid=intent.getStringExtra("ComplaintID").trim();
        Comp="ComplaintID: "+Complaintid;
        String Complainttype=intent.getStringExtra("ComplaintType");
        String Description=intent.getStringExtra("Description");
        String latitude=intent.getStringExtra("Latitude");
        String longtitude=intent.getStringExtra("Longitude");
        lat=Double.parseDouble(latitude);
        lon=Double.valueOf(longtitude);
        user= FirebaseAuth.getInstance().getCurrentUser().getUid();
        t1.setText("Complaint ID:"+Complaintid);
        t2.setText("Complaint Type: "+Complainttype);
        t6.setText(Description);
        FirebaseDatabase.getInstance().getReference("Users/"+user+"/Complaints").child(Comp).child("cid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int complaints=dataSnapshot.getValue(Integer.class);
                //Log.i("Complaint",complaints);
                if(complaints==1){
                    b1.setEnabled(true);
                }else{
                    b1.setEnabled(false);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Users/"+user+"/Complaints").child(Comp).child("complaintStatus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String complaints=dataSnapshot.getValue(String.class).trim();
                Log.i("Complaint",complaints);

                t12.setText(complaints);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Users/"+user+"/Complaints").child(Comp).child("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String date=dataSnapshot.getValue(String.class);
                t14.setText(date);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Users/"+user+"/Complaints").child(Comp).child("encodedimage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String encodedimage=dataSnapshot.getValue(String.class);
                byte[] decodedString = Base64.decode(encodedimage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                i1.setImageBitmap(decodedByte);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UserComplaint.this,ShowImage.class);
                i.putExtra("Comp",Comp);
                startActivity(i);
            }
        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker at Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,17));
    }
}
