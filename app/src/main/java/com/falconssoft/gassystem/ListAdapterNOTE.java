package com.falconssoft.gassystem;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;


import com.falconssoft.gassystem.Modle.Remarks;

import java.util.List;


public class ListAdapterNOTE extends BaseAdapter {
    CheckBox checkPriceed;
    private Context context;
    TextView noteText;
    List<Remarks> itemsList;
 String phoneNo,language;
    Dialog dialog;
    public ListAdapterNOTE(Context context, List<Remarks> itemsList, TextView noteText, Dialog dialog) {
        this.context = context;
        this.itemsList = itemsList;
this.dialog=dialog;
        this.noteText=noteText;
    }

    public ListAdapterNOTE() {

    }

    public void setItemsList(List<Remarks> itemsList) {
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
        TextView tital,body;//, price
TableRow tableRow;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.row_note_adaptir, null);

        holder.tableRow=  view.findViewById(R.id.table);
        holder.tital =  view.findViewById(R.id.tital);
        holder.body =  view.findViewById(R.id.body);


//        holder.state.setText("" + itemsList.get(i).getStatus());

        holder.tital.setText(itemsList.get(i).getTitle());
        holder.body.setText(itemsList.get(i).getBody());
        holder.tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteText.setText(""+itemsList.get(i).getBody());
                dialog.dismiss();
            }
        });

        return view;
    }

        }