package com.foodteam.shoppy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Test;

import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
public class EnterDetailsTests {
    //Put tests here so Jococo can see them.
    EnterDetails testerED = new EnterDetails();
    SQLiteDatabase theDatabase;

    @Test
    public void testDatabaseConnection() {                      // test works :)
        theDatabase = testerED.openTheDatabase();
        assertNotNull( "checking Database Opened", theDatabase );
    }

    @Test
    public void testUpdatingProduct() {
        testerED.updateProductDetails( "test", (float) 42.00, (float) 42.00, "testB", "test");
        boolean working = false;
        Cursor masterData = theDatabase.rawQuery( "select * from MasterList where product = test;",null);
        Cursor productData = theDatabase.rawQuery( "select * from test where brand = testB;",null);
        if (masterData != null && (masterData.getCount() == 1) ) {
            if (productData != null && (productData.getCount() == 1)) {
                working = true;
            }
        }
        assertEquals( true, working );
    }

}
//TODO write proper tests
