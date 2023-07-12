package com.example.call;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.MenuItemCompat;

import com.example.call.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static List<call> callList;
    public static adapter_call adapter;
    private Menu mymenu;
    TextView txtsdt;
    public static Database database;
    public static int vitri;
    android.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        txtsdt = findViewById(R.id.txtsdt);
        callList = new ArrayList<>();
        adapter = new adapter_call(MainActivity.this, R.layout.layout_main1, callList);
        database = new Database(MainActivity.this, "callsss", null, 1);
        database.query("CREATE TABLE IF NOT EXISTS CALL(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEN TEXT, SDT TEXT)");
        cursor();
        Collections.sort(callList, new Comparator<call>() {
            @Override
            public int compare(call call, call t1) {
                return (call.getTen().substring(0,1).compareTo(t1.getTen().substring(0,1)));
            }
        });

        binding.listviewnguoidung.setAdapter(adapter);
        binding.imgbmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
        binding.txtthemlienhe.setOnClickListener(view->{
            startActivity(new Intent(MainActivity.this, MainActivity_them.class));
        });
        binding.imgbyeuthichmain1.setOnClickListener(view -> {
//            if (vitri >= 0 && vitri < callList.size() && MainActivity2.binding.txtuser.getText().toString() !=null) {
//                call calls = callList.get(vitri);
//                calls.setTen(MainActivity2.binding.txtuser.getText().toString());
//                Intent intent = new Intent(MainActivity.this, MainActivity_yeuthich.class);
//                intent.putExtra("yeuthich", calls);
//                startActivityForResult(intent, vitri);
//            }
            startActivity(new Intent(MainActivity.this, MainActivity_imgbyt.class));
        });



        binding.listviewnguoidung.setOnItemClickListener((adapterView, view, i, l) -> {
            vitri = i;
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            call calls = callList.get(vitri);
            intent.putExtra("user", calls);
            startActivityForResult(intent, vitri);
        });

        binding.imgbyeuthichmain1.setOnClickListener(view->{
            startActivity(new Intent(MainActivity.this, MainActivity_yeuthich.class));
        });
        binding.searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                searchView =view.findViewById(R.id.searchView);
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        adapter.getFilter().filter(s);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        adapter.getFilter().filter(s);
                        if(s.isEmpty()){
                            return true;
                        }
                        return false;
                    }
                });

            }
        });

        binding.floatingbtn.setOnClickListener(view->{
            shownumberkeybroard();
        });
        binding.imgdanhba.setOnClickListener(view->{
            recreate();
        });

    }
    public static void cursor(){
        Cursor cursor =database.select("SELECT * FROM CALL");
        callList.clear();
        while(cursor.moveToNext()){
            callList.add(new call(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            ));
        }
        adapter.notifyDataSetChanged();
    }

   private void shownumberkeybroard(){
        // nhan trong diem
        binding.edtphim.requestFocus();
       InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
       if(inputMethodManager!=null){
           // hien thi phim = showSoftInput
           inputMethodManager.showSoftInput(binding.edtphim, inputMethodManager.SHOW_IMPLICIT);
           binding.edtphim.requestFocus();
       }
    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            mymenu = menu;
            return super.onCreateOptionsMenu(menu);
        }
    private void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.show();
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
