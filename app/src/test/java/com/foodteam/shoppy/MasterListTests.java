package com.foodteam.shoppy;

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
public class MasterListTests {
    //Put tests here so Jococo can see them.
    MasterList testerML = new MasterList();
    SQLiteDatabase theDatabase;

    @Test
    public void testDatabaseConnection() {                      // test works :)
        theDatabase = testerML.openTheDatabase();
        assertNotNull( "checking Database Opened", theDatabase );
    }

    @Test
    public void testLoadingMasterListData() {
        assertEquals( 1, testerML.addMasterListProductData() );
    }

}
