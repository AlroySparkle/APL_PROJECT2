package com.example.apl_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

public class OUT extends AppCompatActivity {
    Button update,out;
    RecyclerView rl;
    private String[] ids;
    private String[] names;
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
        }
        rl = findViewById(R.id.recyclerView2);
        MyAdapterCourse myAdapter = new MyAdapterCourse(getApplicationContext(), ids,names);
        rl.setAdapter(myAdapter);
        rl.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        update = findViewById(R.id.updateCourse);
        update.setOnClickListener(v -> {
            this.names = addStudent(names);
            this.ids = addID(ids);
            MyAdapterCourse myAdapter1 = new MyAdapterCourse(getApplicationContext(), ids,names);
            rl.setAdapter(myAdapter1);
            rl.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        });

    }

    private String[] addStudent(String names[]){
        String someNames[] = {"Khalid","Ali","Sultan","Yaqoub","Jhon","Mohammed","Abdulrahman"};
        String newStudents[] = new String[names.length+1];
        for(int student = 0; student<names.length;student++){
            newStudents[student] = names[student];
        }
        newStudents[names.length] = someNames[(int)(Math.random()*7)];
        return newStudents;
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