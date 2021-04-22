package com.example.apl_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class RegisterClass extends AppCompatActivity {
    TextView nameAcitivity,idActivity;
    EditText course_ID;
    Button registerButton;
    private String id;
    private String name;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            id = extras.getString("ID");
            name = extras.getString("NAME");
        }
        nameAcitivity = findViewById(R.id.StdNameRegister);
        idActivity = findViewById(R.id.StdIDRegister);
        registerButton = findViewById(R.id.registerButton);
        course_ID = findViewById(R.id.register_ID_text);
        nameAcitivity.setText(name);
        idActivity.setText(id);
        registerButton.setOnClickListener(v -> {
            addStudent();
        });
    }

    private void addStudent(){
        db.collection("Lecture")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task){
                        String names = "";
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){

                                    if(!names.equals("")){
                                        names+=",";
                                    }
                                    names+= document.getData().get("Subject_subjectId").toString();

                            }
                            String classes[] = names.split(",");
                           if(course_exist(classes, course_ID.getText().toString())){
                               db.collection("Register")
                                       .get()
                                       .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task){
                                               String names = "";
                                               String ids = "";
                                               if (task.isSuccessful()){
                                                   for(QueryDocumentSnapshot document : task.getResult()){
                                                       if(!names.equals("")){
                                                           names+=",";
                                                           ids +=",";
                                                       }
                                                       names+= document.getData().get("Lecture").toString();
                                                       ids+= document.getData().get("ID").toString();

                                                   }
                                                   String classes[] = names.split(",");
                                                   if(!student_exist(classes,ids.split(","),course_ID.getText().toString(),id)){
                                                       Map<String, Object> register = new HashMap<>();
                                                       register.put("ID", id);
                                                       register.put("Lecture", course_ID.getText().toString());
                                                       db.collection("register")
                                                               .add(register)
                                                               .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                   @Override
                                                                   public void onSuccess(DocumentReference documentReference) {
                                                                       Toast.makeText(getApplicationContext(),"registered",Toast.LENGTH_SHORT).show();
                                                                   }
                                                               })
                                                               .addOnFailureListener(new OnFailureListener() {
                                                                   @Override
                                                                   public void onFailure(@NonNull Exception e) {
                                                                       Toast.makeText(getApplicationContext(),"Can't register",Toast.LENGTH_SHORT).show();
                                                                   }
                                                               });
                                                   }
                                                   else{
                                                       Toast.makeText(getApplicationContext(),"already registered",Toast.LENGTH_SHORT).show();

                                                   }
                                               }
                                           }
                                       });
                           }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Error!!!", Toast.LENGTH_LONG).show();
                            Log.d(String.valueOf(getApplicationContext()), "Errorrrr!!!");
                        }
                    }

                });
    }

    boolean course_exist(String courses[],String course_name){
        for(String index:courses){
            if(index.equals(course_name))
                return true;
        }
        return false;
    }    boolean student_exist(String courses[],String names[],String course_name,String id){
        for(int index = 0; index<courses.length;index++){
            if(courses[index].equals(course_name)&&names[index].equals(id))
                return true;
        }
        return false;
    }
}