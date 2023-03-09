package com.simplequiz.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.simplequiz.app.databinding.ActivityValidUserScreenBinding;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Valid_userScreen extends AppCompatActivity {
TextView tv_show;
ActivityValidUserScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> details=new ArrayList<>();
        binding=ActivityValidUserScreenBinding.inflate(getLayoutInflater());
//        rv_details=binding.rvList;
        setContentView(R.layout.activity_valid_user_screen);
//        Bundle bundle=getIntent().getExtras();
//        String [] results=bundle.getStringArray("result");
//        for(String res:results)
//        {
//           System.out.println(res);
//
//        }
        SecondRV adapter=new SecondRV(this,details);



    }
}