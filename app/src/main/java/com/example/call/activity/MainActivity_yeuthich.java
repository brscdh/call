package com.example.call.activity;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.call.model.Call;
import com.example.call.model.Database;
import com.example.call.R;
import com.example.call.adapter.adapter_yt;
import com.example.call.databinding.ActivityMainYeuthichBinding;
import com.example.call.util.DataClass;

import java.io.ObjectInput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity_yeuthich extends AppCompatActivity {
    public static ActivityMainYeuthichBinding binding;

    public static List<Call> callyt;
    public static adapter_yt adapteryt;
    public static Database databaseyt;
    DataClass dataClass;
    Call calls, callsyt;
    public static int vitri;
    Menu mymenu;
    String getTxtyt ;
    android.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainYeuthichBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


         Intent intent = getIntent();
//        calls = (Call) intent.getSerializableExtra("yeuthich");
//        callsyt = (Call) intent.getSerializableExtra("themyeuthich");
//        //String txtytValue = intent.getStringExtra("txtyt");

        dataClass = new DataClass();
        dataClass.listStar = new ArrayList<>();
        dataClass.adapterYt = new adapter_yt(MainActivity_yeuthich.this, R.layout.layout_main3, dataClass.listStar);
        binding.lstyeuthich.setAdapter(adapteryt);
        databaseyt = new Database(MainActivity_yeuthich.this, "ca", null, 1);

//            if(calls!=null && calls.getTen()!= null && !calls.getTen().isEmpty()){
//                Toast.makeText(this, "Da co nguoi yeu thich nay", Toast.LENGTH_SHORT).show();
//                finish();
//            }else{
        if(calls!=null && !calls.getTen().isEmpty() && !calls.getTen().isEmpty() ){
            databaseyt.query("CREATE TABLE IF NOT EXISTS CALL(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "TEN TEXT, SDT TEXT)");
            databaseyt.INSERT_YT(calls.getTen(), Integer.parseInt(calls.getSdt()+""));
            cursor();
            binding.lstyeuthich.setAdapter(adapteryt);
        }
        if(callsyt!=null && !callsyt.getTen().isEmpty() && !callsyt.getTen().isEmpty() ){
            databaseyt.query("CREATE TABLE IF NOT EXISTS CALL(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "TEN TEXT, SDT TEXT)");
            databaseyt.INSERT_TYT(callsyt.getTen(), Integer.parseInt(callsyt.getSdt()+""));
            cursor();
            binding.lstyeuthich.setAdapter(adapteryt);
        }

        binding.lstyeuthich.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitri=i;
                Intent intent1 = new Intent(MainActivity_yeuthich.this, ContactDetailActivity.class);
                Call calls = callyt.get(i);
                intent1.putExtra("user", calls);
                startActivityForResult(intent1, i);
            }
        });
        binding.lstyeuthich.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                xoa();
                return false;
            }
        });

        binding.searchViewyt.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                searchView =view.findViewById(R.id.searchViewyt);
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        adapteryt.getFilter().filter(s);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        adapteryt.getFilter().filter(s);
                        if(s.isEmpty()){
                            return true;
                        }
                        return false;
                    }
                });

            }
        });
        binding.txtthemyt.setOnClickListener(view->{
            startActivity(new Intent(MainActivity_yeuthich.this, MainActivity_themyt.class));
        });
        binding.imgbrecord.setOnClickListener(v->{
            Intent intent1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

            try{
                startActivityForResult(intent1, vitri);
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void cursor() {
        Cursor cursor = databaseyt.select("SELECT * FROM CALL");
        callyt.clear();
        while (cursor.moveToNext()) {
            callyt.add(new Call(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            ));
        }
        Collections.sort(callyt, new Comparator<Call>() {
            @Override
            public int compare(Call call, Call t1) {
                return (call.getTen().substring(0,1).compareTo(t1.getTen().substring(0,1)));
            }
        });
        adapteryt.notifyDataSetChanged();
    }

    private void xoa() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_yeuthich.this);
        builder.setTitle("THONG BAO");
        builder.setMessage("BAN CO MUON XOA KO?");
        builder.setCancelable(false);
        builder.setNegativeButton("CO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Call calls = callyt.get(vitri);
                databaseyt.DELETE_YT(MainActivity_yeuthich.this, calls.getId());
                callyt.remove(vitri);
                cursor();
                Toast.makeText(MainActivity_yeuthich.this, "Xoa Thanh Cong", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == vitri && resultCode == RESULT_OK && data != null){
            ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            binding.searchViewyt.setQuery(Objects.requireNonNull(list).get(0), false);
        }
    }

    @Override
    public void onBackPressed() {
        if (searchView.isIconified()) {
            searchView.setIconified(true);
            finish();

        } else {
            super.onBackPressed();
        }

    }
}





