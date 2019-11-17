package com.example.myroad;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {
    TextView t1,t2,t3;
    EditText e1,e2,e3,e4;
    Button b1;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        t1=(TextView)findViewById(R.id.textView);
        t2=(TextView)findViewById(R.id.textView2);
        t3=(TextView)findViewById(R.id.textView3);
        e1=(EditText)findViewById(R.id.editText3);
        e2=(EditText)findViewById(R.id.editText4);
        e3=(EditText)findViewById(R.id.editText5);
        e4=(EditText)findViewById(R.id.editText6);
        b1=(Button)findViewById(R.id.button2);
        mAuth=FirebaseAuth.getInstance();

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(SignUp.this,MainActivity.class);
                startActivity(i1);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registeruser();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private  void registeruser(){
        final String name=e1.getText().toString().trim();
        String pas=e2.getText().toString().trim();
        final String phone=e3.getText().toString().trim();
        final String email=e4.getText().toString().trim();
        if(name.isEmpty()){
            e1.setError("Please Enter Your Name");
            e1.requestFocus();
            return;
        }
        if(pas.isEmpty()){
            e2.setError("Please Enter Password");
            e2.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            e3.setError("Please Enter Mobile Number");
            e3.requestFocus();
            return;
        }
        if(phone.length()!=10){
            e3.setError("Please Enter Valid Mobile Number");
            e3.requestFocus();
            return;
        }
        if(email.isEmpty()){
            e4.setError("Please Enter Email Address");
            e4.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            e4.setError("Please Enter Valid Email Address");
            e4.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Verification Email is sent to: "+email, Toast.LENGTH_LONG).show();
                        }
                    });
                    User user=new User(
                            name,
                            email,
                            phone
                    );
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                //Toast.makeText(SignUp.this,"Registered Successfully",Toast.LENGTH_LONG).show();
                                //Intent i2=new Intent(SignUp.this,MainActivity.class);
                                //startActivity(i2);
                            }
                        }
                        final FirebaseUser us=mAuth.getCurrentUser();

                    });
                }else{
                    Toast.makeText(SignUp.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
                Intent i2=new Intent(SignUp.this,MainActivity.class);
                startActivity(i2);
                //DatabaseReference myRef = database.getReference("message");

                //myRef.setValue("Hello, World!");

            }
        });

    }

}


