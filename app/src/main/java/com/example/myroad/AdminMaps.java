package com.example.myroad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import android.widget.Button;

public class AdminMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

   TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12;
   ImageView i1;
   Button b1,b2,b3,b4,b5;
   double lat,lon;
    String Phone,Comp,user,uid;
   String updatestatus;
    String useridd;
    String name;
    String Complainttype,Userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.u_type,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        t1=(TextView)findViewById(R.id.textView21);
        t2=(TextView)findViewById(R.id.textView22);
        t3=(TextView)findViewById(R.id.locoation);
        t4=(TextView)findViewById(R.id.locoation2);
        t5=(TextView)findViewById(R.id.locoation3);
        t6=(TextView)findViewById(R.id.locoation4);
        t11=(TextView)findViewById(R.id.locoation10);

        i1=(ImageView)findViewById(R.id.imageView6);
        b1=(Button)findViewById(R.id.button8);
        b2=(Button)findViewById(R.id.button9);
        b3=(Button)findViewById(R.id.button10);
        b4=(Button)findViewById(R.id.button11);
        b5=(Button)findViewById(R.id.button12);



        Intent intent=getIntent();
        String Complaintid=intent.getStringExtra("ComplaintID").trim();
        Comp="ComplaintID: "+Complaintid;
        Complainttype=intent.getStringExtra("ComplaintType");
        String Description=intent.getStringExtra("Description");
        String latitude=intent.getStringExtra("Latitude");
        String longtitude=intent.getStringExtra("Longitude");
        lat=Double.parseDouble(latitude);
        lon=Double.valueOf(longtitude);
        t1.setText("Complaint ID:"+Complaintid);
        t2.setText("Complaint Type: "+Complainttype);
        t6.setText(Description);

        final ComplaintDetails cp=new ComplaintDetails();

        DatabaseReference dref=FirebaseDatabase.getInstance().getReference("All Complaints/complaints").child(Comp).child("UserEmail");
        DatabaseReference ref;
            dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String phone=dataSnapshot.getValue(String.class);
                //Toast.makeText(getApplicationContext(),phone,Toast.LENGTH_LONG).show();
                Phone=phone;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //FirebaseDatabase.getInstance().getReference("Users/"+Userid+"/Complaints").child(Comp)
        //Uri storageReference=FirebaseStorage.getInstance().getReferenceFromUrl("gs://my-road-4ac13.appspot.com/shahshubham60@gmail.com/1551096852954.jpeg").getDownloadUrl().getResult();
        //String st=storageReference.toString();
        //Toast.makeText(this,st,Toast.LENGTH_LONG).show();
// ImageView in your Activity
        //ImageView imageView = findViewById(R.id.imageView);

// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)

            //Glide.with(this).load(storageReference).into(i1);
            user= FirebaseAuth.getInstance().getCurrentUser().getUid();
            /*FirebaseDatabase.getInstance().getReference("Bhubneshwar/"+user+"/"+Comp).child("UserID").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final String Userid=dataSnapshot.getValue(String.class);

                    //Toast.makeText(getApplicationContext(),Userid,Toast.LENGTH_LONG).show();
                    FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            FirebaseDatabase.getInstance().getReference("Users/"+Userid+"/Complaints").child(Comp).child("complaintStatus").setValue(updatestatus);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/
        FirebaseDatabase.getInstance().getReference(user+"/All Complaints").child(Comp+"/UserID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String User=dataSnapshot.getValue(String.class);
                //Toast.makeText(AdminMaps.this, User+"686865", Toast.LENGTH_LONG).show();
                //Log.i("USerID:",User);
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users/"+User+"/Complaints").child(Comp);
                reference.child("encodedimage")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String encodedimage=dataSnapshot.getValue(String.class);
                        //Log.i("Encoded",encodedimage);
                        byte[] decodedString = Base64.decode(encodedimage, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        i1.setImageBitmap(decodedByte);
                        //Toast.makeText(getApplicationContext(),encodedimage,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });



        //t12.setText("User Email is: "+ Phone);
        /*StorageReference storageReference = FirebaseStorage.getInstance().getReference("gs://my-road-4ac13.appspot.com/shahshubham60@gmail.com/1551096852954.jpg");
        try {
            final File localFile = File.createTempFile("images", "jpg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    i1.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}*/
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db=FirebaseDatabase.getInstance().getReference(user+"/All Complaints").child(Comp);
                        db.child("/UserID").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Userid=dataSnapshot.getValue(String.class);

                        //Toast.makeText(getApplicationContext(),Userid,Toast.LENGTH_LONG).show();
                        FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                FirebaseDatabase.getInstance().getReference("Users/"+Userid+"/Complaints").child(Comp).child("complaintStatus").setValue("Rejected");
                                FirebaseDatabase.getInstance().getReference("Users/"+Userid+"/Complaints").child(Comp).child("cid").setValue(0);
                                FirebaseDatabase.getInstance().getReference("All Complaints/complaints").child(Comp).child("complaintStatus").setValue("Rejected");
                                FirebaseDatabase.getInstance().getReference(user+"/All Complaints").child(Comp).child("complaintStatus").setValue("Rejected");
                                b2.setEnabled(false);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DatabaseReference db1=FirebaseDatabase.getInstance().getReference(user+"/All Complaints").child(Comp);
                        db1.child("/UserID").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String Userid=dataSnapshot.getValue(String.class);

                        //Toast.makeText(getApplicationContext(),Userid,Toast.LENGTH_LONG).show();
                        FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                FirebaseDatabase.getInstance().getReference("Users/"+Userid+"/Complaints").child(Comp).child("complaintStatus").setValue("Received");
                                FirebaseDatabase.getInstance().getReference("Users/"+Userid+"/Complaints").child(Comp).child("cid").setValue(0);
                                FirebaseDatabase.getInstance().getReference("All Complaints/complaints").child(Comp).child("complaintStatus").setValue("Received");
                                //FirebaseDatabase.getInstance().getReference("Bhubneshwar/"+user+"/"+Comp).setValue("Received");
                                FirebaseDatabase.getInstance().getReference(user+"/All Complaints").child(Comp).child("complaintStatus").setValue("Received");
                                b3.setEnabled(false);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DatabaseReference db3= FirebaseDatabase.getInstance().getReference(user+"/All Complaints").child(Comp);
                        db3.child("/UserID").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String Userid=dataSnapshot.getValue(String.class);

                        //Toast.makeText(getApplicationContext(),Userid,Toast.LENGTH_LONG).show();
                        FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                FirebaseDatabase.getInstance().getReference("Users/"+Userid+"/Complaints").child(Comp).child("complaintStatus").setValue("On Progress");
                                FirebaseDatabase.getInstance().getReference("Users/"+Userid+"/Complaints").child(Comp).child("cid").setValue(0);
                                FirebaseDatabase.getInstance().getReference("All Complaints/complaints").child(Comp).child("complaintStatus").setValue("On Progress");
                                //if(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                FirebaseDatabase.getInstance().getReference("Users/"+Userid+"/Complaints").child(Comp).child("onProgress").setValue("Assigned to:");
                                //FirebaseDatabase.getInstance().getReference("Bhubneshwar/"+user+"/"+Comp).setValue("On Progress");
                                FirebaseDatabase.getInstance().getReference(user+"/All Complaints").child(Comp).child("complaintStatus").setValue("On Progress");
                                b4.setEnabled(false);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DatabaseReference db5= FirebaseDatabase.getInstance().getReference(user+"/All Complaints").child(Comp);
                        db5.child("/UserID").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String Userid=dataSnapshot.getValue(String.class);

                        //Toast.makeText(getApplicationContext(),Userid,Toast.LENGTH_LONG).show();
                        FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                FirebaseDatabase.getInstance().getReference("Users/"+Userid+"/Complaints").child(Comp).child("complaintStatus").setValue("Solved");
                                FirebaseDatabase.getInstance().getReference("Users/"+Userid+"/Complaints").child(Comp).child("cid").setValue(1);
                                FirebaseDatabase.getInstance().getReference("All Complaints/complaints").child(Comp).child("complaintStatus").setValue("Solved");
                                //FirebaseDatabase.getInstance().getReference("Bhubneshwar/"+user+"/"+Comp).setValue("Solved");
                                FirebaseDatabase.getInstance().getReference(user+"/All Complaints").child(Comp).child("complaintStatus").setValue("Solved");
                                Intent i7=new Intent(AdminMaps.this,AdminUpload.class);
                                i7.putExtra("User",user);
                                i7.putExtra("Comp",Comp);
                                i7.putExtra("ctype",Complainttype);
                                startActivity(i7);
                                b5.setEnabled(false);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)

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
        LatLng sydney = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker at Complaint Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,17));
    }
}
