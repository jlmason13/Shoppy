package com.foodteam.shoppy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
    public void preparingForTesting() {
        MainMenu activityMM = Robolectric.setupActivity(MainMenu.class);
        DBHandler Handler = DBHandler.getInstance(activityMM.getApplicationContext());
        theDatabase = activityMM.shoppyDB;
        Handler.onCreate(theDatabase);
    }

    @Test
    public void createDatabaseTest(){
        theDatabase = activityMM.shoppyDB;
        assertNotNull(theDatabase);
    }
}
