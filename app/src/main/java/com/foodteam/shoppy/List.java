package com.foodteam.shoppy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class List extends AppCompatActivity {

    String tablename;

   /* @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt("MY_FIELD", 43);
        // ... other fields
        savedInstanceState.putString("", "");

        super.onSaveInstanceState(savedInstanceState);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //get name of table from previous page
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                tablename = null;
            } else {
                tablename = extras.getString("nameOfTable");
            }
        }

        //set the title of the page to the lists name
        ListName obj = new ListName();
        obj.setName(tablename);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(obj.toListName(obj.getName()));

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
                    enterdetails.putExtra("", tablename);
                    startActivity(enterdetails);
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

        // add check box, delete button, and prod details button per element in list


    public void deleteItem() {

    }

    //onclick for product details button
    public void prodDets() {

    }

    //onclick for adding products to list
    public void addProduct(View view) {
        //needs to add a row to <listnameTable>

        //add an Edit text, remove button, and product details button for product to a listView
    }





    //suggestions will be an expandableTextView


    public void back(View v) {
        Intent showList = new Intent( getApplicationContext(), List.class );
        startActivity(showList);
    }


    //set inCart depending on if checkbox is clicked
    public void cart(View v) {
        int cart;

        CheckBox check = (CheckBox)v;
        if(check.isChecked()){
            cart = 1;
        } else {
            cart = 0;
        }

        //set inCart for specific product to be int cart

    }

    public void doneShopping(View view) {
        Intent details = new Intent( getApplicationContext(), EnterDetails.class );
        details.putExtra("nameOfTable", tablename);
        startActivity(details);
    }
}