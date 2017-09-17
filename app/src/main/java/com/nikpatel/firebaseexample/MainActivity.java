package com.nikpatel.firebaseexample;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView lv;
    Toolbar toolbar;
    EditText edtName,edtAddress,edtMobile;
    Button btnAdd,btnRetrive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRetrive = (Button) findViewById(R.id.btnRetrive);


        lv = (ListView) findViewById(R.id.listView);

        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db);



        btnRetrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrive();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });

    }

    private void retrive() {
        Log.e(TAG, "retrive: Retrive Button Clicked " );
        adapter = new CustomAdapter(this, helper.retrive());
        lv.setAdapter(adapter);
    }

    private void addData() {

        final Dialog d =new Dialog(this);
        d.setTitle("Save to Firebase");
        d.setContentView(R.layout.inputdialog);


        edtName = (EditText) d.findViewById(R.id.edtName);
        edtAddress = (EditText) d.findViewById(R.id.edtAdd);
        edtMobile = (EditText) d.findViewById(R.id.edtMobile);

        Button save = (Button) d.findViewById(R.id.btnSave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(TAG, "onClick: name = "+edtName.getText().toString());
                Log.e(TAG, "onClick: address = "+edtAddress.getText().toString());
                Log.e(TAG, "onClick: mobile = "+edtMobile.getText().toString() );
                String name = edtName.getText().toString();
                String address = edtAddress.getText().toString();
                String  mobile = edtMobile.getText().toString();

                Model m = new Model();

                m.setName(name);
                m.setAddress(address);
                m.setMobile(mobile);

                if (name.length() > 0){
                    if (helper.save(m)){
                        edtName.setText("");
                        edtAddress.setText("");
                        edtMobile.setText("");

                        adapter = new CustomAdapter(MainActivity.this,helper.retrive());
                        lv.setAdapter(adapter);
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
                d.cancel();
            }
        });
        d.show();
    }
}
