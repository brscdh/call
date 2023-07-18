package com.example.call.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.call.adapter.adapter_call;
import com.example.call.model.Call;
import com.example.call.model.Database;
import com.example.call.R;
import com.example.call.adapter.adapter_yt;
import com.example.call.databinding.ActivityMainImgbytBinding;
import com.example.call.util.DataClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity_imgbyt extends AppCompatActivity {
    private ActivityMainImgbytBinding binding;
    int vitri;
    DataClass dataClass;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainImgbytBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataClass = HomeActivity.dataClass;
        Intent intent1 = getIntent();
        if (intent1 != null && intent1.hasExtra("callList")) {
            List<Call> callList = (List<Call>) intent1.getSerializableExtra("callList");
            if (callList != null) {
                dataClass.listContact.clear();
                dataClass.listContact.addAll(callList);
            }
        }
        dataClass.adapterCall = new adapter_call(this, R.layout.layout_main1, dataClass.listContact);
        binding.lstyeuthichimgb.setAdapter(dataClass.adapterCall);



        dataClass.adapterCall.notifyDataSetChanged();

        Collections.sort(dataClass.listContact, new Comparator<Call>() {
            @Override
            public int compare(Call call, Call t1) {
                return (call.getTen().substring(0,1).compareTo(t1.getTen().substring(0,1)));
            }
        });

        binding.lstyeuthichimgb.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MainActivity_imgbyt.this, ContactDetailActivity.class);
            Call calls = MainActivity_yeuthich.callyt.get(i);
            intent.putExtra("user", calls);
            startActivityForResult(intent, i);
        });

        binding.lstyeuthichimgb.setOnItemLongClickListener((adapterView, view, i, l) -> {
            MainActivity_yeuthich.vitri = i;
            xoa();
            return false;
        });
    }



    private void xoa() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_imgbyt.this);
        builder.setTitle("THONG BAO");
        builder.setMessage("BAN CO MUON XOA KO?");
        builder.setCancelable(false);
        builder.setNegativeButton("CO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Call calls = MainActivity_yeuthich.callyt.get(vitri);
                MainActivity_yeuthich.databaseyt.DELETE_YT(MainActivity_imgbyt.this, calls.getId());
                MainActivity_yeuthich.callyt.remove(vitri);
                MainActivity_yeuthich.cursor();
                binding.lstyeuthichimgb.setAdapter(MainActivity_yeuthich.adapteryt);
                Toast.makeText(MainActivity_imgbyt.this, "Xoa Thanh Cong", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("KO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
