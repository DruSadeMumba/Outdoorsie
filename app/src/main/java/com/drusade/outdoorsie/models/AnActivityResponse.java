package com.drusade.outdoorsie.models;


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.List;

public class AnActivityResponse {

    private DatabaseReference mDatabaseReference;
    private Integer total;
    private List<AnActivity> activities = null;

    public AnActivityResponse() {
        mDatabaseReference = FirebaseDatabase
                .getInstance()
                .getReference(AnActivity.class.getSimpleName());
    }
    public Task<Void> add(AnActivity anAct)
    {
        return mDatabaseReference.push().setValue(anAct);
    }

    public Task<Void> update(String key, HashMap<String ,Object> hashMap)
    {
        return mDatabaseReference.child(key).updateChildren(hashMap);
    }
    public Task<Void> remove(String key)
    {
        return mDatabaseReference.child(key).removeValue();
    }

    public Query get(String key)
    {
        if(key == null)
        {
            return mDatabaseReference.orderByKey().limitToFirst(8);
        }
        return mDatabaseReference.orderByKey().startAfter(key).limitToFirst(8);
    }

    public Query get()
    {
        return mDatabaseReference;
    }
}

