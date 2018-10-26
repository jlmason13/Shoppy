package com.foodteam.shoppy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Lists extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);


        // create list button goes to empty list page
        Button createlistbutton = findViewById(R.id.button_createlist);
        createlistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //txt box for list name
                //create table titled <txt from box>
                //go to list page w/ info from ^table
               Intent showList = new Intent( getApplicationContext(), List.class );
               startActivity(showList);
            }
        });


        // dynamically add buttons to go to other existing lists

    }


    //Create List Button
        //when clicked:
            //txt box for list name
            //create table titled <txt from box>
            //go to list page w/ info from ^table



    //need to add buttons (list button and remove list buttons) for each exiting list?
        // table of lists will exist in database
        // a new view w/ button to enter list and button to delete list per row in listOlists


}