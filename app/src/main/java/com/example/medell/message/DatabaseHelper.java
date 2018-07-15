package com.example.medell.message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/*
This class helps to maintin the database of the messages send by the user
*/

public class DatabaseHelper extends SQLiteOpenHelper {

    String Messege_History;

    public DatabaseHelper(Context context) {
        super(context, "Messages", null, 1);
    }
  
	
  @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
		
		/*
		This creates a table name "history" in database 
		*/

        Messege_History = "create table history ( name Text , number Text , otp Text, timeofsend Text )";
        sqLiteDatabase.execSQL(Messege_History);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS history");
        onCreate(sqLiteDatabase);
    }

	/*
	This method helps to count the number of tuples present in the table
	*/
    int counthistoryTuples(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor mCount= sqLiteDatabase.rawQuery("select count(*) from history", null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }

	/*
	This method helps to insert data in the table
	*/
    boolean insertMessageDetails(String name, String number, String otp, String time) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("number",number);
        contentValues.put("otp",otp);
        contentValues.put("timeofsend",time);
        sqLiteDatabase.insert("history", null, contentValues);
        return true;
    }

	/*
	This method helps to retrieve data from the table
	*/

    Cursor gethistoryData()
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor  cursor=sqLiteDatabase.rawQuery("select * from history order by timeofsend DESC",null);
        return  cursor;
    }
}
