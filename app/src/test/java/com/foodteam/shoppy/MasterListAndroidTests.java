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
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
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

@RunWith(RobolectricTestRunner.class)
//@Config(constants = BuildConfig.class)
public class MasterListAndroidTests {
    SQLiteDatabase theDatabase;
    MasterList activityML;
    TableRow.LayoutParams textViewParam;
    TableRow.LayoutParams numTextViewParam;
    TableRow.LayoutParams buttonParam;

    @Before
    public void prepareForTesting() {
        MainMenu activityMM = Robolectric.setupActivity(MainMenu.class);
        DBHandler Handler = DBHandler.getInstance(activityMM.getApplicationContext());
        theDatabase = activityMM.shoppyDB;
        Handler.onCreate(theDatabase);

        activityML = Robolectric.setupActivity(MasterList.class);

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
    public void testReturnButton() {
        Button returnBtn = activityML.findViewById(R.id.returnToMainMenu);
        returnBtn.performClick();
    }

    @Test
    public void testFiltersButton() {
        Button returnBtn = activityML.findViewById(R.id.gotoFilters);
        returnBtn.performClick();
    }

    @Test
    public void testNewButton() {
        // initialize parameters
        String name = "bread";

        //call the function
        Button check = activityML.createNewButton( buttonParam, name);

        //check button was created and set to proper values
        String viewName = check.getText().toString();
        TableRow.LayoutParams viewParam = (TableRow.LayoutParams) check.getLayoutParams();
        System.out.println( viewParam + " " + buttonParam );
        assertTrue( viewParam == buttonParam );
    }

    @Test
    public void testNewTextView_Text() {
        // create string, and boolean
        String name = "bread";

        //call the function
        TextView check = activityML.createTextView( textViewParam, name, 0 );

        // check that text view was created and set to proper values
        String viewName = check.getText().toString();
        TableRow.LayoutParams viewParam = (TableRow.LayoutParams) check.getLayoutParams();
        assertTrue( (viewName == name) && (viewParam == textViewParam) );
    }

    @Test
    public void testNewTextView_Num() {
        // create string, and boolean
        String name = "2.33";
        boolean num = true;

        //call the function
        TextView check = activityML.createTextView( numTextViewParam, name, 2 );

        // check that text view was created and set to proper values
        String viewName = check.getText().toString();
        TableRow.LayoutParams viewParam = (TableRow.LayoutParams) check.getLayoutParams();
        assertTrue( (viewName == name) && (viewParam == numTextViewParam) );
    }

    @Test
    public void testNewRow() {
        //get Table
        View table = activityML.findViewById(R.id.theTable);

        //create params for textViews and Button
        String name = "bread";
        boolean num = false;

        //create textviews and button
        TextView curProduct = activityML.createTextView( textViewParam, name, 0 );
        TextView curFreq = activityML.createTextView( textViewParam, name, 1 );
        TextView curAvg = activityML.createTextView( textViewParam, name, 2 );
        TextView curLow = activityML.createTextView( textViewParam, name, 2 );
        TextView curTotalSpent = activityML.createTextView( textViewParam, name, 2 );
        Button detailsButton = activityML.createNewButton( buttonParam, name);

        //call function
        TableRow aRow = activityML.createNewRow( curProduct, curFreq, curAvg, curLow, curTotalSpent, detailsButton );

        //check items are in row
        assertTrue( (curProduct.getParent() == aRow)    &&
                            (curFreq.getParent() == aRow)       &&
                            (curAvg.getParent() == aRow)        &&
                            (curLow.getParent() == aRow)        &&
                            (curTotalSpent.getParent() == aRow) &&
                            (detailsButton.getParent() == aRow) &&
                            (aRow.getParent() == table)         );
    }

    @Test
    public void testAddMasterListProductData_WithOutFilters() {
        //insert a few rows into masterList
        theDatabase.execSQL("insert into MasterList " +
                "(product, frequency, avgPrice, lowestPrice, totalSpent) " +
                "values ( 'nothing', 0, 0.00, 0.00, 0.00 );");
        theDatabase.execSQL("insert into MasterList " +
                "(product, frequency, avgPrice, lowestPrice, totalSpent) " +
                "values ( 'something', 4, 2.32, 1.00, 9.28 );");
        theDatabase.execSQL("insert into MasterList " +
                "(product, frequency, avgPrice, lowestPrice, totalSpent) " +
                "values ( 'other', 7, 1.06, 1.06, 7.42 );");

        // call function
        activityML.addMasterListProductData();
    }

    @Test
    public void testAddMasterListProductData_WithFilters() {
        //insert a few rows into masterList
        theDatabase.execSQL("insert into MasterList " +
                "(product, frequency, avgPrice, lowestPrice, totalSpent) " +
                "values ( 'nothing', 0, 0.00, 0.00, 0.00 );");
        theDatabase.execSQL("insert into MasterList " +
                "(product, frequency, avgPrice, lowestPrice, totalSpent) " +
                "values ( 'something', 4, 2.32, 1.00, 9.28 );");
        theDatabase.execSQL("insert into MasterList " +
                "(product, frequency, avgPrice, lowestPrice, totalSpent) " +
                "values ( 'other', 7, 1.06, 1.06, 7.42 );");

        //setFilters
        activityML.filters = new int[] {1, 1, 1, 1, 1, 0};

        // call function
        activityML.addMasterListProductData();
    }
}