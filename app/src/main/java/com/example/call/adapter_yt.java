package com.example.call;

import static androidx.recyclerview.widget.RecyclerView.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class adapter_yt extends BaseAdapter implements Filterable {
    private Context context;
    private int layout;
    private List<call> callList;
    private List<call> getCallList;

    public adapter_yt(Context context, int layout, List<call> callList) {
        this.context = context;
        this.layout = layout;
        this.callList = callList;
        this.getCallList = callList;
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
        TextView txtuser = view.findViewById(R.id.txtuseryt);
        TextView txtusersingle = view.findViewById(R.id.txtusersingleyt);
        call calls = callList.get(i);
        txtuser.setText(calls.getTen());
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
                    callList = getCallList;
                }else{
                    List<call> list = new ArrayList<>();
                    for(call callss : getCallList){
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
                callList = (List<call>) filterResults.values;
                MainActivity_yeuthich.adapteryt.notifyDataSetChanged();
            }
        };
    }
}
