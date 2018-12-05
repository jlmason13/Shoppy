package com.foodteam.shoppy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

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
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

//Tell Mockito to validate if usage of framework is right.
//@RunWith(MockitoJUnitRunner.class)
@RunWith(RobolectricTestRunner.class)
public class ProductDetailsTest {
    SQLiteDatabase theDatabase;
    ProductDetails activityPD;
    TableRow.LayoutParams textViewParam;
    TableRow.LayoutParams numTextViewParam;
    TableRow.LayoutParams buttonParam;

    @Before
    public void preparingForTests() {
        MainMenu activityMM = Robolectric.setupActivity(MainMenu.class);
        DBHandler Handler = DBHandler.getInstance(activityMM.getApplicationContext());
        theDatabase = activityMM.shoppyDB;
        Handler.onCreate(theDatabase);

        activityPD = Robolectric.setupActivity(ProductDetails.class);

        textViewParam = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        );
        numTextViewParam = new TableRow.LayoutParams(
                35,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        );
        buttonParam = new TableRow.LayoutParams(
                120,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        );
    }

    @Test
    public void testNewTextView_TextPD() {
        // create string, and boolean
        String name = "bread";
        boolean num = false;

        //call the function
        TextView check = activityPD.createTextViewPD( textViewParam, name, num );

        // check that text view was created and set to proper values
        String viewName = check.getText().toString();
        TableRow.LayoutParams viewParam = (TableRow.LayoutParams) check.getLayoutParams();
        assertTrue( (viewName.equals(name)) && (viewParam.equals(textViewParam)) );
    }

    @Test
    public void testNewTextView_NumPD() {
        // create string, and boolean
        String name = "bread";
        boolean num = true;

        //call the function
        TextView check = activityPD.createTextViewPD( numTextViewParam, name, num );

        // check that text view was created and set to proper values
        String viewName = check.getText().toString();
        TableRow.LayoutParams viewParam = (TableRow.LayoutParams) check.getLayoutParams();
        assertTrue( (viewName.equals(name)) && (viewParam.equals(numTextViewParam)) );
    }

    //WIP TEST
    /*
    @Test
    public void testNewRowPD() {
        //get Table
        View table = activityPD.findViewById(R.id.theTable);

        //create params for textViews and Button
        String name = "bread";

        //create textviews and button
        TextView brand = activityPD.createTextViewPD( textViewParam, name, false );
        TextView size = activityPD.createTextViewPD( textViewParam, name, false );
        TextView frequency = activityPD.createTextViewPD( textViewParam, name, false );
        TextView avgPrice = activityPD.createTextViewPD( textViewParam, name, false );
        TextView lowestPrice = activityPD.createTextViewPD( textViewParam, name, false );
        TextView highestPrice = activityPD.createTextViewPD( textViewParam, name, false);
        TextView store = activityPD.createTextViewPD( textViewParam, name, false );
        TextView totalSpent = activityPD.createTextViewPD( textViewParam, name, false);
        TextView date = activityPD.createTextViewPD( textViewParam, name, false );

        //call function
        TableRow aRow = activityPD.createNewRowPD( brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent, date );

        //check items are in row
        assertTrue( (brand.getParent() == aRow)    &&
                (size.getParent() == aRow)                  &&
                (frequency.getParent() == aRow)             &&
                (avgPrice.getParent() == aRow)              &&
                (lowestPrice.getParent() == aRow)           &&
                (highestPrice.getParent() == aRow)          &&
                (store.getParent() == aRow)                 &&
                (totalSpent.getParent() == aRow)            &&
                (date.getParent() == aRow)                  &&
                (aRow.getParent() == table)         );
    }
*/
    @Test
    public void testAddPDProductData_WithOutFilters() {
        String product = "egg";
        //insert a few rows into product details
        theDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + product +
                "(brand VARCHAR, size integer, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), highestPrice float(9,2), store VARCHAR, totalSpent float(9,2), date Text " +
                ", primary key(brand, size) );");
        theDatabase.execSQL("insert into " + product +
                " (brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent, date) " +
                "values ( 'something', 4, 2.32, 1.00, 9.28, 2.22, 'store', 4.50, 'date');");
        theDatabase.execSQL("insert into " + product +
                " (brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent, date) " +
                "values ( 'stuff', 2, 0.32, 1.50, 3.28, 6.22, 'totter', 8.88, 'feb');");

        // call function
        activityPD.getDetails(product);
    }

    @Test
    public void testAddPDProductData_WithFilters() {
        String product = "egg";
        //insert a few rows into product details
        theDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + product +
                "(brand VARCHAR, size integer, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), highestPrice float(9,2), store VARCHAR, totalSpent float(9,2), date Text " +
                ", primary key(brand, size) );");
        theDatabase.execSQL("insert into " + product +
                " (brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent, date) " +
                "values ( 'something', 4, 2.32, 1.00, 9.28, 2.22, 'store', 4.50, 'date');");
        theDatabase.execSQL("insert into " + product +
                " (brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent, date) " +
                "values ( 'stuff', 2, 0.32, 1.50, 3.28, 6.22, 'totter', 8.88, 'feb');");

        //setFilters
        activityPD.filters = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 0};

        // call function
        activityPD.getDetails(product);
    }
}