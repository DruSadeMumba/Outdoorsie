package com.drusade.outdoorsie.models;

public class AnActivity {
    private String activityName;
    private String location;
    private long date;
    private String keys;
    private String pushId;

    public AnActivity() {
    }

    public AnActivity(String activityName, String location, long date) {
        this.activityName = activityName;
        this.location = location;
        this.date = date;
    }

    //Getters
    public String getActivityName() {return activityName;}
    public String getLocation() {return location;}
    public long getDate() {return date;}
    public String getKeys()
    {
        return keys;
    }
    public String getPushId() {return pushId;}

    //Setters
    public void setActivityName(String activityName) {this.activityName = activityName;}
    public void setLocation(String location) {this.location = location;}
    public void setDate(long date) {this.date = date;}
    public void setKeys(String key)
    {
        this.keys = keys;
    }
    public void setPushId(String pushId) {this.pushId = pushId;}
}
