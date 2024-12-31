package com.ankitkumar.app.todoapp.karya;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class taskListRecycler extends RecyclerView.Adapter<taskListRecycler.taskViewHolder> {
Context context;
ArrayList<Task> taskList;


//Constructor
taskListRecycler(Context context,ArrayList<Task> taskList){
    this.context = context;
    this.taskList = taskList;



}


public void setFilteredList(ArrayList<Task> list){
    this.taskList = list ;
    notifyDataSetChanged();
}

    @NonNull
    @Override
    public taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //xml - > java


        View view = LayoutInflater.from(context).inflate(R.layout.task_single_background,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Toast.makeText(view.getContext(), "Clicking", Toast.LENGTH_SHORT).show();
         }
     });

        taskViewHolder taskViewHolder = new taskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull taskViewHolder holder, int position) {

        dbOpenHelper opH = new dbOpenHelper(holder.itemView.getContext());

    Task task = taskList.get(position);
    holder.title.setText(task.getTitle());
    holder.date.setText(task.getDate());
    holder.description.setText(task.getDescription());
    aniamteItem(holder.itemView,position);

        holder.statustBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task1 = taskList.get(holder.getAdapterPosition());
                boolean newStatus = !task1.getStatus();
                opH.updateTaskStatus(newStatus, task1.index); // Update DB
                task1.setStatus(newStatus); // Update local task object
                notifyItemChanged(holder.getAdapterPosition()); // Refresh item
                // Update button UI
                holder.statustBtn.setText(newStatus ? R.string.completed : R.string.noticed);
                holder.statustBtn.setTextColor(view.getResources().getColor(
                        newStatus ? R.color.noticed_color : R.color.status_details));
                System.out.println("Task Status Updated");
            }
        });
    }

    @Override
    public int getItemCount() {

    return taskList.size();
    }

     static class taskViewHolder extends RecyclerView.ViewHolder {
         Button statustBtn;
         TextView title;
         TextView date;
         TextView description;


        public taskViewHolder(@NonNull View itemView) {
            super(itemView);


            Bundle bundle = new Bundle();
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.description);
            statustBtn = itemView.findViewById(R.id.taskStatus);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(itemView.getContext(), task_details.class);
                    bundle.putString("title",title.getText().toString());
                    bundle.putString("date",date.getText().toString());
                    bundle.putString("description",description.getText().toString());
                    intent.putExtra("bundle",bundle);

                     startActivity(itemView.getContext(), intent,bundle);
                }
            });

        }



    }

    void aniamteItem (View view , int position){

        Animation sideIn = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        view.startAnimation(sideIn);




    }

}
