package com.example.garageapp.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.garageapp.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Cars> cars;

    public MyAdapter(Context context, ArrayList<Cars> cars) {
        this.context = context;
        this.cars = cars;
    }

    @Override
    public int getCount() {
        return cars.size();
    }

    @Override
    public Object getItem(int position) {
        return cars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_view, null);
        }

        // Bind the object data to the views
        Cars car = cars.get(position);
        TextView make = view.findViewById(R.id.car_make);
        TextView model = view.findViewById(R.id.car_model);
        make.setText(car.getMakeName());
        model.setText(car.getModelName());
        return view;
    }
}

