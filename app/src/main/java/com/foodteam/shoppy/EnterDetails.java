package com.foodteam.shoppy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EnterDetails extends AppCompatActivity {

    SQLiteDatabase theDatabase;
    String product = "test";     //this assignment is temporary for testing purposes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_details);

        Button submitBtn = (Button) findViewById(R.id.saveData);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retreiveData();
            }
        });

    }

    protected SQLiteDatabase openTheDatabase() {
        try {
            theDatabase = this.openOrCreateDatabase("shoppyDB", MODE_PRIVATE, null );
            return theDatabase;
        } catch (Exception e) {
            System.out.println("DATABASE ERROR" + " Zofi: Error opening Database");
            return null;
        }
    }

    protected void retreiveData() {
        //TODO how to get product name from last page
        TextView priceTxt = (TextView) findViewById(R.id.enterPrice);
        TextView sizeTxt  = (TextView) findViewById(R.id.enterSize);
        TextView brandTxt = (TextView) findViewById(R.id.enterBrand);
        TextView storeTxt = (TextView) findViewById(R.id.enterStore);

        Float newPrice   = Float.parseFloat( (String) priceTxt.getText() );
        Float newSize    = Float.parseFloat( (String) sizeTxt.getText() );
        String newBrand    = (String) brandTxt.getText();
        String newStore    = (String) storeTxt.getText();

        System.out.println("ED here1");
        updateProductDetails( product, newPrice, newSize, newBrand, newStore);
    }

    protected void updateProductDetails( String product, Float newPrice, Float newSize, String newBrand, String newStore) {
        System.out.println("ED here2");
        Cursor productQuery = theDatabase.rawQuery("select from MasterList where product = '" + product + "';", null);
        System.out.println("ED here3");
        if( productQuery.getCount() == 1 ){
            //TODO need to update details of that product Table, ?check for brand and size match

            Cursor checkBrandSize = theDatabase.rawQuery( "select from" + product +
                " where brand = '" + newBrand + "', size = '" + newSize + "';", null);
            checkBrandSize.moveToFirst();

            if (checkBrandSize.getCount() == 1) {   //must update existing row
                int lowestPriceColumn   = checkBrandSize.getColumnIndex("lowestPrice");
                float lowest = Float.parseFloat( checkBrandSize.getString(lowestPriceColumn) );
                if ( lowest > newPrice ) { // if newPrice < the current lowest price, update lowest price to newPrice, and store to newStore
                    //TODO cont
                }

            } else {    //combo of brand and size does not exist so create new row in product
                theDatabase.execSQL("insert into" + product + "(brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent)" +
                        "values ( " + newBrand + ", " + newSize + ", 1, " + newPrice + ", " + newPrice + ", " + newPrice + ", " + newStore + ", " + newPrice + ");" );
            }

        } else {
            //add product to MasterList
            theDatabase.execSQL("insert into MasterList " +
                    "(product, frequency, avgPrice, lowestPrice, totalSpent) " +
                    "values ( " + product + ", 0, 0.00, 0.00, 0.00 );");            //depending on trigger to update, TODO create trigger to update

            //create a table with that exact name of product
            theDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + product +
                    "(brand VARCHAR, size integer, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), highestPrice float(9,2), store VARCHAR, totalSpent float(9,2) ) " +
                    "primary key(brand, size) ;");

            //insert new data from into the new table data
            theDatabase.execSQL( "insert into " + product +
                    "(brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent ) " +
                    "values ( " + newBrand + ", " + newSize + ", 1, " + newPrice + ", " + newPrice + ", " + newPrice + ", " + newStore + ", " + newPrice + ");" );
        }
    }



}
