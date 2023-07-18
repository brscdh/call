package com.example.call.activity;

import android.Manifest;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.call.model.Call;
import com.example.call.unused.Incoming;
import com.example.call.R;
import com.example.call.adapter.adapter_call;
import com.example.call.util.DataClass;

import java.util.List;

public class ContactDetailActivity extends AppCompatActivity {
    public static com.example.call.databinding.ActivityMain2Binding binding;
    Call calls;
    PopupMenu popupMenu;
    DataClass dataClass;

    private Menu mymenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.call.databinding.ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        calls = (Call) intent.getSerializableExtra("user");
        binding.txtuser.setText(calls.getTen());
        binding.txtsdtmain2.setText(calls.getSdt()+"");

        binding.txtusersinglemain2.setText(calls.getTen().substring(0,1));



        binding.imgbyeuthich.setOnClickListener(view->{
            Call call = HomeActivity.callList.get(HomeActivity.vitri);
            DataClass dataClass = new DataClass();
            dataClass.listStar.get(dataClass.vitri).setFav(!call.isFav());
            Toast.makeText(this, "Thanh Cong", Toast.LENGTH_SHORT).show();

        });
        binding.imgbmenumain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);

            }
        });
        binding.imageButtonback.setOnClickListener(view->{
            startActivity(new Intent(ContactDetailActivity.this, HomeActivity.class));
        });
//        binding.imgcall.setOnClickListener(view->{
//            Intent intent2 = new Intent(ContactDetailActivity.this, Incoming.class);
//            //Call calls = HomeActivity.callList.get(vitri);
//            intent2.putExtra("Call", calls);
//            intent2.putExtra("type", "justcall");
//            startActivityForResult(intent2, vitri);
//        });
        if(ContextCompat.checkSelfPermission(ContactDetailActivity.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ContactDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE},dataClass.vitri );
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
            Intent intent2 = new Intent(ContactDetailActivity.this, Incoming.class);
            //Call calls = HomeActivity.callList.get(vitri);
            intent2.putExtra("Call", calls);
            intent2.putExtra("type", "callvideo");
            startActivityForResult(intent2, HomeActivity.vitri);
        });
       // cursor();
    }
    private void cursor(){
        Cursor cursor = HomeActivity.database.select("SELECT * FROM CALL");
        dataClass.listContact.clear();
        while(cursor.moveToNext()){
            dataClass.listContact.add(new Call(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            ));
        }
        dataClass.adapterCall.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumain2, menu);
        mymenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    private void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(ContactDetailActivity.this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menumain2, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.sua:
                        Intent intentsua = new Intent(ContactDetailActivity.this, EditContactActivity.class);
                        Call calls = dataClass.listContact.get(dataClass.vitri);
                        intentsua.putExtra("sua", calls);
                        startActivityForResult(intentsua, dataClass.vitri);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ContactDetailActivity.this);
        builder.setTitle("THONG BAO");
        builder.setMessage("BAN CO MUON XOA KO?");
        builder.setCancelable(false);
        builder.setNegativeButton("CO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Call calls = HomeActivity.callList.get(HomeActivity.vitri);
                HomeActivity.database.DELETE(HomeActivity.class, calls.getId());
                HomeActivity.cursor();
                HomeActivity.adapter.notifyDataSetChanged();
                finish();
                Toast.makeText(ContactDetailActivity.this, "Xoa Thanh Cong", Toast.LENGTH_SHORT).show();

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
    public void onBackPressed() {
        super.onBackPressed();
    }
}