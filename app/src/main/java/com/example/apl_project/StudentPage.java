package com.example.apl_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StudentPage extends AppCompatActivity {
    TextView SID,SName;
    Button update;
    RecyclerView rl;
    int counter = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);

        SID = findViewById(R.id.StdID);
        SName = findViewById(R.id.StdName);
        update = findViewById(R.id.update);
        rl = findViewById(R.id.recyclerView);
        MyAdapter myAdapter = new MyAdapter(getApplicationContext(), "test1","test2",++counter);
        rl.setAdapter(myAdapter);
        rl.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        update.setOnClickListener(v -> {
            MyAdapter myAdapter1 = new MyAdapter(getApplicationContext(), "test1","test2",++counter);
            rl.setAdapter(myAdapter1);
            rl.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            SID.setText(extras.getString("ID"));
        }

    }
}