package com.example.apl_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.IDN;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    String s1,s2, name, Id;
    Context context;
    int length;


    public MyAdapter(Context context, String s1, String s2, String name, String ID, int length){
        this.context = context;
        this.s1 = s1;
        this.s2 = s2;
        this.length = length;
        this.name = name;
        this.Id = ID;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_row,parent ,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(this.s1);
        holder.time.setText(position+"-"+(position+1));
        holder.attend.setOnClickListener(v -> {
            Intent intent=new Intent(v.getContext(),OUT.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle=new Bundle();
            bundle.putString("ID", this.Id);
            bundle.putString("NAME", this.name);
            intent.putExtras(bundle);
            v.getContext().startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, time;
        Button attend;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.className);
            time = itemView.findViewById(R.id.classTime);
            attend = itemView.findViewById(R.id.joinBTN);
        }
    }
}
