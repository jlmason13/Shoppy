package com.foodteam.shoppy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // go to enter details page
        Button done = findViewById(R.id.doneShopping);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent enterdetails = new Intent( getApplicationContext(), EnterDetails.class );
                startActivity(enterdetails);
            }
        });
    }

    // add check box, delete button, and prod details button per element in list


    //suggestions will be an expandableTextView


}