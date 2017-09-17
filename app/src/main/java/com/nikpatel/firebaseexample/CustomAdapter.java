package com.nikpatel.firebaseexample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;
/**
 * Created by nikpatel on 17/09/17.
 */

public class CustomAdapter extends BaseAdapter {
    private static final String TAG = "CustomAdapter";
    Context mContext;
    ArrayList<Model> models;

    public CustomAdapter(Context mContext, ArrayList<Model> models) {
        this.mContext = mContext;
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int i) {
        return models.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.model,viewGroup,false);
        }

        TextView name = (TextView) view.findViewById(R.id.txtName);
        TextView address = (TextView) view.findViewById(R.id.txtAdd);
        TextView mobile = (TextView) view.findViewById(R.id.txtMobile);

        final Model m =(Model) this.getItem(i);

        name.setText(m.getName());
        address.setText(m.getAddress());
        mobile.setText(m.getMobile());
        Log.e(TAG, "getView: Name = "+m.getName() );
        Log.e(TAG, "getView: Add = "+m.getAddress() );
        Log.e(TAG, "getView: Mobile = "+m.getMobile() );
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, m.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
