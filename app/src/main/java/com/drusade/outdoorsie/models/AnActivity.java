package com.drusade.outdoorsie.models;

public class AnActivity {
    private String activityName;
    private String location;
    private long date;
    private String key;

    public AnActivity() {
    }

    public AnActivity(String activityName, String location) {
        this.activityName = activityName;
        this.location = location;
    }

    //Getters
    public String getActivityName() {return activityName;}
    public String getLocation() {return location;}
    public long getDate() {return date;}
    public String getKey()
    {
        return key;
    }

    //Setters
    public void setActivityName(String activityName) {this.activityName = activityName;}
    public void setLocation(String location) {this.location = location;}
    public void setDate(long date) {this.date = date;}
    public void setKey(String key)
    {
        this.key = key;
    }
}
