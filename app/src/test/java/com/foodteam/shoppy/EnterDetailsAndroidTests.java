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


        import java.text.SimpleDateFormat;
        import java.util.Date;

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
    EnterDetails activityED;


    @Before
    public void prepareForTesting() {
        //These four lines is how you setup the Database for Robolectic, kindof awkward but works
        MainMenu activityMM = Robolectric.setupActivity(MainMenu.class);
        DBHandler Handler = DBHandler.getInstance(activityMM.getApplicationContext());
        theDatabase = activityMM.shoppyDB;
        Handler.onCreate(theDatabase);

        // prep a table for testing
        theDatabase.execSQL("CREATE TABLE IF NOT EXISTS GroceryList" +
                "( product VARCHAR primary key, inCart int) ;");
        theDatabase.execSQL( "insert into GroceryList (product, inCart) values ('lembasBread', 1);");

        //setup the activity I want to test
        activityED = Robolectric.setupActivity(EnterDetails.class);
        activityED.tableName = "GroceryList";
    }

    @Test
    public void testRetrieveProductName() {
        String answer = activityED.retrieveProductName();

        TextView text = activityED.findViewById(R.id.productName);
    }

    @Test
    public void testRetrieveAndSubmitData_EmptyFields() {
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

    @Test
    public void testUpdatemasterList() {
        EnterDetails activityED = Robolectric.setupActivity(EnterDetails.class);
        activityED.tableName = "GroceryList";
        activityED.product = "lembasBread";
        activityED.retrieveProductName();

        //code to get the current time
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String newDate = currentDate.format(todayDate);

        // setup a product table "lembasBread" with one row of data to test with
        theDatabase.execSQL("CREATE TABLE IF NOT EXISTS lembasBread" +
                "(brand VARCHAR, size integer, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), highestPrice float(9,2), store VARCHAR, totalSpent float(9,2), date Text " +
                ", primary key(brand, size) );");
        theDatabase.execSQL( "insert into lembasBread" +
                "(brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent, date ) " +
                "values ( 'WoodEleves Bakery', 12, 1, 4.42, 4.42, 4.42, 'Lothlorien', 4.42, '" + newDate + "');" );

        // test method
        activityED.updateMasterList();
    }

    @Test
    public void testSubmitButton() {
        //test clicking the button
        EnterDetails activityED = Robolectric.setupActivity(EnterDetails.class);
        activityED.tableName = "GroceryList";
        activityED.retrieveProductName();

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

        // check Product (lembasBread) table
        Cursor fromProduct = theDatabase.rawQuery("select * from lembasBread;", null);

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
                (fromProduct.getString(brandColumnP).equals("WoodElves_Bakery"))  &&
                        (fromProduct.getInt   (sizeColumnP) == 12)                &&
                        (fromProduct.getInt   (freqColumnP) == 1)                 &&
                        (fromProduct.getString(avgColumnP).equals("4.42"))        &&
                        (fromProduct.getString(highestColumnP).equals("4.42"))    &&
                        (fromProduct.getString(lowestColumnP).equals("4.42"))     &&
                        (fromProduct.getString(storeColumnP).equals("Lothlorien"))&&
                        (fromProduct.getString(totalSpentColumnP).equals("4.42") )
                ) {
            productLooksGood = true;
        }


        // check Master table
        Cursor fromMaster = theDatabase.rawQuery("select * from MasterList where product = 'lembasBread';", null);

        int freqColumn          = fromMaster.getColumnIndex("frequency");
        int highestPriceColumn   = fromMaster.getColumnIndex("highestPrice");
        int lowestPriceColumn   = fromMaster.getColumnIndex("lowestPrice");
        int totalSpentColumn    = fromMaster.getColumnIndex("totalSpent");
        fromMaster.moveToFirst();

        //(product, frequency, avgPrice, lowestPrice, totalSpent)
        if (
                (fromMaster.getInt(freqColumn) == 1) &&
                (fromMaster.getString(lowestPriceColumn).equals("4.42") ) &&
                (fromMaster.getString(totalSpentColumn).equals("4.42") )
                ) {
            masterLooksGood = true;
        }

        // return result
        assertTrue(productLooksGood && masterLooksGood);
    }
}

