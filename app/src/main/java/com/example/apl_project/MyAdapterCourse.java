package com.example.apl_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapterCourse extends RecyclerView.Adapter<MyAdapterCourse.MyViewHolder> {


    String s1[],s2[];
    Context context;


    public MyAdapterCourse(Context context, String s1[], String s2[]){
        this.context = context;
        this.s1 = s1;
        this.s2 = s2;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.student_style,parent ,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(this.s2[position]);
        holder.id.setText(this.s1[position]);
        holder.avatar.setImageResource(R.drawable.img_avatar);

    }

    @Override
    public int getItemCount() {
        return this.s1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, id;
        ImageView avatar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.SNameC);
            id = itemView.findViewById(R.id.SIDC);
            avatar = itemView.findViewById(R.id.imageView);
        }
    }
}
