package com.example.myroad;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText e1,e2,e3;
    TextView t1,t2,t3,t4;
    Button b1;
    String ctype;
    Spinner spinner;
    ComplaintPage cp1=new ComplaintPage();
    //String ComplaintId=cp1.ComplaintId;
    String user=cp1.user;
    //String Comp="ComplaintID: "+ComplaintId;
    String Comp,lati,longi;
    String ComplaintStatus="Registered";
    String ComplaintId;
    String email;
    String cd,t;
    String encodedimage;
    int cid=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        e1=(EditText)findViewById(R.id.editText);
        t1=(TextView)findViewById(R.id.textView7);
        t2=(TextView)findViewById(R.id.textView8);
        b1=(Button)findViewById(R.id.button7);
        Bundle bundle=getIntent().getExtras();
        Comp=bundle.getString("Comp");
        lati=bundle.getString("Lattitude");
        longi=bundle.getString("Longitude");
        ComplaintId=bundle.getString("ComplaintId");
        cd=bundle.getString("Date");
        t=bundle.getString("Time");
        encodedimage=bundle.getString("Eimage");
        spinner = (Spinner) findViewById(R.id.c_type);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.c_type,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //ctype = String.valueOf(spinner.getSelectedItemId());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtodb();
                Toast.makeText(getApplicationContext(),"Submitted",Toast.LENGTH_LONG).show();

            }
        });
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {   Toast.makeText(getApplicationContext(),"You can not press back ",Toast.LENGTH_LONG).show();

            finish();
        }
        return super.onKeyDown(keyCode, event);
    }*/
    public void addtodb() {
        final String ComplaintType = spinner.getSelectedItem().toString().trim();
        String Description = e1.getText().toString().trim();

        if (Description.isEmpty()) {

        } else {
            ComplaintDetails complaintPage=new ComplaintDetails(
                    ComplaintId,
                    ComplaintType,
                    ComplaintStatus,
                    lati,
                    longi,
                    Description,
                    cd,
                    t,
                    encodedimage,
                    cid


            );
            DatabaseReference dref=FirebaseDatabase.getInstance().getReference();
            DatabaseReference db=FirebaseDatabase.getInstance().getReference(ComplaintType);
            db.child(Comp).setValue(complaintPage);
            db.child(Comp).child("cid").setValue(0);
            DatabaseReference reference=dref.child("Users").child(user).child("email");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String email=dataSnapshot.getValue(String.class);
                        FirebaseDatabase.getInstance().getReference("All Complaints/complaints").child(Comp).child("UserEmail").setValue(email);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            FirebaseDatabase.getInstance().getReference("Users/"+user).child("email").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String Email=dataSnapshot.getValue(String.class);
                    email=Email;
                    double latitude=Double.parseDouble(lati);
                    double longitude=Double.parseDouble(longi);

                    if(latitude>=15 && latitude <=25 && longitude>=80 && longitude<=90) {
                        FirebaseDatabase.getInstance().getReference("prAQ6iAPI2XEctWOvlqDHBHE9pk2").child(ComplaintType).child(Comp).child("User Email").setValue(Email);
                        FirebaseDatabase.getInstance().getReference("prAQ6iAPI2XEctWOvlqDHBHE9pk2").child("All Complaints").child(Comp).child("User Email").setValue(Email);
                    }
                    if(latitude>=12.879 && latitude<=13.238 && longitude>=92.877 && longitude<=92.960){
                        FirebaseDatabase.getInstance().getReference("eqmEJltI31fOkQwC34ktqBAMf1Y2").child(ComplaintType).child(Comp).child("User Email").setValue(Email);
                        FirebaseDatabase.getInstance().getReference("eqmEJltI31fOkQwC34ktqBAMf1Y2").child("All Complaints").child(Comp).child("User Email").setValue(Email);
                    }
                    if(latitude>=26.416 && latitude<=26.538 && longitude>=89.961 && longitude<=90.159){
                        FirebaseDatabase.getInstance().getReference("e2tlUwRxGeX0iAHO5lY8HsiIRjX2").child(ComplaintType).child(Comp).child("User Email").setValue(Email);
                        FirebaseDatabase.getInstance().getReference("e2tlUwRxGeX0iAHO5lY8HsiIRjX2").child("All Complaints").child(Comp).child("User Email").setValue(Email);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            FirebaseDatabase.getInstance().getReference("Users/" + user + "/Complaints/"+Comp).setValue(complaintPage);




            FirebaseDatabase.getInstance().getReference("All Complaints/complaints").child(Comp).setValue(complaintPage);
            double latitude=Double.parseDouble(lati);
            double longitude=Double.parseDouble(longi);
            if(latitude>=15 && latitude <=25 && longitude>=80 && longitude<=90){
                FirebaseDatabase.getInstance().getReference("prAQ6iAPI2XEctWOvlqDHBHE9pk2").child(ComplaintType).child(Comp).setValue(complaintPage);
                FirebaseDatabase.getInstance().getReference("prAQ6iAPI2XEctWOvlqDHBHE9pk2").child(ComplaintType).child(Comp).child("UserID").setValue(user);
                FirebaseDatabase.getInstance().getReference("prAQ6iAPI2XEctWOvlqDHBHE9pk2").child("All Complaints").child(Comp).setValue(complaintPage);
                FirebaseDatabase.getInstance().getReference("prAQ6iAPI2XEctWOvlqDHBHE9pk2").child("All Complaints").child(Comp).child("UserID").setValue(user);

            }
            if(latitude>=12.879 && latitude<=13.238 && longitude>=92.877 && longitude<=92.960){
                FirebaseDatabase.getInstance().getReference("eqmEJltI31fOkQwC34ktqBAMf1Y2").child(ComplaintType).child(Comp).setValue(complaintPage);
                FirebaseDatabase.getInstance().getReference("eqmEJltI31fOkQwC34ktqBAMf1Y2").child(ComplaintType).child(Comp).child("UserID").setValue(user);
                FirebaseDatabase.getInstance().getReference("eqmEJltI31fOkQwC34ktqBAMf1Y2").child("All Complaints").child(Comp).setValue(complaintPage);
                FirebaseDatabase.getInstance().getReference("eqmEJltI31fOkQwC34ktqBAMf1Y2").child("All Complaints").child(Comp).child("UserID").setValue(user);
            }
            if(latitude>=26.416 && latitude<=26.538 && longitude>=89.961 && longitude<=90.159){
                FirebaseDatabase.getInstance().getReference("e2tlUwRxGeX0iAHO5lY8HsiIRjX2").child(ComplaintType).child(Comp).setValue(complaintPage);
                FirebaseDatabase.getInstance().getReference("e2tlUwRxGeX0iAHO5lY8HsiIRjX2").child(ComplaintType).child(Comp).child("UserID").setValue(user);
                FirebaseDatabase.getInstance().getReference("e2tlUwRxGeX0iAHO5lY8HsiIRjX2").child("All Complaints").child(Comp).setValue(complaintPage);
                FirebaseDatabase.getInstance().getReference("e2tlUwRxGeX0iAHO5lY8HsiIRjX2").child("All Complaints").child(Comp).child("UserID").setValue(user);

            }
   /*FirebaseDatabase.getInstance().getReference("Users/" + user + "/Complaints/" + Comp)
                    .child("Complaint Type").setValue(ComplaintType);

            FirebaseDatabase.getInstance().getReference("Users/" + user + "/Complaints/" + Comp)
                    .child("Description").setValue(Description);
            FirebaseDatabase.getInstance().getReference("Users/" + user + "/Complaints/" + Comp)
                    .child("Address").setValue(Address);
            FirebaseDatabase.getInstance().getReference("Users/" + user + "/Complaints/" + Comp)
                    .child("Pin Code").setValue(PinCode);*/
            Intent i1=new Intent(MapsActivity.this,ThankYou.class);
            Bundle bundle=new Bundle();
            bundle.putString("Comp",Comp);
            i1.putExtras(bundle);
            startActivity(i1);
        }
    }
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(MapsActivity.this,HomePage.class));
            //moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
    /*@Override
    public void onBackPressed() {

        Toast.makeText(getApplicationContext(),"You can not press Back",Toast.LENGTH_LONG).show();
    }*/

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
        ComplaintPage cp=new ComplaintPage();
        GPStracker g=new GPStracker(getApplicationContext());
        Location l=g.getLocation();
        double lat=l.getLatitude();
        double lon=l.getLongitude();



        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker at Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,17));
    }
}
