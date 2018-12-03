package com.foodteam.shoppy;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

public class ColorChanges extends AppCompatActivity {

    public void setWindowCOlor(SQLiteDatabase shoppy, DBHandler help, View view, Window window){
        int color = help.getColor(shoppy);

        switch(color) {
            default:
                view.setBackgroundResource(R.color.white);
                window.setStatusBarColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimaryDark));
                window.setNavigationBarColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimaryDark));
                break;
            case 1:
                view.setBackgroundResource(R.color.lightPink);
                window.setStatusBarColor(ContextCompat.getColor(view.getContext(), R.color.darkPink));
                window.setNavigationBarColor(ContextCompat.getColor(view.getContext(), R.color.darkPink));
                break;

            case 2:
                view.setBackgroundResource(R.color.lightPurple);
                window.setStatusBarColor(ContextCompat.getColor(view.getContext(), R.color.darkPurple));
                window.setNavigationBarColor(ContextCompat.getColor(view.getContext(), R.color.darkPurple));
                break;

            case 3:
                view.setBackgroundResource(R.color.lightGreen);
                window.setStatusBarColor(ContextCompat.getColor(view.getContext(), R.color.darkGreen));
                window.setNavigationBarColor(ContextCompat.getColor(view.getContext(), R.color.darkGreen));
                break;

            case 4:
                view.setBackgroundResource(R.color.lightBlue);
                window.setStatusBarColor(ContextCompat.getColor(view.getContext(), R.color.darkBlue));
                window.setNavigationBarColor(ContextCompat.getColor(view.getContext(), R.color.darkBlue));
                break;

            case 5:
                view.setBackgroundResource(R.color.lightYellow);
                window.setStatusBarColor(ContextCompat.getColor(view.getContext(), R.color.darkYellow));
                window.setNavigationBarColor(ContextCompat.getColor(view.getContext(), R.color.darkYellow));
                break;

            case 6:
                view.setBackgroundResource(R.color.lightOrange);
                window.setStatusBarColor(ContextCompat.getColor(view.getContext(), R.color.darkOrange));
                window.setNavigationBarColor(ContextCompat.getColor(view.getContext(), R.color.darkOrange));
                break;

            case 7:
                view.setBackgroundResource(R.color.lightRed);
                window.setStatusBarColor(ContextCompat.getColor(view.getContext(), R.color.darkRed));
                window.setNavigationBarColor(ContextCompat.getColor(view.getContext(), R.color.darkRed));
                break;

        }

    }
}
