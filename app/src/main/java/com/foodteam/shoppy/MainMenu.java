package com.foodteam.shoppy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Main Menu Button: "LISTS"
        Button gotoLists = findViewById(R.id.buttLists);
        gotoLists.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Upon clicking "LISTS",
                //Intent startIntent = new Intent(getApplicationContext(), MainMenu.class);
                //startActivity(startIntent);
                startActivity(new Intent(MainMenu.this, Lists.class));
            }
        });

        //Main Menu Button: "MASTER LIST"
        Button gotoMasterList = findViewById(R.id.buttMasterList);
        gotoLists.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, MasterList.class));
            }
        });

        //Main Menu Button: "SETTINGS"
        Button gotoSettings = findViewById(R.id.buttSettings);
        gotoLists.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Settings.class));
            }
        });
    }
}