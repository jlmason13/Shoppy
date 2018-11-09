package com.foodteam.shoppy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.database.Cursor;

public class Settings extends AppCompatActivity {

    SQLiteDatabase shoppyDB = null;
    Cursor cur;

    public void stuff(){
        //Deletes the list
        cur = shoppyDB.rawQuery("select listName from Lists", new String [] {});
        while (cur.moveToNext()) {
            shoppyDB.execSQL("DROP TABLE IF EXISTS " + cur.getString(cur.getColumnIndex("listName")) );

        }

        //Deletes the product details
        cur = shoppyDB.rawQuery("select product from MasterList", new String [] {});
        while (cur.moveToNext()) {
            shoppyDB.execSQL("DROP TABLE IF EXISTS " + cur.getString(cur.getColumnIndex("product")) );
        }

        //Empty's the master list
        shoppyDB.execSQL("DELETE FROM MasterList" );
        shoppyDB.execSQL("VACUUM");

        //Empty's thw list of list
        shoppyDB.execSQL("DELETE FROM Lists" );
        shoppyDB.execSQL("VACUUM");

        //close cur
        cur.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Create/open shoppy.db and make it exclusive to the app
        shoppyDB = this.openOrCreateDatabase("shoppyDB", MODE_PRIVATE, null);

        Button startFresh = findViewById(R.id.StartFresh);
        startFresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Deletes the list
                cur = shoppyDB.rawQuery("select listName from Lists", new String[]

                        {
                        });
                while (cur.moveToNext())

                {
                    shoppyDB.execSQL("DROP TABLE IF EXISTS " + cur.getString(cur.getColumnIndex("listName")));

                }

                //Deletes the product details
                cur = shoppyDB.rawQuery("select product from MasterList", new String[]

                        {
                        });
                while (cur.moveToNext())

                {
                    shoppyDB.execSQL("DROP TABLE IF EXISTS " + cur.getString(cur.getColumnIndex("product")));
                }

                //Empty's the master list
                shoppyDB.execSQL("DELETE FROM MasterList");
                shoppyDB.execSQL("VACUUM");

                //Empty's thw list of list
                shoppyDB.execSQL("DELETE FROM Lists");
                shoppyDB.execSQL("VACUUM");

                //close cur
                cur.close();
            }
        });
//        Button Red = findViewById(R.id.red);
//        Red.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                ?? aColor = (??) R.color.colorPrimaryDark;
//                //<color name="colorPrimaryDark">#0x7f070099</color>
//            }
//        });
    }
}