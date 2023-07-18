package com.example.call.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.call.model.Call;
import com.example.call.model.Database;
import com.example.call.R;
import com.example.call.adapter.adapter_call;
import com.example.call.databinding.ActivityMainThemytBinding;
import com.example.call.util.DataClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity_themyt extends AppCompatActivity {
    private ActivityMainThemytBinding binding;
    int vitri;
    DataClass dataClass;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainThemytBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataClass.listContact = new ArrayList<>();
        dataClass.adapterCall = new adapter_call(MainActivity_themyt.this, R.layout.layout_main1, dataClass.listContact);
        binding.lstthemyt.setAdapter(dataClass.adapterCall);
        HomeActivity.database = new Database(MainActivity_themyt.this, "callsss", null, 1);
        HomeActivity.database.query("CREATE TABLE IF NOT EXISTS CALL(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEN TEXT, SDT TEXT)");
        HomeActivity.cursor();
        Collections.sort(dataClass.listContact, new Comparator<Call>() {
            @Override
            public int compare(Call call, Call t1) {
                return (call.getTen().substring(0,1).compareTo(t1.getTen().substring(0,1)));
            }
        });
        binding.lstthemyt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i;
                    Intent intent1 = new Intent(MainActivity_themyt.this, MainActivity_yeuthich.class);
                Call callsyt = dataClass.listContact.get(vitri);
                intent1.putExtra("themyeuthich", callsyt);
                //intent1.putExtra("txtyt",binding.txtuser.getText().toString());
                startActivityForResult(intent1, vitri);
                Toast.makeText(MainActivity_themyt.this, "Thanh Cong", Toast.LENGTH_SHORT).show();

            }
        });
        binding.searchView.setOnClickListener(v->{
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView = v.findViewById(R.id.searchView);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    dataClass.adapterCall.getFilter().filter(s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    dataClass.adapterCall.getFilter().filter(s);
                    if(s.isEmpty()){
                        return true;
                    }
                    return false;
                }
            });
        });
    }
    @Override
    public void onBackPressed() {
        if(searchView.isIconified()){
            searchView.setIconified(true);
            finish();

        }else{
            super.onBackPressed();
        }
    }

}