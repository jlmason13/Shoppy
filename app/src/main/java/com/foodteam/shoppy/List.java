package com.foodteam.shoppy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class List extends AppCompatActivity {
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt("MY_FIELD", 43);
        // ... other fields
        savedInstanceState.putString("", ""/*this needs to be the */);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //connect to db
        try (SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("shoppyDB.db", MODE_PRIVATE, null)) {

            //add item
            Button addItem = findViewById(R.id.addItem);
            addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //add an item to the list, add to <listname> table in db
                    //add a view with a text box and a product details button
                }
            });

            // go to enter details page
            Button done = findViewById(R.id.doneShopping);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent enterdetails = new Intent( getApplicationContext(), EnterDetails.class );
                    startActivity(enterdetails);
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

        // add check box, delete button, and prod details button per element in list


        //suggestions will be an expandableTextView





}