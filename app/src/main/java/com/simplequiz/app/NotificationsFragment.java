package com.simplequiz.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NotificationsFragment extends Fragment {
    TextView tv_name;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_notifications, container, false);
        tv_name=v.findViewById(R.id.tv_noti);
        // Inflate the layout for this fragment
        return v;
    }
}