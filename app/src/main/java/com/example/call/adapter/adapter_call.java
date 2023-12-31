package com.example.call.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.call.R;
import com.example.call.activity.HomeActivity;
import com.example.call.model.Call;
import com.example.call.util.DataClass;

import java.util.ArrayList;
import java.util.List;

public class adapter_call extends BaseAdapter implements Filterable {
    private Context context;
    private int layout;
    private List<Call> callList;
    private List<Call> getCallListlist;

    public adapter_call(Context context, int layout, List<Call> callList) {
        this.context = context;
        this.layout = layout;
        this.callList = callList;
        this.getCallListlist = callList;
    }

    @Override
    public int getCount() {
        return callList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView txtuser = view.findViewById(R.id.txtuser);
        TextView txtsdt = view.findViewById(R.id.txtsdt);
        TextView txtusersingle = view.findViewById(R.id.txtusersingle);
        Call calls = callList.get(i);
        txtuser.setText(calls.getTen());
        txtsdt.setText(calls.getSdt()+"");
        txtusersingle.setText(calls.getTen().substring(0,1));
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strsearch = charSequence.toString();
                if(strsearch.isEmpty()){
                    callList = getCallListlist;
                }else{
                    List<Call> list = new ArrayList<>();
                    for(Call callss : getCallListlist){
                        if(callss.getTen().toLowerCase().contains(strsearch.toLowerCase())){
                            list.add(callss);
                        }
                    }
                    callList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = callList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    callList = (List<Call>) filterResults.values;
                    DataClass dataClass = new DataClass();
                    dataClass.adapterCall.notifyDataSetChanged();
            }
        };
    }
}
