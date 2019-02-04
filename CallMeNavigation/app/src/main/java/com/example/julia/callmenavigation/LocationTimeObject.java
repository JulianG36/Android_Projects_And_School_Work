package com.example.julia.callmenavigation;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by julia on 3/20/2018.
 */

public class LocationTimeObject implements Serializable {
    public static LocationTimeObject instance;
    private ArrayList<singleLocationObject> LocationTimeObjectArray = new ArrayList();

    public static LocationTimeObject Instance(){
        if (instance == null){
            return instance = new LocationTimeObject();
            }
        else {
            return instance;
        }
    }
    public void resetInstance(){
        instance = new LocationTimeObject();
    }
    public void addSingleObject(double latitude, double longitude, String time, String date, Boolean checked,int ID){
        instance.LocationTimeObjectArray.add(new singleLocationObject(latitude,longitude,time,date,checked,ID));
    }
    public double getSingleLatitude(int index){
        return instance.LocationTimeObjectArray.get(index).getLatitude();
    }
    public double getSingleLongitude(int index){
        return instance.LocationTimeObjectArray.get(index).getLongitude();
    }
    public String getSingleTime(int index){
        return instance.LocationTimeObjectArray.get(index).getTime();
    }
    public String getSingleDate(int index){
        return instance.LocationTimeObjectArray.get(index).getDate();
    }
    public Boolean getSingleChecked(int index){
        return instance.LocationTimeObjectArray.get(index).getChecked();
    }
    public void setSingleChecked(int index, Boolean checked){
        instance.LocationTimeObjectArray.get(index).setChecked(checked);
    }
    public void setSingleID(int index, int ID){
        instance.LocationTimeObjectArray.get(index).setID(ID);
    }
    public Integer getSingleID(int index){
        return instance.LocationTimeObjectArray.get(index).getID();
    }
    public int getLength(){
        return LocationTimeObjectArray.size();
    }

    class singleLocationObject{
        private double latitude;
        private double longitude;
        private String date;
        private String time;
        private Boolean checked;
        private int ID;
        public singleLocationObject(double latitude, double longitude, String time, String date, Boolean checked, int ID){
            this.latitude = latitude;
            this.longitude = longitude;
            this.time = time;
            this.date = date;
            this.checked = checked;
            this.ID = ID;
        }
        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public Boolean getChecked() {
            return checked;
        }

        public void setChecked(Boolean checked) {
            this.checked = checked;
        }
        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }
}
