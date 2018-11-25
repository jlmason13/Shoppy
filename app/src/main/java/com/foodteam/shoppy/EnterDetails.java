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

public class EnterDetails extends AppCompatActivity {

    SQLiteDatabase theDatabase;
    String tableName;       //the name of the grocery list
    String product;
    int rowNum;
    int totalRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_details);

        // Handles situations where all or none of necessary extras exists
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                tableName = null;
                Toast.makeText(this, "No extras passed to enterDetails", Toast.LENGTH_LONG).show();
                Intent returnIntent = new Intent( getApplicationContext(), List.class );   //TODO what key name does she store the list name?
                startActivity(returnIntent);

            } else if (extras.containsKey("index") && extras.containsKey("tableName")) {
                if (extras.getInt("index") > 0) {
                    tableName = extras.getString("tableName");    //TODO check to make sure key name matches Kirstin's
                    rowNum = extras.getInt("index");
                } else {
                    tableName = extras.getString("tableName");    //TODO check to make sure key name matches Kirstin's
                    rowNum = 0;
                }
            } else if (!extras.containsKey("index") && extras.containsKey("tableName")) {
                rowNum = 0;
                tableName = extras.getString("tableName");
            } else {    // extras exists but no tableName key
                // ??
                Toast.makeText(this, "No products passed to enterDetails", Toast.LENGTH_LONG).show();
                Intent returnIntent = new Intent( getApplicationContext(), List.class );   //TODO what key name does she store the list name?
                startActivity(returnIntent);
            }
        }

        //Database stuff
        openTheDatabase();
        product = retrieveProductName();
        TextView productName = (TextView) findViewById(R.id.productName);
        productName.setText(product);

        // inform user how many items are left to enter
        Toast.makeText(this,  "" + rowNum + "" + totalRow + " Products Entered", Toast.LENGTH_LONG).show();

        Button submitBtn = (Button) findViewById(R.id.saveData);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retreiveAndSubmitData();
                updateMasterList();
                rowNum++;
                Intent iterativeIntent = new Intent( getApplicationContext(), EnterDetails.class );
                iterativeIntent.putExtra("tableName", tableName);   //TODO make sure it matches Kirstin's
                iterativeIntent.putExtra("index", rowNum);
                startActivity(iterativeIntent);
            }
        });

    }

    //retrieves the next product from the table to be entered
    protected String retrieveProductName(){
        Cursor productQuery = theDatabase.rawQuery("select product from " + tableName + " where inCart = 1;", null ); //TODO check that "inCart" is the right attribute name
        int productColumn = productQuery.getColumnIndex("product");
        totalRow = productQuery.getCount();
        productQuery.moveToPosition(rowNum);
        if (rowNum >= totalRow ) {
            productQuery.close();
            Toast.makeText(this, "All Done!", Toast.LENGTH_LONG).show();
            Intent returnIntent = new Intent( getApplicationContext(), List.class );   //TODO what key name does she store the list name?
            startActivity(returnIntent);
            return "Done";
        } else {
            String aProduct = productQuery.getString(productColumn);
            productQuery.close();
            return aProduct;
        }
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
    protected void retreiveAndSubmitData() {
        TextView priceTxt = (TextView) findViewById(R.id.enterPrice);
        TextView sizeTxt  = (TextView) findViewById(R.id.enterSize);
        TextView brandTxt = (TextView) findViewById(R.id.enterBrand);
        TextView storeTxt = (TextView) findViewById(R.id.enterStore);

        Float newPrice   = Float.parseFloat( (String) priceTxt.getText() );
        Float newSize    = Float.parseFloat( (String) sizeTxt.getText() );
        String newBrand    = (String) brandTxt.getText();
        String newStore    = (String) storeTxt.getText();

        System.out.println("ED here1");
        if ( (newPrice != null) && (newSize != null) && (newBrand != null) && (newStore != null) ){     //basic check that all fields have content
            updateProductDetails( product, newPrice, newSize, newBrand, newStore);
        } else {
            Toast.makeText(this, "All fields must be entered", Toast.LENGTH_LONG).show();
        }
    }

    /* Depending on whether the product exists in MasterList, as well as the combo of brand ad size exists,
       this function updates or inserts the new product data
     */
    protected void updateProductDetails( String product, Float newPrice, Float newSize, String newBrand, String newStore) {
        System.out.println("Zofi: ED here2");
        Cursor productQuery = theDatabase.rawQuery("select from MasterList where product = '" + product + "';", null);
        System.out.println("Zofi: ED here3");
        if( productQuery.getCount() > 0 ){

            Cursor checkBrandSize = theDatabase.rawQuery( "select from" + product +
                " where brand = '" + newBrand + "', size = '" + newSize + "';", null);
            int freqColumn          = checkBrandSize.getColumnIndex("frequency");
            int highestPriceColumn   = checkBrandSize.getColumnIndex("highestPrice");
            int lowestPriceColumn   = checkBrandSize.getColumnIndex("lowestPrice");
            int totalSpentColumn    = checkBrandSize.getColumnIndex("totalSpent");
            checkBrandSize.moveToFirst();

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
                            " where brand = '" + newBrand + "' and size = " + newSize + " ;");
                } else if ( newPrice > highest) {                    // newPrice > highest price
                    theDatabase.execSQL("update " + product +
                            " set frequency = " + newFreq +
                               ", avgPrice = " + newAverage +
                               ", highestPrice = " + newPrice +
                               ", totalSpent = " + newTotal +
                            " where brand = '" + newBrand + "' and size = " + newSize + " ;");
                } else {
                    theDatabase.execSQL("update " + product +
                            " set frequency = " + newFreq +
                               ", avgPrice = " + newAverage +
                               ", totalSpent = " + newTotal +
                            " where brand = '" + newBrand + "' and size = " + newSize + " ;");
                }

            } else {    //combo of brand and size does not exist so create new row in product
                theDatabase.execSQL("insert into" + product + "(brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent)" +
                        "values ( " + newBrand + ", " + newSize + ", 1, " + newPrice + ", " + newPrice + ", " + newPrice + ", " + newStore + ", " + newPrice + ");" );
            }
            checkBrandSize.close();

        } else {
            //add product to MasterList
            theDatabase.execSQL("insert into MasterList " +
                    "(product, frequency, avgPrice, lowestPrice, totalSpent) " +
                    "values ( '" + product + "', 0, 0.00, 0.00, 0.00 );");

            //create a table with that exact name of product
            theDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + product +
                    "(brand VARCHAR, size integer, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), highestPrice float(9,2), store VARCHAR, totalSpent float(9,2) ) " +
                    "primary key(brand, size) ;");

            //insert new data from into the new table data
            theDatabase.execSQL( "insert into " + product +
                    "(brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent ) " +
                    "values ( '" + newBrand + "', " + newSize + ", 1, " + newPrice + ", " + newPrice + ", " + newPrice + ", '" + newStore + "', " + newPrice + ");" );
        }
    }

    /* collects information from product table and creates the data to update the product's row in MasterList
       since there will be a table for each product, this was coded instead of a trigger
     */
    protected void updateMasterList() {
        // create query to retreive all rows of product table, need columns frequency, lowestPrice, and totalSpent to update MasterList
        Cursor update = theDatabase.rawQuery( "select frequency, lowestPrice, totalSpent from " + product + ";", null);
        int freqColumn          = update.getColumnIndex("frequency");
        int lowestPriceColumn   = update.getColumnIndex("lowestPrice");
        int totalSpentColumn    = update.getColumnIndex("totalSpent");

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
