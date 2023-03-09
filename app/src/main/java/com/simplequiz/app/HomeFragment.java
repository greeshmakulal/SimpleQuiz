package com.simplequiz.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.simplequiz.app.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    ListView lv_items;
    RecyclerView rv_items;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHomeBinding.inflate(getLayoutInflater());
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        lv_items=view.findViewById(R.id.lv_data);
        rv_items=view.findViewById(R.id.rv_items);
        ArrayList<String> items=new ArrayList<String>();
        ArrayList<String> recs=new ArrayList<String>();
        recs.add("Facebook");
        recs.add("Snapchat");
        recs.add("Twitter");
        recs.add("Whatsapp");
        recs.add("Instagram");
        recs.add("LinkedIn");
        recs.add("Wechat");
        recs.add("Zomato");
        recs.add("Swiggy");
    ArrayList<String> imageUrls=new ArrayList<String>();
        imageUrls.add("https://images.unsplash.com/photo-1570913149827-d2ac84ab3f9a?ixlib=rb-1.2.1&w=1080&fit=max&q=80&fm=jpg&crop=entropy&cs=tinysrgb");
        imageUrls.add("https://images.unsplash.com/photo-1570913149827-d2ac84ab3f9a?ixlib=rb-1.2.1&w=1080&fit=max&q=80&fm=jpg&crop=entropy&cs=tinysrgb");
        imageUrls.add("https://images.unsplash.com/photo-1570913149827-d2ac84ab3f9a?ixlib=rb-1.2.1&w=1080&fit=max&q=80&fm=jpg&crop=entropy&cs=tinysrgb");
        imageUrls.add("https://images.unsplash.com/photo-1570913149827-d2ac84ab3f9a?ixlib=rb-1.2.1&w=1080&fit=max&q=80&fm=jpg&crop=entropy&cs=tinysrgb");
        imageUrls.add("https://images.unsplash.com/photo-1570913149827-d2ac84ab3f9a?ixlib=rb-1.2.1&w=1080&fit=max&q=80&fm=jpg&crop=entropy&cs=tinysrgb");
        imageUrls.add("https://images.unsplash.com/photo-1570913149827-d2ac84ab3f9a?ixlib=rb-1.2.1&w=1080&fit=max&q=80&fm=jpg&crop=entropy&cs=tinysrgb");



        Integer[] images={R.drawable.facebookicon,R.drawable.whatsappicon,R.drawable.twitter,R.drawable.snapchat};
        items.add("Facebook");
        items.add("Whatsapp");
        items.add("Twitter");
        items.add("Snapchat");
        ListAdapter adapter=new ListAdapter(getActivity().getApplicationContext(),items,images);
        SecondRV recAdapter=new SecondRV (getActivity().getApplicationContext(),imageUrls);
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,items);
        lv_items.setAdapter(adapter);
      rv_items.setAdapter(recAdapter);
//        rv_items.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        rv_items.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),3));
        // Inflate the layout for this fragment
        return view;
    }
}