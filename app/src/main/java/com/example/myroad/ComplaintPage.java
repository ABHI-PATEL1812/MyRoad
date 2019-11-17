package com.example.myroad;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ComplaintPage extends AppCompatActivity {
    //static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_REQUEST_CODE=1;
//    private static final int RESULT_OK=1;
    TextView t1,t2;
    ImageView i1;
    Button b1;
    double lati,longi;

     String ComplaintId=generateID();
     String Comp="ComplaintID: "+ComplaintId;
    String currentDateandTime;
    StorageReference storage;
    String timestamp;
    String encodedimage;



    String user= FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_page);
        //mStorage= FirebaseStorage.getInstance().getReferenceFromUrl("gs://my-road-4ac13.appspot.com");
        t2=(TextView)findViewById(R.id.textView4);
        i1=(ImageView) findViewById(R.id.imageView);
        b1=(Button)findViewById(R.id.button6);
        if (!hascamera())
            i1.setEnabled(false);
        ActivityCompat.requestPermissions(ComplaintPage.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i1.isEnabled()==true){
                    Toast.makeText(ComplaintPage.this, "Please Upload an Image", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    Intent it = new Intent(ComplaintPage.this, MapsActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("Comp",Comp);
                    bundle.putString("Lattitude",String.valueOf(lati));
                    bundle.putString("Longitude",String.valueOf(longi));
                    bundle.putString("ComplaintId",ComplaintId);
                    bundle.putString("Date",currentDateandTime);
                    bundle.putString("Time",timestamp);
                    bundle.putString("Eimage",encodedimage);
                    it.putExtras(bundle);

                    startActivity(it);
                }
            }
        });
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i5=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i5,CAMERA_REQUEST_CODE);
                GPStracker g=new GPStracker(getApplicationContext());
                Location l=g.getLocation();
                double lat=l.getLatitude();
                double lon=l.getLongitude();
                lati=lat;
                longi=lon;
                SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                currentDateandTime=sdf.format(new Date());
                SimpleDateFormat time=new SimpleDateFormat("HH:mm:ss");
                timestamp=time.format(new Date());


                /*FirebaseDatabase.getInstance().getReference("Users/"+user)
                        .child("Complaints");
                FirebaseDatabase.getInstance().getReference("Users/"+user+"/Complaints")
                        .child(Comp);
                FirebaseDatabase.getInstance().getReference("Users/"+user+"/Complaints/"+Comp)
                        .child("ComplaintID").setValue(ComplaintId);
                FirebaseDatabase.getInstance().getReference("Users/"+user+"/Complaints/"+Comp)
                        .child("Lattitude").setValue(lat);
                FirebaseDatabase.getInstance().getReference("Users/"+user+"/Complaints/"+Comp)
                        .child("Longitude").setValue(lon);
                FirebaseDatabase.getInstance().getReference("Users/"+user+"/Complaints/"+Comp)
                        .child("Complaint Status").setValue(null);*/
                i1.setEnabled(false);



            }
        });
    }



    public boolean hascamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CAMERA_REQUEST_CODE && resultCode==RESULT_OK){

            Bundle extras=data.getExtras();
            Bitmap photo=(Bitmap) extras.get("data");
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG,100,baos);
            final byte[] dtaBAOS=baos.toByteArray();
            i1.setImageBitmap(photo);
            encodedimage = Base64.encodeToString(dtaBAOS, Base64.DEFAULT);


            DatabaseReference dref=FirebaseDatabase.getInstance().getReference();
            DatabaseReference reference=dref.child("Users").child(user).child("email");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String email=dataSnapshot.getValue(String.class);
                            StorageReference storageRef=FirebaseStorage.getInstance().getReferenceFromUrl("gs://my-road-4ac13.appspot.com/"+email);
                            StorageReference imagesRef=storageRef.child(ComplaintId);
                            UploadTask uploadTask=imagesRef.putBytes(dtaBAOS);

                            //FirebaseDatabase.getInstance().getReference("Users/"+user+"/Complaints/"+Comp)
                                    //.child("Image URL").setValue(imagesRef.child(ComplaintId).getDownloadUrl().toString());



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



        }
    }

    public  String generateID(){
        String id= String.valueOf(new Date().getTime());
        return id;

    }
}
