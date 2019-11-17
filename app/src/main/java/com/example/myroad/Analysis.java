package com.example.myroad;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hadiidbouk.charts.ChartProgressBar;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class Analysis extends AppCompatActivity {
    private ChartProgressBar mChart;
    int a,b,c,d,sizes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        /*GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(2017, 1),
                new DataPoint(2018, 5),
                new DataPoint(2019, 3),
                new DataPoint(2020, 2),
                new DataPoint(2021, 6)
        });
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"old", "middle", "new"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.addSeries(series);*/
        /*ArrayList<BarData> dataList = new ArrayList<>();

        BarData data = new BarData("Sep", 3.4f, "3.4€");
        dataList.add(data);

        data = new BarData("Oct", 8f, "8€");
        dataList.add(data);

        data = new BarData("Nov", 1.8f, "1.8€");
        dataList.add(data);

        data = new BarData("Dec", 7.3f, "7.3€");
        dataList.add(data);

        data = new BarData("Jan", 6.2f, "6.2€");
        dataList.add(data);

        data = new BarData("Feb", 3.3f, "3.3€");
        dataList.add(data);

        mChart = (ChartProgressBar) findViewById(R.id.ChartProgressBar);

        mChart.setDataList(dataList);
        mChart.build();*/
        /*barChart=(BarChart)findViewById(R.id.chart);
        ArrayList<BarEntry> barEntries=new ArrayList<>();
        barEntries.add(new BarEntry(44f,0));
        barEntries.add(new BarEntry(44f,1));
        barEntries.add(new BarEntry(44f,2));
        barEntries.add(new BarEntry(44f,3));
        BarDataSet barDataSet=new BarDataSet(barEntries,"Dates");
        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("Accident");
        theDates.add("Accident");
        theDates.add("Accident");
        theDates.add("Accident");
        BarData theData=new BarData(theDates,);*/
        FirebaseDatabase.getInstance().getReference().child("Accident").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final int sizea=(int)dataSnapshot.getChildrenCount();
                FirebaseDatabase.getInstance().getReference().child("Land Slides").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final int sizeb=(int)dataSnapshot.getChildrenCount();
                        FirebaseDatabase.getInstance().getReference().child("Road Damage").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final int sizec=(int)dataSnapshot.getChildrenCount();

                                FirebaseDatabase.getInstance().getReference().child("Road Damage").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        final int sized=(int)dataSnapshot.getChildrenCount();
                                        FirebaseDatabase.getInstance().getReference().child("Other").addValueEventListener(new ValueEventListener() {
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
