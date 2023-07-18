package com.example.call.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.call.R;
import com.example.call.databinding.ActivityMainSuaBinding;
import com.example.call.model.Call;
import com.example.call.util.DataClass;

public class EditContactActivity extends AppCompatActivity {
    private ActivityMainSuaBinding binding;
    private Menu mymenu;
    DataClass dataClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainSuaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        Call calls = (Call) intent.getSerializableExtra("sua");
        binding.edtten.setText(calls.getTen());
        binding.edtsdt.setText(calls.getSdt()+"");
        binding.btnluusua.setOnClickListener(view->{
            calls.setTen(binding.edtten.getText().toString());
            calls.setSdt(Integer.parseInt(binding.edtsdt.getText().toString()));
            HomeActivity.database.UPDATE(HomeActivity.class,calls );
            cursor();
            trong();
            Toast.makeText(this, "Thanh Cong", Toast.LENGTH_SHORT).show();
        });
        binding.btnback.setOnClickListener(view->{
            trong();
        });
        binding.imgbmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });
    }
    private void cursor() {
        Cursor cursor = HomeActivity.database.select("SELECT * FROM CALL");
        HomeActivity.callList.clear();
        while (cursor.moveToNext()) {
            HomeActivity.callList.add(new Call(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            ));
        }
        HomeActivity.adapter.notifyDataSetChanged();
    }
    private void trong(){
        binding.edtten.setText("");
        binding.edtsdt.setText("");
        startActivity(new Intent(EditContactActivity.this, HomeActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuthem, menu);
        mymenu = menu;
        return super.onCreateOptionsMenu(menu);
    }
    private void showMenu(View view){
        PopupMenu popupMenu = new PopupMenu(EditContactActivity.this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menuthem, popupMenu.getMenu());
        popupMenu.show();
    }
}