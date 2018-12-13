package com.foodteam.shoppy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

//Tell Mockito to validate if usage of framework is right.
//@RunWith(MockitoJUnitRunner.class)
@RunWith(RobolectricTestRunner.class)
public class MainMenuTest {
    SQLiteDatabase theDatabase;
    MainMenu activityMM;

    @Before
    public void prepareForTesting() {
        activityMM = Robolectric.setupActivity(MainMenu.class);
        DBHandler Handler = DBHandler.getInstance(activityMM.getApplicationContext());
        theDatabase = activityMM.shoppyDB;
        Handler.onCreate(theDatabase);
    }

    @Test
    public void createDatabaseTest() {
        activityMM = Robolectric.setupActivity(MainMenu.class);
        DBHandler Handler = DBHandler.getInstance(activityMM.getApplicationContext());
        theDatabase = activityMM.shoppyDB;
        Handler.onCreate(theDatabase);
    }

    @Test
    public void clickList(){
        //click button
        Button goToLists = activityMM.findViewById(R.id.buttLists);
        goToLists.performClick();
    }

    @Test
    public void clickSettings(){
        //click button
        Button submit = activityMM.findViewById(R.id.buttSettings);
        submit.performClick();
    }

    @Test
    public void clickMList(){
        //click button
        Button submit = activityMM.findViewById(R.id.buttMasterList);
        submit.performClick();
    }
}
