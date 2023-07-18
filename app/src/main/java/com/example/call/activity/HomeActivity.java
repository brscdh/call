package com.example.call.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.call.databinding.ActivityMainBinding;
import com.example.call.model.Call;
import com.example.call.model.Database;
import com.example.call.R;
import com.example.call.adapter.adapter_call;
import com.example.call.util.DataClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Menu mymenu;
    public static List<Call> callList;
    public static adapter_call adapter;
    TextView txtsdt;
    public static Database database;
    public static int vitri;
    private int record = 1;
    android.widget.SearchView searchView;
    public static DataClass dataClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        txtsdt = findViewById(R.id.txtsdt);

        dataClass = new DataClass();
        callList = new ArrayList<>();
        adapter = new adapter_call(HomeActivity.this, R.layout.layout_main1, callList);
        binding.listviewnguoidung.setAdapter(adapter);

        //adapter = new adapter_call(HomeActivity.this, R.layout.layout_main1, dataClass.listStar);
        database = new Database(HomeActivity.this, "callsss", null, 1);
        database.query("CREATE TABLE IF NOT EXISTS CALL(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEN TEXT, SDT TEXT)");
        cursor();

        Collections.sort(callList, new Comparator<Call>() {
            @Override
            public int compare(Call call, Call t1) {
                return (call.getTen().substring(0,1).compareTo(t1.getTen().substring(0,1)));
            }
        });

        dataClass.listContact.addAll(callList);

        binding.imgbyeuthichmain1.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity_imgbyt.class);
            intent.putExtra("callList", new ArrayList<>(callList));
            dataClass.listContact.clear();
            dataClass.listContact.addAll(callList);
            startActivity(intent);
        });
        binding.imgbmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
        binding.txtthemlienhe.setOnClickListener(view->{
            startActivity(new Intent(HomeActivity.this, AddContactActivity.class));
        });
        binding.listviewnguoidung.setOnItemClickListener((adapterView, view, i, l) -> {
            //dataClass.vitri = i;
            vitri = i;
            Intent intent = new Intent(HomeActivity.this, ContactDetailActivity.class);
            Call calls = callList.get(vitri);
            intent.putExtra("user", calls);
            startActivityForResult(intent, vitri);
        });

        binding.imgbyeuthichmain1.setOnClickListener(view->{
            startActivity(new Intent(HomeActivity.this, MainActivity_yeuthich.class));
        });
        binding.imgbrecord.setOnClickListener(v->{
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Spaek to text");
            try{
                startActivityForResult(intent, record);
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
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
            callList.add(new Call(
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
        PopupMenu popupMenu = new PopupMenu(HomeActivity.this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==record && requestCode == RESULT_OK && data != null){
            ArrayList<String> result =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            binding.searchView.setQuery(Objects.requireNonNull(result).get(0),false);
        }
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
