package com.ankitkumar.app.todoapp.karya;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.status_color));
        Intent intent ;
        CurrentDB currentDB = new CurrentDB(this);
        Bundle bundle = new Bundle();
        Intent i = getIntent();
        ArrayList<User> users = currentDB.getData();
        ArrayList<Task> tasks = currentDB.getTasksData();
        if(!users.isEmpty()){

            User user = users.get(0);
            user.setActive(true);
            intent = new Intent(this, MainActivity.class);
            bundle.putString("username",user.getUsername());
            bundle.putString("password",user.getPassword());
            bundle.putString("email",user.getEmail());
            bundle.putString("phone", user.getPhone_number());
            bundle.putBoolean("active",user.getActive());
            intent.putExtra("res", bundle);
        } else{

            if(i.getBundleExtra("signup") != null){

                intent = new Intent(this, MainActivity.class);
                intent.putExtra("res",i.getBundleExtra("signup"));
            } else if (i.getBundleExtra("login")!=null){

                intent = new Intent(this, MainActivity.class);
                intent.putExtra("res",i.getBundleExtra("login"));
            } else {

                intent = new Intent(this, signup_page.class);
                intent.putExtra("null",new Bundle());
            }
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //bundle -> existed_bundle || res || res
                startActivity(intent);
                finish();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 2000);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}