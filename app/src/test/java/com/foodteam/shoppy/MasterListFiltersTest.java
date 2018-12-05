package com.foodteam.shoppy;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import android.database.sqlite.SQLiteDatabase;
import android.widget.CheckBox;
import android.widget.RadioButton;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MasterListFiltersTest {

    MasterListFilters activityMLF;
    SQLiteDatabase theDatabase;
    DBHandler Handler;

    @Before
    public void prepareForTesting() {
        MainMenu activityMM = Robolectric.setupActivity(MainMenu.class);
        Handler = DBHandler.getInstance(activityMM.getApplicationContext());
        theDatabase = activityMM.shoppyDB;
        Handler.onCreate(theDatabase);

        activityMLF = Robolectric.setupActivity(MasterListFilters.class);
    }

    @Test
    public void setArray() {
        activityMLF.setArray();
    }

    @Test
    public void onCheckboxClicked() {
        CheckBox box1 = activityMLF.findViewById(R.id.Name);
        CheckBox box2 = activityMLF.findViewById(R.id.Frequency);
        CheckBox box3 = activityMLF.findViewById(R.id.PriceAvg);
        CheckBox box4 = activityMLF.findViewById(R.id.LowPrice);
        CheckBox box5 = activityMLF.findViewById(R.id.TotalSpent);
        box1.performClick();
        box2.performClick();
        box3.performClick();
        box4.performClick();
        box5.performClick();
    }

    @Test
    public void onRadioButtonClicked() {
        RadioButton button1 = activityMLF.findViewById(R.id.NameAlphabetical);
        RadioButton button2 = activityMLF.findViewById(R.id.FrequencyLH);
        RadioButton button3 = activityMLF.findViewById(R.id.FrequencyHL);
        RadioButton button4 = activityMLF.findViewById(R.id.Lowest);
        RadioButton button5 = activityMLF.findViewById(R.id.TotalLH);
        RadioButton button6 = activityMLF.findViewById(R.id.TotalHL);
        button1.performClick();
        button2.performClick();
        button3.performClick();
        button4.performClick();
        button5.performClick();
        button6.performClick();
    }
}