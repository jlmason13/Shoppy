package com.foodteam.shoppy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DBHandler extends SQLiteOpenHelper {
    private static DBHandler sInstance;

    private static final String DATABASE_NAME = "shoppyDB";
    //private static final String DATABASE_TABLE = "table_name";
    private static final int DATABASE_VERSION = 1;

    //getInstance() ensures only one DBHandler will exist at any time.
    //If sInstance not initialized, it will be created. If one created,
    //It will be returned. Call this by DBHandler.getInstance(context)
    //and NOT ... = new DBHandler(context).
    public static synchronized DBHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DBHandler(context.getApplicationContext());
        }
        return sInstance;
    }
    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create two tables, MasterList & Lists
        db.execSQL("CREATE TABLE IF NOT EXISTS MasterList " +
                "(product VARCHAR primary key, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), totalSpent float(9,2));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Lists " + "(listName VARCHAR primary key);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}


//============
    /*
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shoppyDB";
    //public static final String TABLE_NAME = "MasterList";
    //public static final String COLUMN_ID = "StudentID";
    //public static final String COLUMN_NAME = "StudentName";

    //initialize the database
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create two tables, MasterList & Lists
        db.execSQL("CREATE TABLE IF NOT EXISTS MasterList " +
                "(product VARCHAR primary key, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), totalSpent float(9,2));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Lists " + "(listName VARCHAR primary key);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    //USED TO LOAD DATA FROM DATABASE
    /*public String loadMasterList() {
        String result = "";
        SQLiteDatabase db = this.getWritableDatabase();
        //Implement SQL Statement  & display result to cursor object
        Cursor cursor = db.rawQuery("SELECT * FROM MasterList" , null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }*/

    //ADD TO THE DATABASE
    /*public void addHandler(Lists lists) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, lists);
        values.put(COLUMN_NAME, student.getStudentName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }*/

    //FIND SPECIFIC ITEMS IN DATABASE
    /*public MainMenu findHandler(String studentname) {
        Stringquery = "Select * FROM " + TABLE_NAME + "WHERE" + COLUMN_NAME + " = " + "'" + studentname + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Student student = new Student();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            student.setID(Integer.parseInt(cursor.getString(0)));
            student.setStudentName(cursor.getString(1));
            cursor.close();
        } else {
            student = null;
        }
        db.close();
        return student;
    }*/

    //DELETE FROM DATABASE
    /*public boolean deleteHandler(int ID) {
        booleanresult = false;
        Stringquery = "Select*FROM" + TABLE_NAME + "WHERE" + COLUMN_ID + "= '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Student student = new Student();
        if (cursor.moveToFirst()) {
            student.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, COLUMN_ID + "=?",
                    newString[] {
                String.valueOf(student.getID())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }*/

    //UPDATE A RECORD IN THE DATABASE
    /*public boolean updateHandler(int ID, String name) {SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, ID);
        args.put(COLUMN_NAME, name);
        return db.update(TABLE_NAME, args, COLUMN_ID + "=" + ID, null) > 0;
    }*/


}
