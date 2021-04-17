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
    int counter = 0;
    private String firstName,id, lastName, password, emailAddress, name;
    private String course[];
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
            name = firstName + lastName;
            emailAddress = extras.getString("emailAddress");
            password = extras.getString("password");
        }




        db.collection("Lecture")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task){
                            if (task.isSuccessful()){
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    Log.d("@Lecturs@ ", "------------------------------------------");
                                    Log.d("@Lecturs@ ", "Subject_subjectId: " +document.getData().get("Subject_subjectId"));
                                    Log.d("@Lecturs@ ", "State: " +document.getData().get("State"));
                                    Log.d("@Lecturs@ ", "Teacher_teacherId: " +document.getData().get("Teacher_teacherId"));
                                    Log.d("@Lecturs@ ", "time: " +document.getTimestamp("time").toDate());

                                }
                            } else{
                                Toast.makeText(getApplicationContext(),"Error!!!", Toast.LENGTH_LONG).show();
                                Log.d(String.valueOf(getApplicationContext()), "Errorrrr!!!");
                            }
                        }

                    });





        SID.setText(id);
        SName.setText(name);
        update = findViewById(R.id.update);
        rl = findViewById(R.id.recyclerView);
        course = courses((int)(Math.random()*30));
        MyAdapter myAdapter = new MyAdapter(getApplicationContext(), course,course,name,id);
        rl.setAdapter(myAdapter);
        rl.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        update.setOnClickListener(v -> {
            course = courses((int)(Math.random()*30));
            MyAdapter myAdapter1 = new MyAdapter(getApplicationContext(), course,course,name,id);
            rl.setAdapter(myAdapter1);
            rl.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        });
    }


    protected String[] courses(int length){
        String courses[] = new String[length];
        for(int course = 0; course<courses.length;course++){
            courses[course] = "10"+(int)(Math.random()*9);
        }
        return courses;
    }
}