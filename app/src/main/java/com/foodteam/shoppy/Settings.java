package com.foodteam.shoppy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.database.Cursor;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class Settings extends AppCompatActivity {

    //Necessary for changing the background
    View view;

    //Keeps the color change between pages
    private int[] colors;

    //why?

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
        setArray();

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

        //Necessary for changing the background
        view = this.getWindow().getDecorView();
        //Background default
        view.setBackgroundResource(R.color.white);

        //Back button
        Button goBack = findViewById(R.id.Back);
        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent whatever = new Intent(Settings.this, Settings.class);
                whatever.putExtra("Color Change", colors);
                startActivity(whatever);
                startActivity(new Intent(Settings.this, MainMenu.class));
            }
        });


    }

    //Changes the background color on the button click
    public void goWhite(View v)
    {
        view.setBackgroundResource(R.color.white);
    }

    public void goPink(View v) {
        view.setBackgroundResource(R.color.lightPink);
    }

    public void goPurple(View v) {
        view.setBackgroundResource(R.color.lightPurple);
    }

    public void goGreen(View v) {
        view.setBackgroundResource(R.color.lightGreen);
    }

    public void goBlue(View v) {
        view.setBackgroundResource(R.color.lightBlue);
    }

    public void goYellow(View v) {
        view.setBackgroundResource(R.color.lightYellow);
    }

    public void goOrange(View v) {
        view.setBackgroundResource(R.color.lightOrange);
    }

    public void goRed(View v) {
        view.setBackgroundResource(R.color.lightRed);
    }


    //Keeps color change between pages
    protected void setArray() { colors = new int[] {1, 1, 1, 1, 1, 1, 1, 0};
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.white:
                if (checked) {
                    colors[0] = 1;
                    colors[1] = 1;
                    colors[2] = 1;
                    colors[3] = 1;
                    colors[4] = 1;
                    colors[5] = 1;
                    colors[6] = 1;
                    colors[7] = 0;
                }
                break;

            case R.id.pink:
                if (checked) {
                    colors[0] = 1;
                    colors[1] = 1;
                    colors[2] = 1;
                    colors[3] = 1;
                    colors[4] = 1;
                    colors[5] = 1;
                    colors[6] = 0;
                    colors[7] = 1;
                }
                break;
            case R.id.purple:
                if (checked) {
                    colors[0] = 1;
                    colors[1] = 1;
                    colors[2] = 1;
                    colors[3] = 1;
                    colors[4] = 1;
                    colors[5] = 0;
                    colors[6] = 1;
                    colors[7] = 1;
                }
                break;
            case R.id.green:
                if (checked) {
                    colors[0] = 1;
                    colors[1] = 1;
                    colors[2] = 1;
                    colors[3] = 1;
                    colors[4] = 0;
                    colors[5] = 1;
                    colors[6] = 1;
                    colors[7] = 1;
                }
                break;
            case R.id.blue:
                if (checked) {
                    colors[0] = 1;
                    colors[1] = 1;
                    colors[2] = 1;
                    colors[3] = 0;
                    colors[4] = 1;
                    colors[5] = 1;
                    colors[6] = 1;
                    colors[7] = 1;
                }
                break;
            case R.id.yellow:
                if (checked) {
                    colors[0] = 1;
                    colors[1] = 1;
                    colors[2] = 0;
                    colors[3] = 1;
                    colors[4] = 1;
                    colors[5] = 1;
                    colors[6] = 1;
                    colors[7] = 1;
                }
                break;
            case R.id.orange:
                if (checked) {
                    colors[0] = 1;
                    colors[1] = 0;
                    colors[2] = 1;
                    colors[3] = 1;
                    colors[4] = 1;
                    colors[5] = 1;
                    colors[6] = 1;
                    colors[7] = 1;
                }
                break;
            case R.id.red:
                if (checked) {
                    colors[0] = 0;
                    colors[1] = 1;
                    colors[2] = 1;
                    colors[3] = 1;
                    colors[4] = 1;
                    colors[5] = 1;
                    colors[6] = 1;
                    colors[7] = 1;
                }
                break;
        }
    }
}