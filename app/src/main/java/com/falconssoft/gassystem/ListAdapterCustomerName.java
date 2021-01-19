package com.falconssoft.gassystem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import com.falconssoft.gassystem.Modle.Customer;
import com.falconssoft.gassystem.Modle.Remarks;

import java.util.List;


public class ListAdapterCustomerName extends BaseAdapter {
    CheckBox checkPriceed;
    private Receipt context;
    private Context contexts;
    TextView customerText;
    List<Customer> itemsList;
    int flag;
    String phoneNo, language;
    Dialog dialog;

    public ListAdapterCustomerName(Receipt context, List<Customer> itemsList, TextView customerText, Dialog dialog, int flag, Context contexts) {
        this.context = context;
        this.itemsList = itemsList;
        this.dialog = dialog;
        this.flag = flag;

        this.contexts = contexts;

        this.customerText = customerText;
    }

    public ListAdapterCustomerName() {

    }

    public void setItemsList(List<Customer> itemsList) {
        this.itemsList = itemsList;

    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView tital, coustNo, countNo;//, price

        TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_customer_adaptir, null);

        holder.tableRow = view.findViewById(R.id.table);
        holder.tital = view.findViewById(R.id.tital);
        holder.countNo = view.findViewById(R.id.countNo);
        holder.coustNo = view.findViewById(R.id.coustNo);

//        holder.state.setText("" + itemsList.get(i).getStatus());

        holder.tital.setText(itemsList.get(i).getCustName());
        holder.countNo.setText(""+itemsList.get(i).getCounterNo());
        holder.coustNo.setText(""+itemsList.get(i).getAccNo());
        holder.tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerText.setText("" + itemsList.get(i).getCustName());
                if (flag == 1) {
                    context.fillRecCash(itemsList.get(i));
                }

                dialog.dismiss();
            }
        });

        return view;
    }

}