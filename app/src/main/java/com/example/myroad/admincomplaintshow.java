package com.example.myroad;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class admincomplaintshow extends ArrayAdapter<ComplaintDetails> {
    private Activity context;
    private List<ComplaintDetails> complaintlist;
    public admincomplaintshow(Activity context,List<ComplaintDetails> complaintlist){
        super(context,R.layout.adminshowcomplaints,complaintlist);
        this.context=context;
        this.complaintlist=complaintlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View listViewItem = layoutInflater.inflate(R.layout.adminshowcomplaints,null,true);
        TextView t1=(TextView)listViewItem.findViewById(R.id.ct1);
        TextView t2=(TextView)listViewItem.findViewById(R.id.cid1);
        TextView t3=(TextView)listViewItem.findViewById(R.id.cs3);
        TextView t4=(TextView)listViewItem.findViewById(R.id.cs4);
        ComplaintDetails complaintDetails=complaintlist.get(position);
        t1.setText("Complaint Id: "+complaintDetails.getComplaintId());
        t2.setText("Complaint Type: "+complaintDetails.getComplaintType());
        t4.setText(complaintDetails.getComplaintStatus());
        return listViewItem;
    }
}

