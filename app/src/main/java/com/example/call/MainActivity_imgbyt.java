package com.example.call;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.call.databinding.ActivityMainImgbytBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_imgbyt extends AppCompatActivity {
    private ActivityMainImgbytBinding binding;
    int vitri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainImgbytBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainActivity_yeuthich.callyt = new ArrayList<>();
        MainActivity_yeuthich.adapteryt = new adapter_yt(MainActivity_imgbyt.this, R.layout.layout_main3, MainActivity_yeuthich.callyt);
        binding.lstyeuthichimgb.setAdapter(MainActivity_yeuthich.adapteryt);

        MainActivity_yeuthich.databaseyt = new Database(MainActivity_imgbyt.this,"ca", null, 1);
        MainActivity_yeuthich.databaseyt.query("CREATE TABLE IF NOT EXISTS CALL(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEN TEXT, SDT TEXT)");
        MainActivity_yeuthich.cursor();
        binding.lstyeuthichimgb.setAdapter(MainActivity_yeuthich.adapteryt);

        binding.lstyeuthichimgb.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MainActivity_imgbyt.this, MainActivity2.class);
            call calls = MainActivity_yeuthich.callyt.get(i);
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
                call calls = MainActivity_yeuthich.callyt.get(vitri);
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
