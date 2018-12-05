package com.foodteam.shoppy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetails extends AppCompatActivity {
    /*When ProductDetails is used, it should already have been linked
    to a specific product. This product should be the name of the table
    so getDetails() can pull directly from that table.*/
    SQLiteDatabase shoppyDB;
    TableLayout Table;
    String product = "product";
    int[] filters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        openTheDatabase();
        Table = findViewById(R.id.TheTable);
//RETAIN COLOR CHANGES BETWEEN PAGES:
        DBHandler dbHelper = DBHandler.getInstance(getApplicationContext()); //Only one instance can be active at a time. Protect race conditions
        ColorChanges obj = new ColorChanges();
        View view = this.getWindow().getDecorView();
        obj.setWindowCOlor(shoppyDB, dbHelper, view, getWindow());

        //Get product name from List activity
        if (savedInstanceState == null){
            Bundle extra = getIntent().getExtras();
            if (extra == null){
                product = "";
                filters = null;
            }else{
                product = extra.getString("THEPRODUCTNAME");
                filters = extra.getIntArray("filterslist");
            }
        }else {
            product = (String) savedInstanceState.getSerializable("THEPRODUCTNAME");
        }

        //Set title of page to the name of the product
        ListName convertSpace = new ListName();
        TextView PN = findViewById(R.id.ProductTitle);
        PN.setText(convertSpace.toListName(product));
        //PN.setText(product);

        //Once product name is acquired, get details on it to display
        getDetails(product);

        //Product Details Button: Return to ???
        Button returnToList = findViewById(R.id.buttReturnToList);
        returnToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent returnIntent = new Intent( getApplicationContext(), List.class );
                //startActivity(returnIntent);
                finish();
            }
        });

        //Product Details Button: Open Filtering Page
        Button filter = findViewById(R.id.buttFilters);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent( getApplicationContext(), Filters.class );
                ListName convertSpace = new ListName(); //replace spaces with underscores
                returnIntent.putExtra("THEPRODUCTNAME", convertSpace.toTableName(product));
                startActivity(returnIntent);
            }
        });
    }

    public int getDetails(String product){
        try{
            if (product == null){
                //Did not successfully receive product name from List.java
                Toast.makeText(this, "Product Cannot Be Found", Toast.LENGTH_LONG).show();
            }

            //The layout parameters for contents of a row
            TableRow.LayoutParams textViewParam = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            TableRow.LayoutParams numTextViewParam = new TableRow.LayoutParams(
                    35,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f
            );

            //Create TextViews of columns from XML
            TextView colBrand = findViewById(R.id.colBrand);
            TextView colSize = findViewById(R.id.colSize);
            TextView colFreq = findViewById(R.id.colFreq);
            TextView colAvgP = findViewById(R.id.colAvgP);
            TextView colLowP = findViewById(R.id.colLowP);
            TextView colHighP = findViewById(R.id.colHighP);
            TextView colStore = findViewById(R.id.colStore);
            TextView colTotal = findViewById(R.id.colTotal);
            TextView colDate = findViewById(R.id.colDate);

            //Since the filter can change the order of the columns, change based on filters array
            String[] attrNames = {    "brand",  "size",  "freq-\nuency", "avg\nPrice", "lowest\nPrice", "highest\nPrice", "store",  "total\nSpent", "date"};
            TextView[] attrLocales = {colBrand, colSize,  colFreq,     colAvgP,      colLowP,      colHighP,     colStore,  colTotal, colDate};
            if (filters != null) {
                for (int i = 0, j = 0; i < filters.length - 1; i++) {
                    if (filters[i] == 1) {
                        //System.out.println( "filters index " + i + " equals " + filters[i]);
                        attrLocales[j].setText(attrNames[i]);
                        j++;
                    }
                }
            } else {
                for (int i = 0; i < 9; i++) {
                    attrLocales[i].setText(attrNames[i]);
                }
            }

            //Adjusts query based on last element of filters array
            String query = "SELECT * FROM " + product + " ORDER BY ";
            if (filters != null) {
                switch (filters[9]) {
                    case 0:
                        query = query + "date ASC;"; //Most Recent Purchase: Sort By Time
                        break;
                    case 1:
                        query = query + "date DESC;"; //Oldest Purchase: Sort by Time
                        break;
                    case 2:
                        query = query + "store ASC"; //Store Alphabetical: Sort By Store
                        break;
                    case 3:
                        query = query + "brand ASC;"; //Brand Alphabetical: Sort By Brand
                        break;
                    case 4:
                        query = query + "avgPrice ASC;"; //Price Average: Lowest to Highest
                        break;
                    case 5:
                        query = query + "lowestPrice ASC;"; //Price Lowest: Lowest to Highest
                        break;
                    case 6:
                        query = query + "highestPrice ASC;"; //Price Highest: Lowest to Highest
                        break;
                    //case 7:
                        //query = query + "totalSpent ASC;";
                        //break;
                    default:
                        query = query + "brand ASC;";
                }
            } else {
                query = query + "brand;";
            }

            Cursor iterate = shoppyDB.rawQuery( query, null );

            //Get the columns from the product table
            int brand = iterate.getColumnIndex("brand");
            int size = iterate.getColumnIndex("size");
            int freq = iterate.getColumnIndex("frequency");
            int avgP = iterate.getColumnIndex("avgPrice");
            int lowP = iterate.getColumnIndex("lowestPrice");
            int highP = iterate.getColumnIndex("highestPrice");
            int store = iterate.getColumnIndex("store");
            int total = iterate.getColumnIndex("totalSpent");
            int date = iterate.getColumnIndex("date");

            //create rows based on data from cursor and the filters array, only includes columns that were selected via the filters page
            iterate.moveToFirst();
            if (iterate != null && (iterate.getCount() > 0)) {
                for( int r = 0; r < iterate.getCount(); r++ ) {
                    TableRow row = new TableRow(this);
                    if ( filters != null ) {
                        if (filters[0] == 1) { //If "brand" is visible
                            TextView curBrand = createTextViewPD( textViewParam, iterate.getString(brand),false );
                            row.addView(curBrand);
                        }
                        if (filters[1] == 1) { //If "size" is visible
                            TextView curSize = createTextViewPD( numTextViewParam, iterate.getString(size),true );
                            row.addView(curSize);
                        }
                        if (filters[2] == 1) { //If "frequency" is visible
                            TextView curFreq = createTextViewPD( numTextViewParam, iterate.getString(freq),true );
                            row.addView(curFreq);
                        }
                        if (filters[3] == 1) { //If "avg price" is visible
                            TextView curAvg = createTextViewPD( numTextViewParam, iterate.getString(avgP),true );
                            row.addView(curAvg);
                        }
                        if (filters[4] == 1) { //If "lowest price" is visible
                            TextView curLow = createTextViewPD( numTextViewParam, iterate.getString(lowP),true );
                            row.addView(curLow);
                        }
                        if (filters[5] == 1) { //If "highest price" is visible
                            TextView curHigh = createTextViewPD( numTextViewParam, iterate.getString(highP),true );
                            row.addView(curHigh);
                        }
                        if (filters[6] == 1) { //If "store" is visible
                            TextView curStore = createTextViewPD( textViewParam, iterate.getString(store),false );
                            row.addView(curStore);
                        }
                        if (filters[7] == 1) { //If "total" is visible
                            TextView curTotal = createTextViewPD( numTextViewParam, iterate.getString(total),true );
                            row.addView(curTotal);
                        }
                        if (filters[8] == 1) { //If "date" is visible
                            TextView curTotal = createTextViewPD( textViewParam, iterate.getString(date), false );
                            row.addView(curTotal);
                        }
                        Table.addView(row);
                    } else {
                        TextView curBrand = createTextViewPD( textViewParam, iterate.getString(brand),false );
                        TextView curSize = createTextViewPD( numTextViewParam, iterate.getString(size),true );
                        TextView curFreq = createTextViewPD( numTextViewParam, iterate.getString(freq),true );
                        TextView curAvg = createTextViewPD( numTextViewParam, iterate.getString(avgP),true );
                        TextView curLow = createTextViewPD( numTextViewParam, iterate.getString(lowP),true );
                        TextView curHigh = createTextViewPD( numTextViewParam, iterate.getString(highP),true );
                        TextView curStore = createTextViewPD( textViewParam, iterate.getString(store),false );
                        TextView curTotal = createTextViewPD( numTextViewParam, iterate.getString(total),true );
                        TextView curDate = createTextViewPD( textViewParam, iterate.getString(date),false );
                        createNewRowPD( curBrand, curSize, curFreq, curAvg, curLow, curHigh, curStore, curTotal, curDate);
                    }
                    iterate.moveToNext();
                }
            }
            iterate.close();

        }catch(Exception e){
            Log.e("PRODDETAIL ERROR", "Problem getting product details for " + product + ". Error: " + e);
        }
        return 1;
    }

    //Create a new row
    public TableRow createNewRowPD(TextView brand, TextView size, TextView freq, TextView avgP, TextView lowP, TextView highP, TextView store, TextView total, TextView date) {
        TableRow row = new TableRow(this);

        row.addView(brand);
        row.addView(size);
        row.addView(freq);
        row.addView(avgP);
        row.addView(lowP);
        row.addView(highP);
        row.addView(store);
        row.addView(total);
        row.addView(date);

        Table.addView(row);
        return row;
    }

    //Create Text View
    public TextView createTextViewPD(TableRow.LayoutParams aParam, String toDisplay, boolean isNum ){
        TextView text = new TextView( this );
        text.setText( toDisplay );
        text.setTextSize(16);
        if ( !isNum ) {
            text.setEms(5);     //wraps the text
        } else {
            text.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }
        text.setLayoutParams(aParam);
        text.setTextColor(ContextCompat.getColor(this, R.color.black));
        return text;
    }

    protected SQLiteDatabase openTheDatabase() {
        try {
            shoppyDB = this.openOrCreateDatabase("shoppyDB", MODE_PRIVATE, null );
            return shoppyDB;
        } catch (Exception e) {
            System.out.println("DATABASE ERROR" + "Product Details: Error opening Database");
            return null;
        }
    }

}