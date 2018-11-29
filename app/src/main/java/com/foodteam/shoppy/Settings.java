package com.foodteam.shoppy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.database.Cursor;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    //Necessary for changing the background
    View view;

    TextView text;

    ColorChanges obj = new ColorChanges();
    SQLiteDatabase shoppyDB = null;
    DBHandler db;
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

        //Empty's the list of list
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
        db = DBHandler.getInstance(getApplicationContext());

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

        //Necessary for changing the background
        view = this.getWindow().getDecorView();

        //Background default
        view.setBackgroundResource(R.color.white);

        //Back button
        Button goBack = findViewById(R.id.Back);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, MainMenu.class));
            }
        });

        //Default text color
       // text.setTextColor(getResources().getColor(R.color.black));
        //text.setTextColor(ContextCompat.getColor(this, R.color.darkGreen));

        try {
            obj.setWindowCOlor(shoppyDB, db, view, getWindow());
        } catch (Exception e) {
            Log.e("ERROR WITH COLORS", "color stuff");
            e.printStackTrace();
        }

        //checks the current colors button
        RadioGroup rg = findViewById(R.id.radioGroup2);
        int color = db.getColor(shoppyDB);
        switch(color) {
            default:
                ((RadioButton)rg.findViewById(R.id.white)).setChecked(true);
                break;
            case 1:
                ((RadioButton)rg.findViewById(R.id.pink)).setChecked(true);
                break;

            case 2:
                ((RadioButton)rg.findViewById(R.id.purple)).setChecked(true);
                break;

            case 3:
                ((RadioButton)rg.findViewById(R.id.green)).setChecked(true);
                break;

            case 4:
                ((RadioButton)rg.findViewById(R.id.blue)).setChecked(true);
                break;

            case 5:
                ((RadioButton)rg.findViewById(R.id.yellow)).setChecked(true);
                break;

            case 6:
                ((RadioButton)rg.findViewById(R.id.orange)).setChecked(true);
                break;

            case 7:
                ((RadioButton)rg.findViewById(R.id.red)).setChecked(true);
                break;

        }
    }

    //Changes the background color on the button click
    public void goWhite(View v)
    {
        //view.setBackgroundResource(R.color.white);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        db.setColor(shoppyDB, 0);
        try {
            obj.setWindowCOlor(shoppyDB, db, view, getWindow());
        } catch (Exception e) {
            Log.e("ERROR WITH COLORS", "color stuff");
            e.printStackTrace();
        }

    }

    public void goPink(View v) {
        //view.setBackgroundResource(R.color.lightPink);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.darkPink));
        db.setColor(shoppyDB, 1);
        try {
            obj.setWindowCOlor(shoppyDB, db, view, getWindow());
        } catch (Exception e) {
            Log.e("ERROR WITH COLORS", "color stuff");
            e.printStackTrace();
        }

    }

    public void goPurple(View v) {
        //view.setBackgroundResource(R.color.lightPurple);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.darkPurple));
        db.setColor(shoppyDB, 2);
        try {
            obj.setWindowCOlor(shoppyDB, db, view, getWindow());
        } catch (Exception e) {
            Log.e("ERROR WITH COLORS", "color stuff");
            e.printStackTrace();
        }

    }

    public void goGreen(View v) {
        //view.setBackgroundResource(R.color.lightGreen);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.darkGreen));
        db.setColor(shoppyDB, 3);
        try {
            obj.setWindowCOlor(shoppyDB, db, view, getWindow());
        } catch (Exception e) {
            Log.e("ERROR WITH COLORS", "color stuff");
            e.printStackTrace();
        }

    }

    public void goBlue(View v) {
        //view.setBackgroundResource(R.color.lightBlue);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.darkBlue));
        db.setColor(shoppyDB, 4);
        try {
            obj.setWindowCOlor(shoppyDB, db, view, getWindow());
        } catch (Exception e) {
            Log.e("ERROR WITH COLORS", "color stuff");
            e.printStackTrace();
        }

    }

    public void goYellow(View v) {
        //view.setBackgroundResource(R.color.lightYellow);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.darkYellow));
        db.setColor(shoppyDB, 5);
        try {
            obj.setWindowCOlor(shoppyDB, db, view, getWindow());
        } catch (Exception e) {
            Log.e("ERROR WITH COLORS", "color stuff");
            e.printStackTrace();
        }

    }

    public void goOrange(View v) {
        //view.setBackgroundResource(R.color.lightOrange);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.darkOrange));
        db.setColor(shoppyDB, 6);
        try {
            obj.setWindowCOlor(shoppyDB, db, view, getWindow());
        } catch (Exception e) {
            Log.e("ERROR WITH COLORS", "color stuff");
            e.printStackTrace();
        }

    }

    public void goRed(View v) {
        //view.setBackgroundResource(R.color.lightRed);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.darkRed));
        db.setColor(shoppyDB, 7);
        try {
            obj.setWindowCOlor(shoppyDB, db, view, getWindow());
        } catch (Exception e) {
            Log.e("ERROR WITH COLORS", "color stuff");
            e.printStackTrace();
        }

    }

}