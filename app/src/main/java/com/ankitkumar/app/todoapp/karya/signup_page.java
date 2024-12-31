package com.ankitkumar.app.todoapp.karya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class signup_page extends AppCompatActivity {
Button btn, signup;
EditText email_edit,username_edit,password_edit,phone_edit;
boolean DB_exist = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_page);
        btn =findViewById(R.id.dont_have_account_btn);
        email_edit = findViewById(R.id.signup_email);
        username_edit = findViewById(R.id.signup_username);
        password_edit = findViewById(R.id.signup_password);
        phone_edit = findViewById(R.id.signup_phone);
        signup = findViewById(R.id.signup_btn);
        Intent i = new Intent(this, login_page.class);
        Intent i2 = new Intent(this, splash.class);
        Bundle bundle = new Bundle();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_edit.getText().toString();
                String username = username_edit.getText().toString();
                String password = password_edit.getText().toString();
                String phone    = phone_edit.getText().toString();
                if(email.isEmpty() || username.isEmpty() || password.isEmpty() || phone.isEmpty()){
                    Toast.makeText(view.getContext(), "Fill all the fields Carefully.", Toast.LENGTH_SHORT).show();
                } else {
                    try  {
                        dbOpenHelper d = new dbOpenHelper(view.getContext());
                        CurrentDB currentDB = new CurrentDB(view.getContext());
                        if(!DB_exist){
                            DB_exist = true;
                        }
                        else {
                            d.addUser(username,password,email,phone);
                            currentDB.addUser(username,password,email,phone);
                           ArrayList<User> users = d.getUserData();
                           User user = users.get(0);
                            bundle.putString("email",user.getEmail());
                            bundle.putString("username",user.getUsername());
                            bundle.putString("password",user.getPassword());
                            bundle.putString("phone",user.getPhone_number());
                            bundle.putBoolean("active",true);
                            i2.putExtra("signup",bundle);
                        }
                        d.close();
                    } catch (Exception e){
                          System.out.println(e.getMessage());
                      }
                    startActivity(i2);
                    finish();
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}