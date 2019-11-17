package com.example.myroad;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Bhubneshwar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhubneshwar);
        FirebaseDatabase.getInstance().getReference("prAQ6iAPI2XEctWOvlqDHBHE9pk2").child("Accident").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final int sizea=(int)dataSnapshot.getChildrenCount();
                FirebaseDatabase.getInstance().getReference("prAQ6iAPI2XEctWOvlqDHBHE9pk2").child("Land Slides").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final int sizeb=(int)dataSnapshot.getChildrenCount();
                        FirebaseDatabase.getInstance().getReference("prAQ6iAPI2XEctWOvlqDHBHE9pk2").child("Road Damage").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final int sizec=(int)dataSnapshot.getChildrenCount();

                                FirebaseDatabase.getInstance().getReference("prAQ6iAPI2XEctWOvlqDHBHE9pk2").child("Road Damage").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        final int sized=(int)dataSnapshot.getChildrenCount();
                                        FirebaseDatabase.getInstance().getReference("prAQ6iAPI2XEctWOvlqDHBHE9pk2").child("Other").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                int sizee=(int)dataSnapshot.getChildrenCount();
                                                GraphView graph = (GraphView) findViewById(R.id.graph);
                                                //Toast.makeText(getApplicationContext(),sizes,Toast.LENGTH_LONG).show();
                                                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                                                        new DataPoint(2017, sizea),
                                                        new DataPoint(2018, sizeb),
                                                        new DataPoint(2019, sizec),
                                                        new DataPoint(2020,sized),
                                                        new DataPoint(2021,sizee)

                                                });
                                                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                                                staticLabelsFormatter.setHorizontalLabels(new String[] {"Accident", "Land Slides", "Road Damage","Pot Holes","Other"});
                                                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                                                graph.addSeries(series);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    }

