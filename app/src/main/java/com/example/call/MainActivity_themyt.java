package com.example.call;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.call.databinding.ActivityMainThemBinding;
import com.example.call.databinding.ActivityMainThemytBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity_themyt extends AppCompatActivity {
    private ActivityMainThemytBinding binding;
    int vitri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainThemytBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MainActivity.callList = new ArrayList<>();
        MainActivity.adapter = new adapter_call(MainActivity_themyt.this, R.layout.layout_main1, MainActivity.callList);
        binding.lstthemyt.setAdapter(MainActivity.adapter);
        MainActivity.database = new Database(MainActivity_themyt.this, "callsss", null, 1);
        MainActivity.database.query("CREATE TABLE IF NOT EXISTS CALL(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEN TEXT, SDT TEXT)");
        MainActivity.cursor();
        Collections.sort(MainActivity.callList, new Comparator<call>() {
            @Override
            public int compare(call call, call t1) {
                return (call.getTen().substring(0,1).compareTo(t1.getTen().substring(0,1)));
            }
        });
        binding.lstthemyt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i;
                    Intent intent1 = new Intent(MainActivity_themyt.this, MainActivity_yeuthich.class);
                call callsyt = MainActivity.callList.get(vitri);
                intent1.putExtra("themyeuthich", callsyt);
                //intent1.putExtra("txtyt",binding.txtuser.getText().toString());
                startActivityForResult(intent1, vitri);
                Toast.makeText(MainActivity_themyt.this, "Thanh Cong", Toast.LENGTH_SHORT).show();

            }
        });
    }

}