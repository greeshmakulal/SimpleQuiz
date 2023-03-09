package com.simplequiz.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> lv_items;
    private  Integer[] images;
    public ListAdapter(Context context,ArrayList<String> lv_items,Integer[] images)
    {
        this.context=context;
        this.lv_items=lv_items;
        this.images = images;
    }
    @Override
    public int getCount() {
        return lv_items.size();
    }

    @Override
    public Object getItem(int i) {
        return lv_items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1= LayoutInflater.from(context).inflate(R.layout.item_layout,null);
        TextView title=(TextView) view1.findViewById(R.id.tv_title);
        ImageView home=(ImageView) view1.findViewById(R.id.iv_icon);
        title.setText(lv_items.get(i));
        home.setImageResource(images[i]);
        return view1;

    }
}
