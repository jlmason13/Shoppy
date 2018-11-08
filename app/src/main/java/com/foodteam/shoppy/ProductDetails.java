package com.foodteam.shoppy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ProductDetails extends AppCompatActivity {
    /*When ProductDetails is used, it should already have been linked
    to a specific product. This product should be the name of the table
    so getDetails() can pull from that table.*/
    SQLiteDatabase shoppyDB = null; //The database
    //CODE TO PUT INTO LIST.JAVA:
    //Intent intent = new Intent(List.this, ProductDetails.class); //Link activity pages
    //String product = null; //the product name variable(also the name of the table associated to it)
    //Do stuff to product
    //intent.putExtra("THEPRODUCTNAME", product); //Actual string itself is sent to ProductDetails through key "THEPRODUCTNAME"


    public void getDetails(String product){
        try{
            //Create/open shoppy.db and make it exclusive to the app
            shoppyDB = this.openOrCreateDatabase("shoppyDB", MODE_PRIVATE, null);
            shoppyDB.execSQL("SELECT * FROM " + product + ");");
        }catch(Exception e){
            Log.e("PRODDETAIL ERROR", "Problem getting product details for " + product);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        //Get product name from List activity
        String product;
        if (savedInstanceState == null){
            Bundle extra = getIntent().getExtras();
            if (extra == null){
                product = null;
            }else{
                product = extra.getString("THEPRODUCTNAME");
            }
        }else {
            product = (String) savedInstanceState.getSerializable("THEPRODUCTNAME");
        }
        getDetails(product);
    }

}