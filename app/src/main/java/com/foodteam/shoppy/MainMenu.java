package com.foodteam.shoppy;

import android.content.Context;
import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

public class MainMenu extends AppCompatActivity {

    SQLiteDatabase shoppyDB = null;
    Context mcontext;

    public MainMenu(Context context){
        mcontext = context;
    }

    public String hello(){
        //With the massive unit test troubles we've been having,
        //this method shows us that tests can work!!!!
        return mcontext.getPackageName();
    }

    public void createDatabase(){
        try{
            //Create/open shoppy.db and make it exclusive to the app
            shoppyDB = this.openOrCreateDatabase("shoppyDB", MODE_PRIVATE, null);
            //Create MasterList and Lists tables
            shoppyDB.execSQL("CREATE TABLE IF NOT EXISTS MasterList " +
                    "(product VARCHAR primary key, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), totalSpent float(9,2));");
            shoppyDB.execSQL("CREATE TABLE IF NOT EXISTS Lists " + "(listName VARCHAR primary key);");
            //db on file system

            File database = getApplicationContext().getDatabasePath("shoppyDB.db");
            //Show if file was actually set to shoppyDB

            if (!database.exists()){
                Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Database Missing", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            Log.e("DATABASE ERROR", "Problem creating database");
        }
    }

    /*
    protected void onDestroy(){
        shoppyDB.close();
        super.onDestroy();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        createDatabase();

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
    }
}