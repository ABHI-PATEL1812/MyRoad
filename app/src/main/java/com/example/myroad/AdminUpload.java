package com.example.myroad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

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

public class AdminUpload extends AppCompatActivity {
    TextView t1;
    ImageView i1;
    Button b1;
    String encodedimage;
    String Comp;
    String user= FirebaseAuth.getInstance().getCurrentUser().getUid();
    String name,Complainttype;
    private static final int CAMERA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_upload);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        t1 = (TextView) findViewById(R.id.textView23);
        i1 = (ImageView) findViewById(R.id.imageView5);
        b1 = (Button) findViewById(R.id.button13);

        Intent intent=getIntent();
        Complainttype=intent.getStringExtra("ctype");
        Comp=intent.getStringExtra("Comp");
        Log.i("Comp",Comp);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i5 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i5, CAMERA_REQUEST_CODE);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            final byte[] dtaBAOS = baos.toByteArray();
            i1.setImageBitmap(photo);
            encodedimage = Base64.encodeToString(dtaBAOS, Base64.DEFAULT);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference(user+"/All Complaints").child(Comp+"/UserID").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String User=dataSnapshot.getValue(String.class);
                            Log.i("Email",User);
                            FirebaseDatabase.getInstance().getReference("Users/"+User+"/Complaints").child(Comp).child("SolvedImage").setValue(encodedimage);
                            FirebaseDatabase.getInstance().getReference("Users/"+User+"/Complaints").child(Comp).child("cid").setValue(1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    startActivity(new Intent(AdminUpload.this,AdminHome.class));
                    finish();

                }
            });

            //FirebaseDatabase.getInstance().getReference()
            /*DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
            DatabaseReference reference=dref.child("Users").child(user).child("email");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String email=dataSnapshot.getValue(String.class);
                    StorageReference storageRef= FirebaseStorage.getInstance().getReferenceFromUrl("gs://my-road-4ac13.appspot.com/"+email);
                    StorageReference imagesRef=storageRef.child(ComplaintId);
                    UploadTask uploadTask=imagesRef.putBytes(dtaBAOS);

                    //FirebaseDatabase.getInstance().getReference("Users/"+user+"/Complaints/"+Comp)
                    //.child("Image URL").setValue(imagesRef.child(ComplaintId).getDownloadUrl().toString());



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/


        }
    }
}