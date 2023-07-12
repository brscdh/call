package com.example.call;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.security.Permission;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    public static com.example.call.databinding.ActivityMain2Binding binding;
    call calls;
    private List<call> callList;
    private adapter_call adapterCall;
    int vitri = MainActivity.vitri;
    public  static int vitrimain2;
    PopupMenu popupMenu;

    private Menu mymenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.call.databinding.ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        calls = (call) intent.getSerializableExtra("user");
        binding.txtuser.setText(calls.getTen());
        binding.txtsdtmain2.setText(calls.getSdt()+"");

        binding.txtusersinglemain2.setText(calls.getTen().substring(0,1));



        binding.imgbyeuthich.setOnClickListener(view->{
            Intent intent1 = new Intent(MainActivity2.this, MainActivity_yeuthich.class);
            call calls = MainActivity.callList.get(MainActivity.vitri);
            intent1.putExtra("yeuthich", calls);
            //intent1.putExtra("txtyt",binding.txtuser.getText().toString());
            startActivityForResult(intent1, vitri);
        });
        binding.imgbmenumain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);

            }
        });
        binding.imageButtonback.setOnClickListener(view->{
            startActivity(new Intent(MainActivity2.this, MainActivity.class));
        });
//        binding.imgcall.setOnClickListener(view->{
//            Intent intent2 = new Intent(MainActivity2.this, Incoming.class);
//            //call calls = MainActivity.callList.get(vitri);
//            intent2.putExtra("call", calls);
//            intent2.putExtra("type", "justcall");
//            startActivityForResult(intent2, vitri);
//        });
        if(ContextCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.CALL_PHONE},vitri );
        }
        binding.imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phone_intent = new Intent(Intent.ACTION_CALL);
            phone_intent.setData(Uri.parse("tel:" + binding.txtsdtmain2.getText().toString()+""));
            startActivity(phone_intent);
            }
        });
        binding.imgvideo.setOnClickListener(view->{
            Intent intent2 = new Intent(MainActivity2.this, Incoming.class);
            //call calls = MainActivity.callList.get(vitri);
            intent2.putExtra("call", calls);
            intent2.putExtra("type", "callvideo");
            startActivityForResult(intent2, vitri);
        });
        cursor();
    }
    private void cursor(){
        Cursor cursor =MainActivity.database.select("SELECT * FROM CALL");
        MainActivity.callList.clear();
        while(cursor.moveToNext()){
            MainActivity.callList.add(new call(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            ));
        }
        MainActivity.adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumain2, menu);
        mymenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    private void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(MainActivity2.this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menumain2, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.sua:
                        Intent intentsua = new Intent(MainActivity2.this, MainActivity_sua.class);
                        call calls = MainActivity.callList.get(MainActivity.vitri);
                        intentsua.putExtra("sua", calls);
                        startActivityForResult(intentsua, MainActivity.vitri);
                        return true;
                    case R.id.xoa:
                        xoa();
                        return true;
                }
                return onOptionsItemSelected(item);
            }
        });
        popupMenu.show();
    }

    private void xoa(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setTitle("THONG BAO");
        builder.setMessage("BAN CO MUON XOA KO?");
        builder.setCancelable(false);
        builder.setNegativeButton("CO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                call calls = MainActivity.callList.get(vitri);
                MainActivity.database.DELETE(MainActivity.class, calls.getId());
                MainActivity.cursor();
                Toast.makeText(MainActivity2.this, "Xoa Thanh Cong", Toast.LENGTH_SHORT).show();

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