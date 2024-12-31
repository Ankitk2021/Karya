package com.ankitkumar.app.todoapp.karya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CurrentDB extends SQLiteOpenHelper {
    Context context ;
    final static String  DB_NAME = "CurrentDB_Karya.app" ;
    final static int VERSION = 1;

    final static String USER_TABLE_NAME = "USERS" ;
    final static String USER_COL_ID = "ID" ;
    final static String USER_COL_USERNAME = "USERNAME";
    final static String USER_COL_EMAIL = "EMAIL";
    final static String USER_COL_PASSWORD = "PASSWORD";
    final static String USER_COL_PHONE = "PHONES";

    final static String Task_TABLE_NAME = "Tasks" ;
    final static String Task_COL_ID = "task_ids" ;
    final static String Task_COL_USERNAME = "task_names";
    final static String Task_COL_EMAIL = "EMAIL";
    final static String Task_COL_PASSWORD = "PASSWORD";
    final static String Task_COL_PHONE = "PHONES";
    final static String Task_COL_TITLE = "title";
    final static String Task_COL_CONTENTS = "task_contents";
    final static String Task_COL_DATE = "date";
    final static String Task_COL_STATUS = "status";


    public CurrentDB( Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sqlQuery = "create table if not exists " + USER_TABLE_NAME+ " ( "+USER_COL_ID+" integer primary key autoincrement, "+USER_COL_USERNAME+" String, "+USER_COL_PASSWORD+" String, "+USER_COL_EMAIL+" String, "+USER_COL_PHONE+" String )";
        sqLiteDatabase.execSQL(sqlQuery);


        String sqlQuery2 = "create table if not exists " + Task_TABLE_NAME + "( "+Task_COL_ID+" integer primary key autoincrement, "+Task_COL_USERNAME+" String, "+Task_COL_PASSWORD+" String, "+Task_COL_EMAIL+" String, "+Task_COL_PHONE+" String, "+Task_COL_TITLE+" String, "+Task_COL_CONTENTS+" String, "+Task_COL_DATE+" String, "+Task_COL_STATUS+" integer )";
        sqLiteDatabase.execSQL(sqlQuery2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists "+USER_TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    void addUser(String name , String pass , String email ,String phone){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_USERNAME,name);
        contentValues.put(USER_COL_PASSWORD,pass);
        contentValues.put(USER_COL_EMAIL,email);
        contentValues.put(USER_COL_PHONE,phone);
        db.insert(USER_TABLE_NAME,null,contentValues);

    }


    ArrayList<User> getData(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ USER_TABLE_NAME,null) ;
        ArrayList<User> users = new ArrayList<>();
        while(c.moveToNext()){
            String username = c.getString(1);
            String password = c.getString(2);
            String email = c.getString(3);
            String phone = c.getString(4);
            User user = new User(username,password,phone,email);
            users.add(user);
        }



        return users;

    }

    ArrayList<User> loginUser(String email , String password){



        SQLiteDatabase db = getReadableDatabase();
        ArrayList<User> users = new ArrayList<>();

        try{
            Cursor c = db.rawQuery("SELECT * FROM "+ USER_TABLE_NAME ,null) ;

            while(c.moveToNext()){
                String username = c.getString(1);
                String pass = c.getString(2);
                String email_ = c.getString(3);
                String phone = c.getString(4);
                if(email.equals(email_) && pass.equals(password)){
                    User user = new User(username,pass,phone,email_);
                    users.add(user);
                }

            }

            c.close();
        } catch (Exception e){

            System.out.println(e.getMessage());

        }


        return users;




    }


    void addNewTask(String username , String password , String email ,String title , String phone , String contents , String date){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Task_COL_USERNAME,username);
        cv.put(Task_COL_PASSWORD,password);
        cv.put(Task_COL_TITLE,title);
        cv.put(Task_COL_EMAIL,email);
        cv.put(Task_COL_PHONE,phone);
        cv.put(Task_COL_CONTENTS,contents);
        cv.put(Task_COL_DATE,date);
        cv.put(Task_COL_STATUS,0);
        db.insert(Task_TABLE_NAME,null,cv);
        String sqlQuery = "select * from "+ Task_TABLE_NAME;

        ArrayList<Task> tasks = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlQuery ,null) ;


        while(cursor.moveToNext()){

            String title_ = cursor.getString(5);
            String date_task = cursor.getString(7);
            String des = cursor.getString(6);
            int z  = cursor.getInt(8);
            boolean status = (z==0);
            int index = cursor.getInt(0);


            String username_task = cursor.getString(1);


            if(username_task.equals(username) && title_.equals(title) && des.equals(contents)){

                Task task = new Task(title_,date_task,des,status,index);
                tasks.add(task);
            }


        }




    }


    ArrayList<Task>  getTasksData (){

        String sqlQuery = "select * from "+ Task_TABLE_NAME ;
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlQuery ,null) ;


        while(cursor.moveToNext()){

            String title_ = cursor.getString(5);
            String date_task = cursor.getString(7);
            String des = cursor.getString(6);
            int z  = cursor.getInt(8);
            boolean status = (z==1);
            int index = cursor.getInt(0);

            //Task_COL_USERNAME+" = "+username+" and "+ Task_COL_PASSWORD+" = "+password+" and "+ Task_COL_EMAIL+" = "+email

            String username_task = cursor.getString(1);




                Task task = new Task(title_,date_task,des,status,index);
                tasks.add(task);



        }

        return   tasks;

    }


    public String dropTable (){



       try{
           SQLiteDatabase db = getWritableDatabase();
           db.execSQL("drop table if exists "+Task_TABLE_NAME);
           db.execSQL("drop table if exists "+USER_TABLE_NAME);

           onCreate(db);
       } catch (Exception e){

           return e.getMessage();
       }


        return "success";
    }



}
