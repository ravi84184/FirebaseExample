package com.nikpatel.firebaseexample;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import java.util.*;
/**
 * Created by nikpatel on 17/09/17.
 */

public class FirebaseHelper {

    private static final String TAG = "FirebaseHelper";

    DatabaseReference db;
    Boolean saved;
    ArrayList<Model> models = new ArrayList<>();


    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public Boolean save(Model model){

        if (model == null){
            saved = false;
        }else {
            try {
                db.child("DATA").push().setValue(model);
                saved = true;
            }catch (DatabaseException e){
                e.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }

    private void fetchData(DataSnapshot dataSnapshot){
        models.clear();

        for (DataSnapshot ds: dataSnapshot.getChildren()){
            Model model = ds.getValue(Model.class);
            models.add(model);
        }
    }

    public ArrayList<Model> retrive(){
        Log.e(TAG, "retrive: startt---" );


        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return models;
    }


}
