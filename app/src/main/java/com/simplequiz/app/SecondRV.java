package com.simplequiz.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SecondRV extends RecyclerView.Adapter<SecondRV.ViewHolder> {
    Context context;
    ArrayList<String> login_api_result=new ArrayList<>();
    public SecondRV(Context context,ArrayList<String> log_result)
    {
        this.context=context;
        this.login_api_result=log_result;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.rlist_layout,null);
        ViewHolder holder=new ViewHolder(view);l
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.row.setText(login_api_result.get(position));
        holder.img.setImageResource(R.drawable.smith);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView row;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            row=itemView.findViewById(R.id.tv_info);
            img=itemView.findViewById(R.id.iv_img);

        }
    }
}
