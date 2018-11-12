package com.foodteam.shoppy;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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


public class MasterList extends AppCompatActivity {

    SQLiteDatabase theDatabase;
    TableLayout theTable;
    int[] filters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("here0");
        openTheDatabase();
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
                Intent startIntent = new Intent( getApplicationContext(), MasterListFilters.class );    // MasterListFilters.class complaining because don't have current master version
                startActivity(startIntent);

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
        Cursor itterate = theDatabase.rawQuery( "select * from MasterList;", null);

        int productColumn       = itterate.getColumnIndex("product");
        int freqColumn          = itterate.getColumnIndex("frequency");
        int avgPriceColumn      = itterate.getColumnIndex("avgPrice");
        int lowestPriceColumn   = itterate.getColumnIndex("lowestPrice");
        int totalSpentColumn    = itterate.getColumnIndex("totalSpent");

        Button   detailsButton   = new Button(this);
        detailsButton.setText("Details");

        itterate.moveToFirst();

        System.out.println("here1");    //track where error occurs
        int i = 2;

        if (itterate != null && (itterate.getCount() > 0)) {
            do{
                System.out.println("here" + i); i++;
                TextView curProduct      = createTextView( itterate.getString(productColumn)     );
                TextView curFreq         = createTextView( itterate.getString(freqColumn)        );
                TextView curAvg          = createTextView( itterate.getString(avgPriceColumn)    );
                TextView curLow          = createTextView( itterate.getString(lowestPriceColumn) );
                TextView curTotalSpent   = createTextView( itterate.getString(totalSpentColumn)  );

                createNewRow( curProduct, curFreq, curAvg, curLow, curTotalSpent, detailsButton);   //details Button has no onclickListener
            } while ( itterate.moveToNext() );

        }
        return 1;
    }

    private void createNewRow ( TextView curProduct, TextView curFreq, TextView curAvg, TextView curLow, TextView curTotalSpent, Button detailsButton ) {
        //TODO Must figure out how to implement order based on filter
        TableRow row = new TableRow(this);

        row.addView(curProduct);
        row.addView(curFreq);
        row.addView(curAvg);
        row.addView(curLow);
        row.addView(curTotalSpent);
        row.addView(detailsButton);

        theTable.addView(row);
    }

    private TextView createTextView (String toDisplay ){
        TextView text = new TextView( this );
        text.setText( toDisplay );
        return text;
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