package com.ankitkumar.app.todoapp.karya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class login_page extends AppCompatActivity {

    Button button,login;
    EditText email_edit,pass_edit;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        button = findViewById(R.id.already_account_btn);
        login = findViewById(R.id.button_login);
        email_edit = findViewById(R.id.login_email);
        pass_edit = findViewById(R.id.login_password);
        error = findViewById(R.id.login_error);

        Intent i = new Intent(this, signup_page.class);
        Intent i2 = new Intent(this, splash.class);
        Bundle bundle = new Bundle();

        dbOpenHelper dbOpenHelper = new dbOpenHelper(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = email_edit.getText().toString();
                String password = pass_edit.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(login_page.this, "Fill all the fields first.", Toast.LENGTH_SHORT).show();
                }
                else {
                    ArrayList<User> users = dbOpenHelper.loginUser(email, password);
                    if (users.isEmpty()) {
                        error.setVisibility(View.VISIBLE);
                    } else {

                        error.setVisibility(View.GONE);
                        User user = users.get(0);
                        user.setActive(true);
                        bundle.putString("email",user.getEmail());
                        bundle.putString("username",user.getUsername());
                        bundle.putString("password",user.getPassword());
                        bundle.putString("phone",user.getPhone_number());
                        bundle.putBoolean("active",true);
                        i2.putExtra("login",bundle);

                        startActivity(i2);
                        finish();
                    }
                }
            }
        });
        button.setOnClickListener(v->{
            startActivity(i);
            finish();
        });






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}