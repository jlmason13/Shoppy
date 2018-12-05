package com.foodteam.shoppy;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import android.database.sqlite.SQLiteDatabase;
import android.widget.CheckBox;
import android.widget.RadioButton;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class FiltersTest {

    Filters activityF;
    SQLiteDatabase theDatabase;

    @Before
    public void prepareForTesting() {
        MainMenu activityMM = Robolectric.setupActivity(MainMenu.class);
        DBHandler Handler = DBHandler.getInstance(activityMM.getApplicationContext());
        theDatabase = activityMM.shoppyDB;
        Handler.onCreate(theDatabase);

        activityF = Robolectric.setupActivity(Filters.class);
    }

    @Test
    public void initializeArray() {
        activityF.initializeArray();
    }

    @Test
    public void onCheckBoxClicked() {
        CheckBox box1 = activityF.findViewById(R.id.brand);
        CheckBox box2 = activityF.findViewById(R.id.size);
        CheckBox box3 = activityF.findViewById(R.id.frequency);
        CheckBox box4 = activityF.findViewById(R.id.avgPrice);
        CheckBox box5 = activityF.findViewById(R.id.lowestPrice);
        CheckBox box6 = activityF.findViewById(R.id.highestPrice);
        CheckBox box7 = activityF.findViewById(R.id.store);
        CheckBox box8 = activityF.findViewById(R.id.totalSpent);
        CheckBox box9 = activityF.findViewById(R.id.date);
        box1.performClick();
        box2.performClick();
        box3.performClick();
        box4.performClick();
        box5.performClick();
        box6.performClick();
        box7.performClick();
        box8.performClick();
        box9.performClick();
    }

    @Test
    public void onRadioClicked() {
        RadioButton button1 = activityF.findViewById(R.id.MostRecentPurchase);
        RadioButton button2 = activityF.findViewById(R.id.OldestPurchase);
        RadioButton button3 = activityF.findViewById(R.id.StoreAlephabetical);
        RadioButton button4 = activityF.findViewById(R.id.BrandAlephabetical);
        RadioButton button5 = activityF.findViewById(R.id.PriceAverage);
        RadioButton button6 = activityF.findViewById(R.id.PriceLowest);
        RadioButton button7 = activityF.findViewById(R.id.PriceHeighest);
        button1.performClick();
        button2.performClick();
        button3.performClick();
        button4.performClick();
        button5.performClick();
        button6.performClick();
        button7.performClick();
    }
}