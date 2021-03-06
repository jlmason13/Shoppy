package com.foodteam.shoppy;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Locale;


public class MasterList extends AppCompatActivity {

    SQLiteDatabase theDatabase;
    TableLayout theTable;
    int[] filters;
    DBHandler Handler;
    ListName conversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openTheDatabase();
        conversion = new ListName();

        //assigns colors
        Handler = DBHandler.getInstance(getApplicationContext());
        ColorChanges obj = new ColorChanges();
        View view = this.getWindow().getDecorView();
        obj.setWindowCOlor(theDatabase, Handler, view, getWindow());

        setContentView(R.layout.activity_master_list);
        theTable = (TableLayout) findViewById(R.id.theTable);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                filters = null;
            } else {
                filters = extras.getIntArray("filtersList");
            }
        }
        addMasterListProductData();

        Button gotoFrontBtn = (Button) findViewById(R.id.gotoFilters );
        gotoFrontBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent( getApplicationContext(), MasterListFilters.class );
                startActivity(startIntent);

            }
        });

        Button returnToMainMenu = (Button) findViewById(R.id.returnToMainMenu);
        returnToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent( getApplicationContext(), MainMenu.class );
                startActivity(returnIntent);
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

    protected int addMasterListProductData() {
        //The layout parameters for contents of a row
        TableRow.LayoutParams buttonParam = new TableRow.LayoutParams(
                120,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        );
        TableRow.LayoutParams textViewParam = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        );
        TableRow.LayoutParams floatTextViewParam = new TableRow.LayoutParams(
                70,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        );
        TableRow.LayoutParams intTextViewParam = new TableRow.LayoutParams(
                5,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        );

        //retreive textViews of Attribute titles
        TextView attrOne   = (TextView) findViewById(R.id.AttributeOne);
        TextView attrTwo   = (TextView) findViewById(R.id.AttributeTwo);
        TextView attrThree = (TextView) findViewById(R.id.AttributeThree);
        TextView attrFour  = (TextView) findViewById(R.id.AttributeFour);
        TextView attrFive  = (TextView) findViewById(R.id.AttributeFive);


        // label the attributes based on filters array
        String[] attrNames = {"product", "frequency", "avgPrice", "lowestPrice", "totalSpent"};
        TextView[] attrLocales = {attrOne, attrTwo, attrThree, attrFour, attrFive};
        if (filters != null) {
            for (int i = 0, j = 0; i < filters.length - 1; i++) {
                if (filters[i] == 1) {
                    //System.out.println( "Zofi: filters index " + i + " equals " + filters[i]);    //for testing
                    attrLocales[j].setText(attrNames[i]);
                    j++;
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {               //5 = filters.length - 1
                attrLocales[i].setText(attrNames[i]);
            }
        }

        // adjusts query based on last element of filters array
        String query = "select * from Masterlist order by ";
        if (filters != null) {
            switch (filters[5]) {
                case 0:
                    query = query + "product ASC;";
                    break;
                case 1:
                    query = query + "frequency ASC;";
                    break;
                case 2:
                    query = query + "frequency DESC;";
                    break;
                case 3:
                    query = query + "lowestPrice ASC;";
                    break;
                case 4:
                    query = query + "totalSpent ASC;";
                    break;
                case 5:
                    query = query + "totalSpent DESC;";
                    break;
                default:
                    query = query + "product ASC;";
            }
        } else {
            query = query + "product;";
        }

        Cursor itterate = theDatabase.rawQuery( query, null );

        int productColumn       = itterate.getColumnIndex("product");
        int freqColumn          = itterate.getColumnIndex("frequency");
        int avgPriceColumn      = itterate.getColumnIndex("avgPrice");
        int lowestPriceColumn   = itterate.getColumnIndex("lowestPrice");
        int totalSpentColumn    = itterate.getColumnIndex("totalSpent");


        //create rows based on data from cursor and the filters array, only includes columns that were selected via the filters page
        itterate.moveToFirst();
        if (itterate != null && (itterate.getCount() > 0)) {
            for( int r = 0; r < itterate.getCount(); r++ ) {
                TableRow row = new TableRow(this);
                if ( filters != null ) {
                    if (filters[0] == 1) {
                        TextView curProduct     = createTextView( textViewParam,    conversion.toListName(itterate.getString(productColumn)),0 );
                        row.addView(curProduct);
                    }
                    if (filters[1] == 1) {
                        TextView curFreq        = createTextView( intTextViewParam, itterate.getString(freqColumn),1 );
                        row.addView(curFreq);
                    }
                    if (filters[2] == 1) {
                        String toDisplay = String.format( Locale.getDefault(),"%.2f", itterate.getFloat(avgPriceColumn) );
                        TextView curAvg         = createTextView( floatTextViewParam, toDisplay,2 );
                        row.addView(curAvg);
                    }
                    if (filters[3] == 1) {
                        String toDisplay = String.format( Locale.getDefault(), "%.2f", itterate.getFloat(lowestPriceColumn) );
                        TextView curLow         = createTextView( floatTextViewParam, toDisplay, 2 );
                        row.addView(curLow);
                    }
                    if (filters[4] == 1) {
                        String toDisplay = String.format( Locale.getDefault(), "%.2f", itterate.getFloat(totalSpentColumn) );
                        TextView curTotalSpent  = createTextView( floatTextViewParam, toDisplay,  2 );
                        row.addView(curTotalSpent);
                    }
                    Button detailsButton        = createNewButton( buttonParam, itterate.getString(productColumn) );
                    row.addView(detailsButton);
                    theTable.addView(row);
                } else {
                    String displayAvg = String.format( Locale.getDefault(),"%.2f", itterate.getFloat(avgPriceColumn) );
                    String displayLow = String.format( Locale.getDefault(), "%.2f", itterate.getFloat(lowestPriceColumn) );
                    String displayTot = String.format( Locale.getDefault(), "%.2f", itterate.getFloat(totalSpentColumn) );

                    TextView curProduct      = createTextView( textViewParam,    conversion.toListName(itterate.getString(productColumn)), 0 );
                    TextView curFreq         = createTextView( intTextViewParam, itterate.getString(freqColumn),    1 );
                    TextView curAvg          = createTextView( floatTextViewParam, displayAvg,                        2 );
                    TextView curLow          = createTextView( floatTextViewParam, displayLow,                        2 );
                    TextView curTotalSpent   = createTextView( floatTextViewParam, displayTot,                        2 );
                    Button detailsButton     = createNewButton( buttonParam, itterate.getString(productColumn) );
                    createNewRow( curProduct, curFreq, curAvg, curLow, curTotalSpent, detailsButton);
                }
                itterate.moveToNext();
            }
        }
        itterate.close();
        return 1;
    }

    //handles creating a new row when filters array is not initialized
    protected TableRow createNewRow ( TextView curProduct, TextView curFreq, TextView curAvg, TextView curLow, TextView curTotalSpent, Button detailsButton ) {
        TableRow row = new TableRow(this);

        row.addView(curProduct);
        row.addView(curFreq);
        row.addView(curAvg);
        row.addView(curLow);
        row.addView(curTotalSpent);
        row.addView(detailsButton);

        theTable.addView(row);
        return row;
    }

    //name is self-explanatory
    protected TextView createTextView (TableRow.LayoutParams aParam, String toDisplay, int type ){
        TextView text = new TextView( this );
        text.setText( toDisplay );
        text.setTextSize(16);
        if ( type == 0 ) {
            text.setEms(3);     //wraps the text
        } else if ( type == 1) {
            text.setEms(1);
        } else {
            text.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }
        text.setLayoutParams(aParam);
        text.setTextColor( Color.parseColor("#000000") );
        return text;
    }

    //name is self-explanatory
    protected Button createNewButton(TableRow.LayoutParams buttonParam, String productName) {
        final String product = productName;
        Button detailsButton = new Button(this);
        detailsButton.setText("Details");
        detailsButton.setLayoutParams(buttonParam);
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transferIntent = new Intent(getApplicationContext(), ProductDetails.class);
                transferIntent.putExtra("RETURN", "MasterList");
                transferIntent.putExtra("THEPRODUCTNAME", product);
                startActivity(transferIntent);
            }
        });
        return detailsButton;
    }

}

/**
 input from Filters format for array:
 0 or 1  name  (0 means don't display, 1 means display)
 0 or 1	 freq
 0 or 1	 avg
 0 or 1	 lowest
 0 or 1  totalprice
 0 - 5   radio buttons
            0 = alpha, 1 = freq low to high, 2 = freq high to low, 3 = lowest price, 4 = total spent low to high, 5 = total spent high to low
 **/