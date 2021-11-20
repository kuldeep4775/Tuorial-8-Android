package com.example.tutorial_8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class details extends AppCompatActivity {

    TextView name,uname,phone,pass;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name = findViewById(R.id.lblname);
        uname = findViewById(R.id.lbluname);
        phone = findViewById(R.id.lblphone);
        pass = findViewById(R.id.lblpass);

        DB = new DBHelper(this);

        Intent i1 = getIntent();
        String n = i1.getStringExtra("name");
        String u = i1.getStringExtra("uname");
        String ph = i1.getStringExtra("phone");
        String p = i1.getStringExtra("pass");

        name.setText("Name : "  + n);
        uname.setText("UName : "  + u);
        phone.setText("Phone : "  + ph);
        pass.setText("Pass : "  + p);

    }
}