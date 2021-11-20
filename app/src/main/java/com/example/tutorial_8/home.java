package com.example.tutorial_8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class home extends AppCompatActivity {

    TextView user;
    Button show;
    ListView userList;


    DBHelper DB;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = findViewById(R.id.viewuname);
        userList = findViewById(R.id.listuser);
        show = findViewById(R.id.btnshow);

        preferences = getSharedPreferences("MyShPre",MODE_PRIVATE);
        editor = preferences.edit();

        Intent i= getIntent();
        String s = i.getStringExtra("uname");
        user.setText("User Login : " + s);

        DB = new DBHelper(this);

        listItem = new ArrayList<>();

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItem.clear();
                viewData();
            }
        });

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private String name,uname,phone,pass;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor1 = DB.showData();
                while(cursor1.moveToNext()) {
                    name = cursor1.getString(1);
                    uname = cursor1.getString(2);
                    phone = cursor1.getString(3);
                    pass = cursor1.getString(4);

                    String p = userList.getItemAtPosition(position).toString();
                    Toast.makeText(getApplicationContext(), "Click : " + p, Toast.LENGTH_SHORT).show();

                    if (name.equals(p)) {
                        Intent i1 = new Intent(getApplicationContext(), details.class);
                        i1.putExtra("name", name);
                        i1.putExtra("uname", uname);
                        i1.putExtra("phone", phone);
                        i1.putExtra("pass", pass);
                        startActivity(i1);
                    }
                }
            }
        });
    }

    private void viewData() {
        Cursor cursor = DB.viewData();
        if(cursor .getCount() == 0){
            Toast.makeText(getApplicationContext(),"Not Data Show",Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(1));
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItem);
            userList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.Logout:
                userLogout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void userLogout() {
        editor.remove("Login");
        editor.commit();
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        Toast.makeText(getApplicationContext(),"User Not Login",Toast.LENGTH_SHORT).show();

    }
}