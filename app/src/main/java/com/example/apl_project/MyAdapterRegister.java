package com.example.apl_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MyAdapterRegister extends RecyclerView.Adapter<MyAdapterRegister.MyViewHolder> {


    String s1[],s2[], name, Id;
    Context context;
    boolean flag;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public MyAdapterRegister(Context context, String s1[], String s2[], String name, String ID){
        this.context = context;
        this.s1 = s1;
        this.s2 = s2;
        this.name = name;
        this.Id = ID;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.register_class,parent ,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText("CS"+this.s1[position]);
        holder.time.setText(position+"-"+(position+1));
        holder.attend.setOnClickListener(v -> {
            if (!student_exist(Id,s1[position],context)){
                Map<String, Object> register = new HashMap<>();
                register.put("ID", Id);
                register.put("Lecture", name);

                db.collection("register")
                        .add(register)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(context,"registered",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context,"Can't register",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else {
                Toast.makeText(context,"You are registered",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.s1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, time;
        Button attend;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.CIDRegister);
            time = itemView.findViewById(R.id.CTimeRegister);
            attend = itemView.findViewById(R.id.RegisterBTN);
        }
    }
    private boolean student_exist(String ID, String lecture, Context context){
        flag = false;
        db.collection("register")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task){
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                if(document.getData().get("ID").toString().equals(ID) && document.getData().get("ID").toString().equals(lecture)){
                                    flag = true;
                                }
                            }
                        }
                        else{
                            Toast.makeText(context,"Error!!!", Toast.LENGTH_LONG).show();
                            Log.d(String.valueOf(context), "Errorrrr!!!");
                        }
                    }
                });
        return flag;
    }
}
