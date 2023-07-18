package com.example.call.unused;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.call.R;
import com.example.call.databinding.ActivityIncomingBinding;
import com.example.call.model.Call;

import java.util.List;

public class Incoming extends AppCompatActivity {
    private ActivityIncomingBinding binding;
    private List<Call> callList;
    public static ImageView imgcall;
    private String meeting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIncomingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        meeting = getIntent().getStringExtra("type");
        if(meeting.equals("justcall")){
            binding.imgcall.setImageResource(R.drawable.ic_audio);
        }else{
            binding.imgcall.setImageResource(R.drawable.ic_video);
        }

        Call calls = (Call) getIntent().getSerializableExtra("Call");
        if(calls != null){
            binding.txtuser.setText(calls.getTen());
            binding.txtusersinglemain3.setText(calls.getTen().substring(0,1));
        }
        binding.imgbcancel.setOnClickListener(view->{
            finish();
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        });
    }

}