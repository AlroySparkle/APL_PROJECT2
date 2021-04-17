package com.example.apl_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText SID, pass;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private Source source = Source.CACHE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.loginBTN);
        SID = findViewById(R.id.SID);
        pass = findViewById(R.id.pass);




        login.setOnClickListener(v -> {
            String user = SID.getText().toString();
            String password = pass.getText().toString();
//            Here is the validation of the student



            DocumentReference docRef = db.collection("Student").document(user);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                            if(password.equals(document.getData().get("password"))){
                                Toast.makeText(getApplicationContext(),"OK", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(MainActivity.this, StudentPage.class);
                                Bundle extras = new Bundle();
                                extras.putString("id", user);
                                extras.putString("firstName", document.getData().get("firstName").toString());
                                extras.putString("lastName", document.getData().get("lastName").toString());
                                extras.putString("password", document.getData().get("password").toString());
                                extras.putString("emailAddress", document.getData().get("emailAddress").toString());
                                i.putExtras(extras);
                                startActivity(i);
                            }else{
                                Toast.makeText(getApplicationContext(),"Password is wrong", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d("TAG", "No such document");
                            Toast.makeText(getApplicationContext(),"Username is wrong", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                }
            });






//            db.collection("Lecture").document("AkVEejWMRAFWubdaAXPx")
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                            Log.d(String.valueOf(getApplicationContext()), "AAAQWWW: " + task.isSuccessful());
//
////                            if (task.isSuccessful()) {
////                                Log.d("djjdjd" , "the lenght is " + task.getResult().size());
////                                for (QueryDocumentSnapshot document : task.getResult()) {
////                                    Log.d(String.valueOf(getApplicationContext()), "AAAQ: ");
////                                    Log.d(String.valueOf(getApplicationContext()), "AAA: "+document.getId() + " => " + document.getData());
////                                }
////                            } else {
////                                Log.w(String.valueOf(getApplicationContext()), "Error getting documents.", task.getException());
////                            }
//                        }
//                    });



//            db.collection("Student").whereEqualTo("emailAddress", "2160001234@iau.edu.sa")
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task){
//                            if (task.isSuccessful()){
//                                for(QueryDocumentSnapshot document : task.getResult()){
//                                    Toast.makeText(getApplicationContext(),"Datta: " + document.getData(), Toast.LENGTH_LONG).show();
//                                    Log.d(String.valueOf(getApplicationContext()), document.getId() + " = >" + document.getData());
//                                }
//                            } else{
//                                Toast.makeText(getApplicationContext(),"Error!!!", Toast.LENGTH_LONG).show();
//                                Log.d(String.valueOf(getApplicationContext()), "Errorrrr!!!");
//                            }
//                        }
//
//                    });



//            if(user.equals("321") && password.equals("123")) {
//                Intent i = new Intent(MainActivity.this, StudentPage.class);
//                Bundle extras = new Bundle();
//                extras.putString("ID", user);
//                extras.putString("NAME", "Ali");
//                i.putExtras(extras);
//                startActivity(i);
//            }
//            else{
//                Toast.makeText(getApplicationContext(),"username or password is wrong", Toast.LENGTH_SHORT).show();
//            }
        });
    }
}