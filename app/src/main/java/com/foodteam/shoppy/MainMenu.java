package com.foodteam.shoppy;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainMenu extends AppCompatActivity {

    SQLiteDatabase shoppyDB;

    public MainMenu(){}
    public MainMenu(SQLiteDatabase shoppyDB){
        this.shoppyDB = shoppyDB;
    }
    public void setShoppyDB(SQLiteDatabase shoppyDB){
        this.shoppyDB = shoppyDB;
    }
    public SQLiteDatabase getShoppyDB(){
        return this.shoppyDB;
    }

    public void createDatabase(){
        try{
            //Create/open shoppy.db and make it exclusive to the app
            shoppyDB = this.openOrCreateDatabase("shoppyDB", MODE_PRIVATE, null);
            //Create MasterList and Lists tables
            shoppyDB.execSQL("CREATE TABLE IF NOT EXISTS MasterList " +
                    "(product VARCHAR primary key, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), totalSpent float(9,2));");
            shoppyDB.execSQL("CREATE TABLE IF NOT EXISTS Lists " + "(listName VARCHAR primary key);");
            shoppyDB.execSQL("CREATE TABLE IF NOT EXISTS dummyList(product VARCHAR unique, inCart int);");
            shoppyDB.execSQL("CREATE TABLE IF NOT EXISTS dummyProduct(brand VARCHAR, size integer, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), highestPrice float(9,2), store VARCHAR, totalSpent float(9,2),  primary key (brand, size)); ");
            shoppyDB.execSQL("CREATE TABLE IF NOT EXISTS settings(color integer default 0);");

            int settingsExists = 0;
            Cursor c = shoppyDB.rawQuery("select color from settings", null);
            settingsExists = c.getCount();
            c.close();
            if (settingsExists == 0) {
                ContentValues values = new ContentValues();
                values.put("color", 0);
                shoppyDB.insert("settings", null, values);
            }

            //db on file system
            File database = getApplicationContext().getDatabasePath("shoppyDB");    //altered by Zofi
            //Show if file was actually set to shoppyDB
            if (database.exists()){
                Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Database Missing", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            Log.e("DATABASE ERROR", "Problem creating database");
        }
    }

    protected void onDestroy(){
        shoppyDB.close();
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        createDatabase();                               //added by Zofi for debugging purposes

//USE THIS CODE TO USE THE DATABASE:
        //I'm using the database:
        DBHandler dbHelper = DBHandler.getInstance(getApplicationContext()); //Only one instance can be active at a time. Protect race conditions
        //Toast.makeText(this, "Added to ML", Toast.LENGTH_SHORT).show(); //Use TOAST if you want to see a pop-up message
/*        Log.e("Create Item", "Create eggs in MasterList"); //Use LOG if you want to see it in file dump
        dbHelper.createItemMasterList("eggs", 2, (float) 1.75, (float) 0.98, (float) 2.78); //add eggs to MasterList
        dbHelper.createItemMasterList("milk", 1, (float) 1.25, (float) 1.00, (float) 3.75); //add milk to MasterList
        dbHelper.createItemMasterList("cheese", 3, (float) 4.25, (float) 3.15, (float) 5.55); //add milk to MasterList
        /*
        //Toast.makeText(this, "Deleted in ML", Toast.LENGTH_SHORT).show();
        Log.e("Delete Item", "Delete eggs from MasterList"); //Use LOG if you want to see it in file dump
        dbHelper.deleteProductMasterList("eggs"); //Delete product from MasterList (whole row)

        Log.e("Number Items", "Items in MasterList " + dbHelper.countMasterList()); //Use LOG if you want to see it in file dump
*/

//RETAIN COLOR CHANGES BETWEEN PAGES:
        ColorChanges obj = new ColorChanges();
        View view = this.getWindow().getDecorView();
        obj.setWindowCOlor(shoppyDB, dbHelper, view, getWindow());

//BUTTONS:
        //Main Menu Button: "LISTS"
        Button gotoLists = findViewById(R.id.buttLists);
        gotoLists.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Lists.class));
            }
        });

        //Main Menu Button: "MASTER LIST"
        Button gotoMasterList = findViewById(R.id.buttMasterList);
        gotoMasterList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, MasterList.class));
            }
        });

        //Main Menu Button: "SETTINGS"
        Button gotoSettings = findViewById(R.id.buttSettings);
        gotoSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Settings.class));
            }
        });

        //Zofi Reminder: remove crazyness from MasterList

    }
}