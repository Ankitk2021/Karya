package com.ankitkumar.app.todoapp.karya;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements add_new_task.HomeListener , profile_section.ProfileLogout {

    FloatingActionButton floatingActionButton;
    ArrayList<Task> taskListRec;
    RecyclerView recyclerView;
    taskListRecycler adapter;
    LinearLayout linearLayout;
    FrameLayout frameLayout;
    ImageButton close_btn;
    String current_username = "";
    String current_password = "";
    String current_email = "";
    String current_phone = "";
    LottieAnimationView lottieAnimationView_noDataFound;
    int i = 0;
    ImageButton logout;

androidx.appcompat.widget.SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.status_color));

        Intent i = getIntent();
        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.List_on_task);
        linearLayout = findViewById(R.id.noTaskLinear);
        frameLayout = findViewById(R.id.container);
        close_btn = findViewById(R.id.close_btn_profile_popup);
        searchView = findViewById(R.id.searchView);
        lottieAnimationView_noDataFound = findViewById(R.id.animation_noDataFound);


        // Set up floating action button listener
        floatingActionButton.setOnClickListener(view -> {
            VibrateThis(100);
            add_new_task addNewTaskFragment = new add_new_task();
            addNewTaskFragment.show(getSupportFragmentManager(), addNewTaskFragment.getTag());
        });


        ImageView profile_logo = findViewById(R.id.profile_logo);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment;

        Bundle bundle = i.getBundleExtra("res");
        if (bundle != null) {

            boolean check = bundle.getBoolean("active");
            if (!check) {
                fragment = new fragment_login_check_show();
            } else {
                current_username = bundle.getString("username" );
                current_password = bundle.getString("password" );
                current_email = bundle.getString("email");
                current_phone = bundle.getString("phone");

                fragment = new profile_section(current_username, current_password, current_email, current_phone);
                ft.add(R.id.container, fragment);

                dbOpenHelper db = new dbOpenHelper(this);
                taskListRec = db.getAllTasks(current_username, current_email, current_password);
                if (taskListRec == null) taskListRec = new ArrayList<>();

                adapter = new taskListRecycler(this, taskListRec);
                if (!taskListRec.isEmpty()) {
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    linearLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        } else {
            Intent go = new Intent(this, signup_page.class);
            Toast.makeText(this, "Try creating your account first.", Toast.LENGTH_SHORT).show();
            startActivity(go);
            return;
        }

        // Profile logo click listener
        profile_logo.setOnClickListener(view -> {
            int show = frameLayout.getVisibility();
            if (show == View.GONE) {
                frameLayout.setVisibility(View.VISIBLE);
                close_btn.setVisibility(View.VISIBLE);
                profile_logo.setVisibility(View.GONE);
                searchView.setVisibility(View.GONE);
            } else {
                frameLayout.setVisibility(View.GONE);
                close_btn.setVisibility(View.GONE);
                profile_logo.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.VISIBLE);
            }
        });

        close_btn.setOnClickListener(view -> {
            int show = frameLayout.getVisibility();
            if (show == View.GONE) {
                frameLayout.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.GONE);
            } else {
                frameLayout.setVisibility(View.GONE);
                close_btn.setVisibility(View.GONE);
                profile_logo.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.VISIBLE);
            }
        });
        searchView.clearFocus();

     searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
         @Override
         public boolean onQueryTextSubmit(String query) {
             return false;
         }

         @Override
         public boolean onQueryTextChange(String newText) {
             filterList(newText);
             return true;
         }
     });



        ft.commit();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    private void filterList(String s) {
       ArrayList<Task> myList = new ArrayList<>();
        for (Task task: taskListRec
             ) {

            if(task.getTitle().toLowerCase().contains(s.toLowerCase())){
                myList.add(task);
            }

            adapter.setFilteredList(myList);




                if(myList.isEmpty() ){
                    lottieAnimationView_noDataFound.setVisibility(View.VISIBLE);


            } else{
                lottieAnimationView_noDataFound.setVisibility(View.GONE);

            }



        }


    }

    void VibrateThis(int time) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                VibrationEffect effect = VibrationEffect.createOneShot(time, VibrationEffect.EFFECT_HEAVY_CLICK);
                vibrator.vibrate(effect);
            } else {
                vibrator.vibrate(time);
            }
        }
    }

    @Override
    public void sendDetails(String title, String date, String desc) {
        if (title != null && date != null && desc != null) {
            // Create the new task
            Task newTask = new Task(title, date, desc, true, taskListRec.size());

            // Add the task to the database
            dbOpenHelper dbOpenHelper = new dbOpenHelper(getApplicationContext());
            dbOpenHelper.addNewTask(current_username, current_password, current_email, title, current_phone, desc, date);

            // Ensure taskListRec is initialized
            if (taskListRec == null) {
                taskListRec = new ArrayList<>();
            }else {
                taskListRec.add(newTask
                );
            }
           if (taskListRec.isEmpty() ||( taskListRec.size()==1)) {
                startActivity(new Intent(this, splash.class));
            }
            Toast.makeText(this, "TaskList Size : "+ taskListRec.size(), Toast.LENGTH_SHORT).show();

            // Notify the adapter about the new item
            if (adapter != null) {


                if(!taskListRec.isEmpty()) {


                ArrayList<Task> list =    dbOpenHelper.getAllTasks(current_username,current_email,current_password);

                    adapter.notifyItemInserted(taskListRec.size()-1);
                    recyclerView.smoothScrollToPosition(taskListRec.size() - 1);

                }

            } else {
                adapter = new taskListRecycler(this, taskListRec);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }

            // Update UI visibility
            linearLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            // Inform the user
            Toast.makeText(this, "Task Added: " + title, Toast.LENGTH_SHORT).show();
        }
    }


    void ClearThis(){
        finish();
    }

    @Override
    public void sendButton(ImageButton btn) {
        logout = btn;
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CurrentDB db = new CurrentDB(getApplicationContext());
                String s = db.dropTable();
                if(s.equals("success")){
                    Intent i = new Intent(getApplicationContext(), login_page.class);
                    startActivity(i);
                    view.findViewById(R.id.log_out_btn).setVisibility(View.GONE);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
