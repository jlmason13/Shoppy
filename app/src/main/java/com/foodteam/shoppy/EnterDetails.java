package com.foodteam.shoppy;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class EnterDetails extends AppCompatActivity {

    SQLiteDatabase theDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_details);
    }

    private void openTheDatabase() {
        try {
            theDatabase = this.openOrCreateDatabase("Shoppy", MODE_PRIVATE, null );
        } catch (Exception e) {
            Log.e("DATABASE ERROR","Zofi: Error opening Database");
        }

    }
}
