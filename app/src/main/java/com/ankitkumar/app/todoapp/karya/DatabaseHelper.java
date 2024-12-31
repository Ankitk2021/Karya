package com.ankitkumar.app.todoapp.karya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private  static final  String DATABASE_NAME = "User";
    private  static final  String TABLE_NAME = "User_Details_SECOND";
    private  static final String COLUMN_ID = "id";
    private static final  String key_username = "username";
    private static final  String key_password = "password";
    private static final  String key_phone_number = "phone_number";
    private static final  String key_email = "email";




    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE "+" if not exists "+TABLE_NAME+"( "+COLUMN_ID+" INTEGER primary key autoincrement ," + key_username + " String ,"+key_password+" String ,"+key_phone_number+" String ,"+key_email+" String "+")";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("drop table if exists "+ TABLE_NAME);
    onCreate(sqLiteDatabase);
    }

    void addUser(String username,String password,String phone_number,String Email){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(key_username,username);
        contentValues.put(key_password,password);
        contentValues.put(key_phone_number,phone_number);
        contentValues.put(key_email,Email);
        db.insert(TABLE_NAME,null,contentValues);
        db.close();

    }

   public ArrayList<User> getData(Context context){

        ArrayList<User> usersList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

      try{

          Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME,null);

          while(cursor.moveToNext()){
              String username = cursor.getString(2);
              String password = cursor.getString(2);
              String phone_number = cursor.getString(3);
              String email = cursor.getString(0);
              User user = new User(username,password,phone_number,email);
              usersList.add(user);

          }

          cursor.close();

      }catch (Exception e){
          Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
      }
        db.close();
return usersList;

    }


}
