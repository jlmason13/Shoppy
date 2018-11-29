package com.foodteam.shoppy;

        import android.content.Context;


        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;

        import static org.hamcrest.core.AllOf.allOf;
        import static org.junit.Assert.assertEquals;

        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.widget.Button;
        import android.widget.TextView;

        import org.junit.Rule;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.robolectric.Robolectric;
        import org.robolectric.RobolectricTestRunner;
        import org.robolectric.annotation.Config;


        import static org.hamcrest.core.AllOf.allOf;
        import static org.junit.Assert.assertEquals;
        import static org.junit.Assert.assertThat;
        import static org.junit.Assert.assertTrue;
        import static org.junit.Assert.fail;

//Despite saying Android Test, it must stay in current folder to function

@RunWith(RobolectricTestRunner.class)
//@Config(constants = BuildConfig.class)
public class EnterDetailsAndroidTests {
    SQLiteDatabase theDatabase;

//TODO CONT working with Robolectric
    @Before
    public void prepareForTesting() {
        MainMenu activityMM = Robolectric.setupActivity(MainMenu.class);
        activityMM.createDatabase();
        theDatabase = activityMM.shoppyDB;

        theDatabase.execSQL("CREATE TABLE IF NOT EXISTS GroceryList" +
                "( product VARCHAR primary key, inCart int) ;");
        theDatabase.execSQL( "insert into GroceryList (product, inCart) values ('lembasBread', 1);");

        theDatabase.execSQL("CREATE TABLE IF NOT EXISTS DummyTable" +
                "( product VARCHAR primary key, inCart int) ;");
    }

    @Test
    public void testRetrieveProductName() {

        EnterDetails activityED = Robolectric.setupActivity(EnterDetails.class);
        activityED.tableName = "GroceryList";
        String answer = activityED.retrieveProductName();

        assertEquals("lembasBread", answer);
    }

    @Test
    public void testRetrieveAndSubmitData_EmptyFields() {

        EnterDetails activityED = Robolectric.setupActivity(EnterDetails.class);
        activityED.tableName = "GroceryList";

        int result = activityED.retrieveAndSubmitData();

        assertEquals( -1, result);
    }

    @Test
    public void testRetrieveAndSubmitData_FullFields() {
        //Also tests UpdateMasterList when empty

        EnterDetails activityED = Robolectric.setupActivity(EnterDetails.class);
        activityED.tableName = "GroceryList";

        //fill the feilds
        TextView store = activityED.findViewById(R.id.enterStore);
        TextView price = activityED.findViewById(R.id.enterPrice);
        TextView brand = activityED.findViewById(R.id.enterBrand);
        TextView size  = activityED.findViewById(R.id.enterSize);

        store.setText("Lothlorien");
        price.setText("4.42");
        brand.setText("WoodElves Bakery");
        size.setText("12");

        //get result from entering fields
        int result = activityED.retrieveAndSubmitData();

        assertEquals( 1, result);
    }

//    @Test
//    public void testUpdateMasterList_NOTEmptyMasterList() {
//        fail("Unimplemented");
//    }

    @Test
    public void testSubmitButton() {
        //test clicking the button
        EnterDetails activityED = Robolectric.setupActivity(EnterDetails.class);
        activityED.tableName = "GroceryList";

        // fill fields
        TextView store = activityED.findViewById(R.id.enterStore);
        TextView price = activityED.findViewById(R.id.enterPrice);
        TextView brand = activityED.findViewById(R.id.enterBrand);
        TextView size  = activityED.findViewById(R.id.enterSize);

        store.setText("Lothlorien");
        price.setText("4.42");
        brand.setText("WoodElves Bakery");
        size.setText("12");

        //click button
        Button submit = activityED.findViewById(R.id.saveData);
        submit.performClick();

        //vars to check database changes
        boolean masterLooksGood  = false;
        boolean productLooksGood = false;

        //for some reason, there is no being entered product in EnterDetails

        // check Master table
        Cursor fromMaster = theDatabase.rawQuery("select * from MasterList where product = 'lembasBread';", null);
//        if ( fromMaster.getCount() == 0 ) {
//            fail("Zofi: masterList had no data when it should have");
//        }
        int freqColumn          = fromMaster.getColumnIndex("frequency");
        int highestPriceColumn   = fromMaster.getColumnIndex("highestPrice");
        int lowestPriceColumn   = fromMaster.getColumnIndex("lowestPrice");
        int totalSpentColumn    = fromMaster.getColumnIndex("totalSpent");
        fromMaster.moveToFirst();

        if (
                (fromMaster.getInt(freqColumn) == 1) &&
                (fromMaster.getFloat(highestPriceColumn) == 4.42) &&
                (fromMaster.getFloat(lowestPriceColumn) == 4.42) &&
                (fromMaster.getFloat(totalSpentColumn) == 4.42)
                ) {
            masterLooksGood = true;
        }

        // check Product (lembasBread) table
        Cursor fromProduct = theDatabase.rawQuery("select * from lembasBread;", null);
//        if ( fromProduct.getCount() == 0 ) {
//            fail("Zofi: lembasBread table had no data when it should have");
//        }
        int brandColumnP        = fromProduct.getColumnIndex("brand");
        int sizeColumnP         = fromProduct.getColumnIndex("size");
        int freqColumnP         = fromProduct.getColumnIndex("frequency");
        int avgColumnP          = fromProduct.getColumnIndex("avgPrice");
        int lowestColumnP       = fromProduct.getColumnIndex("lowestPrice");
        int highestColumnP      = fromProduct.getColumnIndex("highestPrice");
        int storeColumnP        = fromProduct.getColumnIndex("store");
        int totalSpentColumnP   = fromProduct.getColumnIndex("totalSpent");
        fromProduct.moveToFirst();

        if (
                (fromProduct.getString(brandColumnP) == "WoodElves Bakery") &&
                (fromProduct.getInt(sizeColumnP) == 12) &&
                (fromProduct.getInt(freqColumnP) == 1) &&
                (fromProduct.getFloat(avgColumnP) == 4.42) &&
                (fromProduct.getFloat(highestColumnP) == 4.42) &&
                (fromProduct.getFloat(lowestColumnP) == 4.42) &&
                (fromProduct.getString(storeColumnP) == "Lothlorien") &&
                (fromProduct.getFloat(totalSpentColumnP) == 4.42)
                ) {
            productLooksGood = true;
        }

        // return result
        assertTrue(productLooksGood && masterLooksGood);
    }

}

