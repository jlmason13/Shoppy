package com.foodteam.shoppy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.text.SimpleDateFormat;


public class EnterDetails extends AppCompatActivity {

    SQLiteDatabase theDatabase;
    //this assignment is temporary, SQLite complaining null is invalid for the setup of activity in testing
    String tableName = "dummyList";     //this stores the name of the grocery list    //temporarily set to dummyTable
    String product;
    int rowNum;
    int totalRow;
    Button submitBtn;
    DBHandler Handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_details);

        // Handles situations where all or none of necessary extras exists
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Toast.makeText(this, "No extras passed to enterDetails", Toast.LENGTH_LONG).show();
                Intent returnIntent = new Intent( getApplicationContext(), List.class );
                returnIntent.putExtra("nameOfTable", tableName);
                startActivity(returnIntent);

            } else if (extras.containsKey("index") && extras.containsKey("nameOfTable")) {
                if (extras.getInt("index") > 0) {
                    tableName = extras.getString("nameOfTable");
                    rowNum = extras.getInt("index");
                } else {
                    tableName = extras.getString("nameOfTable");
                    rowNum = 0;
                }
            } else if (!extras.containsKey("index") && extras.containsKey("nameOfTable")) {
                rowNum = 0;
                tableName = extras.getString("nameOfTable");
            } else {    // extras exists but no tableName key
                // ??
                Toast.makeText(this, "No products passed to enterDetails", Toast.LENGTH_LONG).show();
                Intent returnIntent = new Intent( getApplicationContext(), Lists.class );
                startActivity(returnIntent);
            }
        }

        //Database stuff
        openTheDatabase();
        product = retrieveProductName();
        TextView productName = (TextView) findViewById(R.id.productName);
        productName.setText(product);

        //assigns colors
        Handler = DBHandler.getInstance(getApplicationContext());
        ColorChanges obj = new ColorChanges();
        View view = this.getWindow().getDecorView();
        if ( true ) {
            obj.setWindowCOlor(theDatabase, Handler, view, getWindow());      //causes error when testing but not when using emulator
        }

        // inform user how many items are left to enter
        Toast.makeText(this,  "" + rowNum + "" + totalRow + " Products Entered", Toast.LENGTH_LONG).show();

        submitBtn = (Button) findViewById(R.id.saveData);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Zofi: submit button has been clicked");
                retrieveAndSubmitData();
                System.out.println("Zofi: retrieveAndSubmitData() finished, product = " + product + ".");
                rowNum++;
                Intent iterativeIntent = new Intent( getApplicationContext(), EnterDetails.class );
                iterativeIntent.putExtra("tablename", tableName);
                iterativeIntent.putExtra("index", rowNum);
                startActivity(iterativeIntent);
            }
        });

    }

    //retrieves the next product from the table to be entered
    protected String retrieveProductName(){
        String aProduct = "";

        Cursor productQuery = theDatabase.rawQuery("select product from " + tableName + " where inCart = 1;", null );
        int productColumn = productQuery.getColumnIndex("product");
        totalRow = productQuery.getCount();
        productQuery.moveToPosition(rowNum);
        if (rowNum >= totalRow ) {
            productQuery.close();
            Toast.makeText(this, "All Done!", Toast.LENGTH_LONG).show();
            Intent returnIntent = new Intent( getApplicationContext(), List.class );
            returnIntent.putExtra("nameOfTable", tableName);
            startActivity(returnIntent);
        } else {
            aProduct = productQuery.getString(productColumn);
            productQuery.close();
        }
        return aProduct;
    }

    //name self-explanatory
    protected SQLiteDatabase openTheDatabase() {
        try {
            theDatabase = this.openOrCreateDatabase("shoppyDB", MODE_PRIVATE, null );
            return theDatabase;
        } catch (Exception e) {
            System.out.println("DATABASE ERROR" + " Zofi: Error opening Database");
            return null;
        }
    }

    // collects information on entered product, checking that there is data in the feilds, and passes values to updateProductDetails function
    protected int retrieveAndSubmitData() {
        TextView priceTxt = (TextView) findViewById(R.id.enterPrice);
        TextView sizeTxt  = (TextView) findViewById(R.id.enterSize);
        TextView brandTxt = (TextView) findViewById(R.id.enterBrand);
        TextView storeTxt = (TextView) findViewById(R.id.enterStore);


        String newPrice   = priceTxt.getText().toString();
        String newSize    =  sizeTxt.getText().toString();
        String newBrand    = brandTxt.getText().toString();
        String newStore    = storeTxt.getText().toString();

        if ( newPrice.equals("") || newSize.equals("") || newBrand.equals("") || newStore.equals("") ){     //basic check that all fields have content
            Toast.makeText(this, "All fields must be entered", Toast.LENGTH_LONG).show();
            return -1;
        } else {
            int result = updateProductDetails( product, Float.parseFloat(newPrice), Float.parseFloat(newSize), newBrand, newStore);
            if (result == 1) {
                updateMasterList();
            }
            return 1;
        }
    }

    //TODO fix, product is showing up as null for some reason
    //TODO check date stamp works

    /* Depending on whether the product exists in MasterList, as well as the combo of brand ad size exists,
       this function updates or inserts the new product data
     */
    protected int updateProductDetails( String product, Float newPrice, Float newSize, String newBrand, String newStore) {
        if (product.equals("")) { return 0; }
        Cursor productQuery = theDatabase.rawQuery("select * from MasterList where product = '" + product + "';", null);

        //code to get the current time
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String newDate = currentDate.format(todayDate);

        System.out.println("Zofi: Entering if, checking if productQuery.getcount() == 1, productQuery.getCount() = " + productQuery.getCount() );   //TODO delete me
        if( productQuery.getCount() > 0 ){

            Cursor checkBrandSize = theDatabase.rawQuery( "select * from " + product +
                " where brand = '" + newBrand + "' and size = '" + newSize + "';", null);
            int freqColumn          = checkBrandSize.getColumnIndex("frequency");
            int highestPriceColumn  = checkBrandSize.getColumnIndex("highestPrice");
            int lowestPriceColumn   = checkBrandSize.getColumnIndex("lowestPrice");
            int totalSpentColumn    = checkBrandSize.getColumnIndex("totalSpent");
            checkBrandSize.moveToFirst();

            System.out.println("Zofi: Entering if, checking if checkBrandSize.getcount() == 1");   //TODO delete me
            if (checkBrandSize.getCount() == 1) {   //must update existing row if row of brand and size already exist
                float lowest = Float.parseFloat( checkBrandSize.getString(lowestPriceColumn) );
                float highest = Float.parseFloat( checkBrandSize.getString(highestPriceColumn) );

                //calcuate new Frequency, total spent, and average
                int newFreq = checkBrandSize.getInt(freqColumn) + 1;
                float newTotal = checkBrandSize.getFloat(totalSpentColumn);
                      newTotal = newTotal + newPrice;
                float newAverage = newTotal / newFreq;

                if ( newPrice < lowest ) { // if newPrice < the current lowest price, update lowest price to newPrice, and store to newStore
                    theDatabase.execSQL("update " + product +
                            " set frequency = " + newFreq +
                               ", avgPrice = " + newAverage +
                               ", lowestPrice = " + newPrice +
                               ", store = " + newStore +
                               ", totalSpent = " + newTotal +
                               ", date = " + newDate +
                            " where brand = '" + newBrand + "' and size = " + newSize + " ;");
                } else if ( newPrice > highest) {                    // newPrice > highest price
                    theDatabase.execSQL("update " + product +
                            " set frequency = " + newFreq +
                               ", avgPrice = " + newAverage +
                               ", highestPrice = " + newPrice +
                               ", totalSpent = " + newTotal +
                               ", date = " + newDate +
                            " where brand = '" + newBrand + "' and size = " + newSize + " ;");
                } else {
                    theDatabase.execSQL("update " + product +
                            " set frequency = " + newFreq +
                               ", avgPrice = " + newAverage +
                               ", totalSpent = " + newTotal +
                               ", date = " + newDate +
                            " where brand = '" + newBrand + "' and size = " + newSize + " ;");
                }

            } else {    //combo of brand and size does not exist so create new row in product
                theDatabase.execSQL("insert into" + product + "(brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent, date)" +
                        "values ( " + newBrand + ", " + newSize + ", 1, " + newPrice + ", " + newPrice + ", " + newPrice + ", " + newStore + ", " + newPrice + ", '" + newDate + "');" );
            }
            checkBrandSize.close();
            return 1;

        } else {
            System.out.println("Zofi: table does not exist yet reached");   //TODO delete me
            //add product to MasterList
            theDatabase.execSQL("insert into MasterList " +
                    "(product, frequency, avgPrice, lowestPrice, totalSpent) " +
                    "values ( '" + product + "', 0, 0.00, 0.00, 0.00 );");

            //create a table with that exact name of product
            theDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + product +
                    "(brand VARCHAR, size integer, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), highestPrice float(9,2), store VARCHAR, totalSpent float(9,2), date Text " +
                    ", primary key(brand, size) );");

            //insert new data from into the new table data
            theDatabase.execSQL( "insert into " + product +
                    "(brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent, date ) " +
                    "values ( '" + newBrand + "', " + newSize + ", 1, " + newPrice + ", " + newPrice + ", " + newPrice + ", '" + newStore + "', " + newPrice + ", '" + newDate + "');" );
            return 1;
        }
    }

    /* collects information from product table and creates the data to update the product's row in MasterList
       since there will be a table for each product, this was coded instead of a trigger
     */
    protected void updateMasterList() {
        if (product.equals("")) {return;}
        // create query to retreive all rows of product table, need columns frequency, lowestPrice, and totalSpent to update MasterList
        Cursor update = theDatabase.rawQuery( "select frequency, lowestPrice, totalSpent from " + product + ";", null);
        int freqColumn          = update.getColumnIndex("frequency");
        int lowestPriceColumn   = update.getColumnIndex("lowestPrice");
        int totalSpentColumn    = update.getColumnIndex("totalSpent");
        update.moveToFirst();

        // create variables to hold the sum or min of frequency, lowestPrice, and totalSpent in product table
        int sumFreq = 0;
        float sumTotalSpent = 0.00f;
        float lowestLow = 0.00f;

        // loop through each row and add or update to variables
        for (int r = 0; r < update.getCount(); r++ ) {
            sumFreq = sumFreq + update.getInt(freqColumn);
            sumTotalSpent = sumTotalSpent + update.getFloat(totalSpentColumn);
            float check = update.getFloat(lowestPriceColumn);
            if ( check < lowestLow ) {
                lowestLow = check;
            }
            update.moveToNext();
        }
        update.close();

        // divide totalSpent by frequency to create MasterList avgPrice
        float avgForMaster = sumTotalSpent / sumFreq;

        // use update query to change product row in MasterList (product, frequency, avgPrice, lowestPrice, totalSpent)
        theDatabase.execSQL("update MasterList" +
                " set frequency = " + sumFreq +
                   ", avgPrice = " + avgForMaster +
                   ", lowestPrice = " + lowestLow +
                   ", totalSpent = " + sumTotalSpent +
                " where product = '" + product + "' ;");
    }
    //TODO code update of masterList more efficiently
}
