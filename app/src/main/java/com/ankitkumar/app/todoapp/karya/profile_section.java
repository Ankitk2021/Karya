package com.ankitkumar.app.todoapp.karya;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class profile_section extends Fragment {


    String username;
    String password;
    String phone_number;
    String email;
    ProfileLogout profileLogout;







    public profile_section() {
        // Required empty public constructor
    }   public profile_section(String username ,String password , String email ,String phone_number) {


        this.username = username;
        this.password = password;
        this.email = email ;
        this.phone_number = phone_number;
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static profile_section newInstance(String param1, String param2) {
        profile_section fragment = new profile_section();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_section, container, false);




        return inflater.inflate(R.layout.fragment_profile_section, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TextView titleTextview = view.findViewById(R.id.title_text_profile_section);
        TextView PhoneTextview = view.findViewById(R.id.phone_number_profile_section);
        TextView emailTextview = view.findViewById(R.id.email_text_profile_section);


        titleTextview.setText(username);
        PhoneTextview.setText(phone_number);
        emailTextview.setText(email);
        ImageButton log_out_Bten = view.findViewById(R.id.log_out_btn);
        profileLogout = (ProfileLogout) getContext();
        assert profileLogout != null;
        profileLogout.sendButton(log_out_Bten);
        super.onViewCreated(view, savedInstanceState);
    }

    public interface  ProfileLogout {
        void sendButton(ImageButton btn);
    }

}