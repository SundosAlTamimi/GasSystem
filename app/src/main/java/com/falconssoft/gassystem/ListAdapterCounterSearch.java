package com.falconssoft.gassystem;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import com.falconssoft.gassystem.Modle.Customer;
import com.falconssoft.gassystem.Modle.Remarks;

import java.util.List;


public class ListAdapterCounterSearch extends BaseAdapter {
    private Context context;
    TextView noteText,currentReader;
    List<Customer> itemsList;
    Dialog dialog;

    public ListAdapterCounterSearch(Context context, List<Customer> itemsList, TextView noteText, Dialog dialog, TextView currentReader) {
        this.context = context;
        this.itemsList = itemsList;
this.dialog=dialog;
this.currentReader=currentReader;
        this.noteText=noteText;
    }

    public ListAdapterCounterSearch() {

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
        TextView counterNo,customerName,customerNo;
         TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_note_adaptir, null);

        holder.tableRow=  view.findViewById(R.id.table);
        holder.counterNo =  view.findViewById(R.id.tital);
        holder.customerName =  view.findViewById(R.id.body);
        holder.customerNo =  view.findViewById(R.id.cusNo);

        holder.counterNo.setText(itemsList.get(i).getCounterNo());
        holder.customerName.setText(itemsList.get(i).getCustName());
        holder.customerNo.setText(itemsList.get(i).getAccNo());
        holder.tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteText.setText(""+itemsList.get(i).getCounterNo());
                new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    currentReader.requestFocus();
                                }
                            });

                dialog.dismiss();
            }
        });

        return view;
    }

        }