package com.ankitkumar.app.todoapp.karya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.http.UrlRequest;

import java.util.ArrayList;

public class dbOpenHelper extends SQLiteOpenHelper {

    Context context;
    final static String DB_NAME = "Database_Karya.app";
    final static int VERSION = 1;

    final static String USER_TABLE_NAME = "USERS";
    final static String USER_COL_ID = "ID";
    final static String USER_COL_USERNAME = "USERNAME";
    final static String USER_COL_EMAIL = "EMAIL";
    final static String USER_COL_PASSWORD = "PASSWORD";
    final static String USER_COL_PHONE = "PHONES";
    final static String Task_TABLE_NAME = "Tasks";
    final static String Task_COL_ID = "task_ids";
    final static String Task_COL_USERNAME = "task_names";
    final static String Task_COL_EMAIL = "EMAIL";
    final static String Task_COL_PASSWORD = "PASSWORD";
    final static String Task_COL_PHONE = "PHONES";
    final static String Task_COL_TITLE = "title";
    final static String Task_COL_CONTENTS = "task_contents";
    final static String Task_COL_DATE = "date";
    final static String Task_COL_STATUS = "status";


    public dbOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sqlQuery = "create table if not exists " + USER_TABLE_NAME + " ( " + USER_COL_ID + " integer primary key autoincrement, " + USER_COL_USERNAME + " String, " + USER_COL_PASSWORD + " String, " + USER_COL_EMAIL + " String, " + USER_COL_PHONE + " String )";
        sqLiteDatabase.execSQL(sqlQuery);

        String sqlQuery2 = "create table if not exists " + Task_TABLE_NAME + "( " + Task_COL_ID + " integer primary key autoincrement, " + Task_COL_USERNAME + " String, " + Task_COL_PASSWORD + " String, " + Task_COL_EMAIL + " String, " + Task_COL_PHONE + " String, " + Task_COL_TITLE + " String, " + Task_COL_CONTENTS + " String, " + Task_COL_DATE + " String, " + Task_COL_STATUS + " integer )";
        sqLiteDatabase.execSQL(sqlQuery2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists " + USER_TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    void addUser(String name, String pass, String email, String phone) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_USERNAME, name);
        contentValues.put(USER_COL_PASSWORD, pass);
        contentValues.put(USER_COL_EMAIL, email);
        contentValues.put(USER_COL_PHONE, phone);
        db.insert(USER_TABLE_NAME, null, contentValues);

    }

    void addNewTask(String username, String password, String email, String title, String phone, String contents, String date) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Task_COL_USERNAME, username);
        cv.put(Task_COL_PASSWORD, password);
        cv.put(Task_COL_TITLE, title);
        cv.put(Task_COL_EMAIL, email);
        cv.put(Task_COL_PHONE, phone);
        cv.put(Task_COL_CONTENTS, contents);
        cv.put(Task_COL_DATE, date);
        cv.put(Task_COL_STATUS, 1);
        db.insert(Task_TABLE_NAME, null, cv);
    }

    ArrayList<User> getUserData() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null);
        ArrayList<User> users = new ArrayList<>();
        while (c.moveToNext()) {
            String username = c.getString(1);
            String password = c.getString(2);
            String email = c.getString(3);
            String phone = c.getString(4);
            User user = new User(username, password, phone, email);
            users.add(user);
        }
        c.close();
        return users;
    }

    ArrayList<User> loginUser(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<User> users = new ArrayList<>();
        try {
            Cursor c = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null);

            while (c.moveToNext()) {
                String username = c.getString(1);
                String pass = c.getString(2);
                String email_ = c.getString(3);
                String phone = c.getString(4);
                if (email.equals(email_) && pass.equals(password)) {
                    User user = new User(username, pass, phone, email_);
                    users.add(user);
                }
            }
            c.close();
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
        return users;
    }




    public ArrayList<Task> getAllTasks(String USERNAME , String EMAIL ,String PASSWORD ){
        SQLiteDatabase db = getReadableDatabase();
        String sqlQuery = "select * from "+ Task_TABLE_NAME;
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlQuery ,null) ;
        while(cursor.moveToNext()){
            int index = cursor.getInt(0);
            String title_ = cursor.getString(5);
            String date_task = cursor.getString(7);
            String des = cursor.getString(6);
            int z  = cursor.getInt(8);
            boolean status = (z==1);
            String username_ = cursor.getString(1);
            String email_ = cursor.getString(3);
            String password_ = cursor.getString(2);
            if(USERNAME.equals(username_) && EMAIL.equals(email_) && PASSWORD.equals(password_)){
                Task task = new Task(title_,date_task,des,status,index);
                tasks.add(task);
            }
        }
        cursor.close();
        return  tasks;
    }

    void updateTaskStatus (boolean status,int i) {

        SQLiteDatabase db = getWritableDatabase();
        String query = "update " + Task_TABLE_NAME +" set "+Task_COL_STATUS+" = "+ status;
        ContentValues cv = new ContentValues();
        if(status){
            cv.put(Task_COL_STATUS,1);
        } else {
            cv.put(Task_COL_STATUS,0);
        }

        db.update(Task_TABLE_NAME,cv,Task_COL_ID+" = "+i,null);

    }

    void getStatus (int index){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * " + " from "+ Task_TABLE_NAME,null);


        while(cursor.moveToNext() ){

            int i = cursor.getInt(0);
            if(i==index){
                System.out.println("task click : "+i);
                int status = cursor.getInt(8);
                System.out.println("status code : "+status);
                updateTaskStatus(status==1,i);

            } else {

                System.out.println("Method is called but 😒");
            }
        }
        System.out.println("Method called.");
        System.out.println(cursor.toString());
    }


}





