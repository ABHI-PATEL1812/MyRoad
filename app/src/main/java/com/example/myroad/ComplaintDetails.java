package com.example.myroad;

public class ComplaintDetails {
    String complaintId,lattitude,longitude,complaintType,description,complaintStatus=null,date,time,encodedimage;
    int cid;

    public ComplaintDetails(){}
    public ComplaintDetails(String ComplaintId,String ComplaintType,String ComplaintStatus,String Lattitude,String Longitude,String Description,String Date,String Time,String Encodedimage,int Cid ) {
        this.complaintId = ComplaintId;
        this.lattitude = Lattitude;
        this.longitude = Longitude;
        this.complaintType = ComplaintType;
        this.description = Description;
        this.complaintStatus = ComplaintStatus;
        this.date=Date;
        this.time=Time;
        this.encodedimage=Encodedimage;
        this.cid=Cid;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }
    public void  setCid(int cid){
        this.cid=cid;
    }
    public int getCid(){return  cid;}
    public String getComplaintId() {
        return complaintId;
    }

    public String getLattitude() {
        return lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public String getDescription() {
        return description;
    }


    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public  String getEncodedimage(){ return  encodedimage;}
    public String getComplaintStatus() {
        return complaintStatus;
    }
}
