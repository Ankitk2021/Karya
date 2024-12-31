package com.ankitkumar.app.todoapp.karya;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.time.LocalDate;

public class add_new_task extends BottomSheetDialogFragment {
    View close_view;
    Button saveBtn;
    HomeListener homeListener;



    public add_new_task() {}
    public static add_new_task newInstance() {
        return new add_new_task();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the EditTexts and Button



        EditText editTitle = view.findViewById(R.id.bottomTitle);
        EditText editDate = view.findViewById(R.id.bottomDate);
        EditText editDesc = view.findViewById(R.id.bottomDesc);
        saveBtn = view.findViewById(R.id.btnDone);

        close_view = view.findViewById(R.id.close_view);
        close_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        // Set OnClickListener for the Save Button
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the values from EditTexts
                String Title = editTitle.getText().toString().trim();
                String Date = editDate.getText().toString().trim();
                String Desc = editDesc.getText().toString().trim();




                if(Date.isEmpty()){
                    LocalDate currentDate = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        currentDate = LocalDate.now();
                        Date = currentDate.toString();
                    }
                    System.out.println("Current date: " + currentDate);
                }

                // Validate input fields
                if (Title.isEmpty()  || Desc.isEmpty()) {
                    Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Pass the values to the Activity using HomeListener
                if (homeListener != null) {
                    homeListener.sendDetails(Title,Date,Desc);
                }
                // Dismiss the BottomSheetDialogFragment
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            homeListener = (HomeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement HomeListener");
        }
    }

    // Define the interface
    public interface HomeListener {
        void sendDetails(String title, String date, String desc);


    }

}
