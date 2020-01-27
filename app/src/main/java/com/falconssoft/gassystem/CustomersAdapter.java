package com.falconssoft.gassystem;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.falconssoft.gassystem.Modle.Customer;

import java.util.ArrayList;

public class CustomersAdapter extends ArrayAdapter<Customer> {
    public CustomersAdapter(Context context, ArrayList<Customer> customers) {
        super(context, 0 ,customers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Customer customer = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cust_name, parent, false);
        }


        TextView custName = (TextView) convertView.findViewById(R.id.cust);
        custName.setText(customer.getCustName());

        return convertView;
    }
}

