package com.example.apl_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText SID, pass;
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
            if(user.equals("321") && password.equals("123")) {
                Intent i = new Intent(MainActivity.this, StudentPage.class);
                Bundle extras = new Bundle();
                extras.putString("ID", user);
                i.putExtras(extras);
                startActivity(i);
            }
            else{
                Toast.makeText(getApplicationContext(),"username or password is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}