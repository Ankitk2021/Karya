package com.ankitkumar.app.todoapp.karya;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class task_details extends AppCompatActivity {
TextView title;
TextView date;
TextView details;
TextView task_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_details);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_details));
title = findViewById(R.id.title);
date = findViewById(R.id.date);
details = findViewById(R.id.details);
task_status = findViewById(R.id.task_status);




        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");


        assert bundle != null;

        title.setText(bundle.getString("title"));
        date.setText(bundle.getString("date"));
        details.setText(bundle.getString("description"));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}