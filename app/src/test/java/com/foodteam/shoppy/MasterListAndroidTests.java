package com.foodteam.shoppy;

import android.content.Context;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

//Despite saying Android Test, it must stay in current folder to function

@RunWith(RobolectricTestRunner.class)
//@Config(constants = BuildConfig.class)
public class MasterListAndroidTests {
    SQLiteDatabase theDatabase;

    //TODO CONT working with Robolectric
    @Before
    public void prepareForTesting() {
        MainMenu activityMM = Robolectric.setupActivity(MainMenu.class);
        activityMM.createDatabase();
        theDatabase = activityMM.shoppyDB;
    }
}