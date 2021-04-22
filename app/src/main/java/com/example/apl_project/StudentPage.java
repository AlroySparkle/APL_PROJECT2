package com.example.apl_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StudentPage extends AppCompatActivity {


    TextView SID,SName;
    Button update;
    RecyclerView rl;
    private String firstName,id, lastName, password, emailAddress, name;
    private static String course[];
    private static String times[];
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);
        SID = findViewById(R.id.StdID);
        SName = findViewById(R.id.StdName);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            id = extras.getString("id");
            firstName = extras.getString("firstName");
            lastName = extras.getString("lastName");
            name = firstName +" "+ lastName;
            emailAddress = extras.getString("emailAddress");
            password = extras.getString("password");
        }




            db.collection("Lecture")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task){
                            if (task.isSuccessful()){
                                String classes="";
                                String time="";
                                String IDS="";
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    if(document.getData().get("State").toString().equals("true")){
                                        if(!classes.equals("")){
                                            classes+=",";
                                            time+=",";
                                            IDS+=",";
                                        }
                                        classes+= document.getData().get("Subject_subjectId").toString();
                                        time+=document.getTimestamp("time").toDate().toString();

                                    }
                                }
                                course = classes.split(",");
                                times = time.split(",");
                                rl = findViewById(R.id.recyclerView);
                                MyAdapter myAdapter = new MyAdapter(getApplicationContext(), course,times,name,id);
                                rl.setAdapter(myAdapter);
                                rl.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Error!!!", Toast.LENGTH_LONG).show();
                                Log.d(String.valueOf(getApplicationContext()), "Errorrrr!!!");
                            }
                        }

                    });





        SID.setText(id);
        SName.setText(name);
        update = findViewById(R.id.update);
        update.setOnClickListener(v -> {
            update();
        });
    }

    private void update(){
        db.collection("Lecture")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task){
                        if (task.isSuccessful()){
                            String classes="";
                            String time="";
                            for(QueryDocumentSnapshot document : task.getResult()){
                                if(document.getData().get("State").toString().equals("true")){
                                    if(!classes.equals("")){
                                        classes+=",";
                                        time+=",";
                                    }
                                    classes+= document.getData().get("Subject_subjectId").toString();
                                    time+=document.getTimestamp("time").toDate().toString();
                                }
                            }
                            course = classes.split(",");
                            times = time.split(",");
                            rl = findViewById(R.id.recyclerView);
                            MyAdapter myAdapter = new MyAdapter(getApplicationContext(), course,times,name,id);
                            rl.setAdapter(myAdapter);
                            rl.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Error!!!", Toast.LENGTH_LONG).show();
                            Log.d(String.valueOf(getApplicationContext()), "Errorrrr!!!");
                        }
                    }

                });
    }
}