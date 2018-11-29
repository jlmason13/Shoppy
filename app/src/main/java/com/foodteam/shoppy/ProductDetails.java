package com.foodteam.shoppy;

import android.content.ContentValues;
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

import org.w3c.dom.Text;

public class ProductDetails extends AppCompatActivity {
    /*When ProductDetails is used, it should already have been linked
    to a specific product. This product should be the name of the table
    so getDetails() can pull directly from that table.*/
    SQLiteDatabase shoppyDB; //The database
    TableLayout Table;
    int[] filters;
    //CODE TO PUT INTO LIST.JAVA:
    //Intent intent = new Intent(List.this, ProductDetails.class); //Link activity pages
    //String product = null; //the product name variable(also the name of the table associated to it)
    //Do stuff to product
    //intent.putExtra("THEPRODUCTNAME", product); //Actual string itself is sent to ProductDetails through key "THEPRODUCTNAME"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        openTheDatabase();
        Table = findViewById(R.id.TheTable);

        //Get product name from List activity
        String product;
        if (savedInstanceState == null){
            Bundle extra = getIntent().getExtras();
            if (extra == null){
                product = null;
                filters = null;
            }else{
                product = extra.getString("THEPRODUCTNAME");
                filters = extra.getIntArray("filterslist");
            }
        }else {
            product = (String) savedInstanceState.getSerializable("THEPRODUCTNAME");
        }
        product = "eggs"; //TESTING STRING

        //Set title of page to the name of the product
        TextView PN = findViewById(R.id.ProductTitle);
        PN.setText(product);

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
                startActivity(returnIntent);
            }
        });
    }

    public void getDetails(String product){
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

            //Since the filter can change the order of the columns, change based on filters array
            String[] attrNames = {    "brand",  "size",  "frequency", "avgPrice", "lowestPrice", "highestPrice", "store",  "totalSpent"};
            TextView[] attrLocales = {colBrand, colSize,  colFreq,     colAvgP,      colLowP,      colHighP,     colStore,  colTotal};
            if (filters != null) {
                for (int i = 0, j = 0; i < filters.length - 1; i++) {
                    if (filters[i] == 1) {
                        System.out.println( "filters index " + i + " equals " + filters[i]);
                        attrLocales[j].setText(attrNames[i]);
                        j++;
                    }
                }
            } else {
                for (int i = 0; i < 8; i++) {
                    attrLocales[i].setText(attrNames[i]);
                }
            }

            //Adjusts query based on last element of filters array
            String query = "SELECT * FROM " + product + " ORDER BY ";
            if (filters != null) {
                switch (filters[6]) {
                    case 0:
                        query = query + "brand ASC;";
                        break;
                    case 1:
                        query = query + "size ASC;";
                        break;
                    case 2:
                        query = query + "frequency ASC";
                        break;
                    case 3:
                        query = query + "avgPrice DESC;";
                        break;
                    case 4:
                        query = query + "lowestPrice ASC;";
                        break;
                    case 5:
                        query = query + "highestPrice ASC;";
                        break;
                    case 6:
                        query = query + "store DESC;";
                        break;
                    case 7:
                        query = query + "totalSpent ASC;";
                        break;
                    default:
                        query = query + "brand ASC;";
                }
            } else {
                query = query + "brand;";
            }
            //The query to use
            //String query = "SELECT * FROM " + product + ");"; //QUERY WAITING ON PRODUCT TABLE

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

            //create rows based on data from cursor and the filters array, only includes columns that were selected via the filters page
            iterate.moveToFirst();
            if (iterate != null && (iterate.getCount() > 0)) {
                for( int r = 0; r < iterate.getCount(); r++ ) {
                    TableRow row = new TableRow(this);
                    if ( filters != null ) {
                        if (filters[0] == 1) {
                            TextView curBrand = createTextView( textViewParam, iterate.getString(brand),false );
                            row.addView(curBrand);
                        }
                        if (filters[1] == 1) {
                            TextView curSize = createTextView( numTextViewParam, iterate.getString(size),true );
                            row.addView(curSize);
                        }
                        if (filters[2] == 1) {
                            TextView curFreq = createTextView( numTextViewParam, iterate.getString(freq),true );
                            row.addView(curFreq);
                        }
                        if (filters[3] == 1) {
                            TextView curAvg = createTextView( numTextViewParam, iterate.getString(avgP),true );
                            row.addView(curAvg);
                        }
                        if (filters[4] == 1) {
                            TextView curLow = createTextView( numTextViewParam, iterate.getString(lowP),true );
                            row.addView(curLow);
                        }
                        if (filters[5] == 1) {
                            TextView curHigh = createTextView( numTextViewParam, iterate.getString(highP),true );
                            row.addView(curHigh);
                        }
                        if (filters[6] == 1) {
                            TextView curStore = createTextView( numTextViewParam, iterate.getString(store),false );
                            row.addView(curStore);
                        }
                        if (filters[7] == 1) {
                            TextView curTotal = createTextView( numTextViewParam, iterate.getString(total),true );
                            row.addView(curTotal);
                        }
                        Table.addView(row);
                    } else {
                        TextView curBrand = createTextView( textViewParam, iterate.getString(brand),false );
                        TextView curSize = createTextView( numTextViewParam, iterate.getString(size),true );
                        TextView curFreq = createTextView( numTextViewParam, iterate.getString(freq),true );
                        TextView curAvg = createTextView( numTextViewParam, iterate.getString(avgP),true );
                        TextView curLow = createTextView( numTextViewParam, iterate.getString(lowP),true );
                        TextView curHigh = createTextView( numTextViewParam, iterate.getString(highP),true );
                        TextView curStore = createTextView( textViewParam, iterate.getString(store),false );
                        TextView curTotal = createTextView( numTextViewParam, iterate.getString(total),true );
                        createNewRow( curBrand, curSize, curFreq, curAvg, curLow, curHigh, curStore, curTotal);
                    }
                    iterate.moveToNext();
                }
            }
            iterate.close();

            //Create rows based on data
            /*iterate.moveToFirst();
            if (iterate != null && (iterate.getCount() > 0)) { //Make sure there is something to iterate through
                for( int r = 0; r < iterate.getCount(); r++ ) { //Iterate through all
                    TableRow row = new TableRow(this);
                    TextView curBrand  = createTextView(textViewParam, iterate.getString(brand),  false);
                    TextView curSize   = createTextView(numTextViewParam, iterate.getString(size), true);
                    TextView curAvgP   = createTextView(numTextViewParam, iterate.getString(avgP), true);
                    TextView curLowP   = createTextView(numTextViewParam, iterate.getString(lowP), true);
                    TextView curHighP  = createTextView(numTextViewParam, iterate.getString(highP),true);
                    TextView curDate  = createTextView(numTextViewParam, iterate.getString(date),true);
                    TextView curStore  = createTextView(textViewParam, iterate.getString(store),  false);
                    createNewRow( curBrand, curSize, curAvgP, curLowP, curHighP, curDate, curStore);

                    iterate.moveToNext();
                }
            }
            iterate.close();
            */
        }catch(Exception e){
            Log.e("PRODDETAIL ERROR", "Problem getting product details for " + product + ". Error: " + e);
        }
    }

    //Create a new row
    private void createNewRow ( TextView brand, TextView size, TextView freq, TextView avgP, TextView lowP, TextView highP, TextView store, TextView total) {
        TableRow row = new TableRow(this);

        row.addView(brand);
        row.addView(size);
        row.addView(freq);
        row.addView(avgP);
        row.addView(lowP);
        row.addView(highP);
        row.addView(store);
        row.addView(total);

        Table.addView(row);
    }

    //Create Text View
    private TextView createTextView (TableRow.LayoutParams aParam, String toDisplay, boolean isNum ){
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
            //Toast.makeText(this, "Database Linked", Toast.LENGTH_SHORT).show();
            //CREATE TEST TABLE FOR EGGS
            shoppyDB.execSQL("CREATE TABLE IF NOT EXISTS eggs (brand VARCHAR, size integer, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), highestPrice float(9,2), store VARCHAR, totalSpent float(9,2), primary key(brand, size) );");
            //shoppyDB.execSQL("INSERT INTO eggs (brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent) VALUES (leggos, 12, 1, 2.22, 1.11, 3.33, Teeter, 2.22);");
            ContentValues c = new ContentValues();
            c.put("brand", "Humpty");
            c.put("size", 8);
            c.put("frequency", 1);
            c.put("avgPrice", 3.02);
            c.put("lowestPrice", 1.55);
            c.put("highestPrice", 3.02);
            c.put("store", "Target");
            c.put("totalSpent", 3.02);
            shoppyDB.insert("eggs", null, c);
            //END TEST CODE

            return shoppyDB;
        } catch (Exception e) {
            System.out.println("DATABASE ERROR" + "Product Details: Error opening Database");
            return null;
        }
    }

}