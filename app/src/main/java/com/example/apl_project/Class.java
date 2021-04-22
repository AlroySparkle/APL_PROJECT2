package com.example.apl_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Class extends AppCompatActivity {
    Button update,out;
    RecyclerView rl;
    private String[] ids;
    private String[] names;
    private String id;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_u_t);
        ids = new String[1];
        names = new String[1];
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            ids[0] = extras.getString("ID");
            names[0] = extras.getString("NAME");
            id = extras.getString("CLASS");
        }
        Map<String, Object> attend = new HashMap<>();
        attend.put("Lecture_lectureId", id);
        attend.put("Student_student", names[0]);
        attend.put("present", true);
        attend.put("sid",ids[0]);

        db.collection("Attendance")
                .add(attend)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Test", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Test", "Error writing document", e);
                    }
                });
        addStudent();
        update = findViewById(R.id.updateCourse);
        update.setOnClickListener(v -> {
            addStudent();
        });

    }

    private void addStudent(){
        db.collection("Attendance")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task){
                        if (task.isSuccessful()){
                            String students=names[0];
                            String sid=ids[0];
                            for(QueryDocumentSnapshot document : task.getResult()){
                                if(document.getData().get("Lecture_lectureId").toString().equals(id)){
                                    if(!students.equals("")){
                                        students+=",";
                                        sid+=",";
                                    }
                                    students+= document.getData().get("Student_student").toString();
                                    sid+=document.getData().get("sid").toString();

                                }
                            }
                            names = students.split(",");
                            ids = sid.split(",");
                            Log.d("Test", "onComplete: "+students);
                            rl = findViewById(R.id.recyclerView2);
                            MyAdapterCourse myAdapter1 = new MyAdapterCourse(getApplicationContext(), ids,names);
                            rl.setAdapter(myAdapter1);
                            rl.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Error!!!", Toast.LENGTH_LONG).show();
                            Log.d(String.valueOf(getApplicationContext()), "Errorrrr!!!");
                        }
                    }

                });
    }


    private String[] addID(String names[]){
        String newStudents[] = new String[names.length+1];
        for(int student = 0; student<names.length;student++){
            newStudents[student] = names[student];
        }
        newStudents[names.length] = "10"+((int)(Math.random()*9));
        return newStudents;
    }
}