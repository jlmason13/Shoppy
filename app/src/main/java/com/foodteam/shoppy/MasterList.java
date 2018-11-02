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
import android.widget.TextView;


public class MasterList extends AppCompatActivity {

    SQLiteDatabase theDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openTheDatabase();
        setContentView(R.layout.activity_master_list);
        addMasterListProductData();



        Button gotoFrontBtn = (Button) findViewById(R.id.gotoFilters );
        gotoFrontBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent( getApplicationContext(), Filters.class );
                startActivity(startIntent);

            }
        });

//        //Test dynamically adding textviews
//        LinearLayout attOneCol = (LinearLayout) findViewById(R.id.AttributeOneColumn);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);       //CONTINUE ME
//        String[] items = { "egg", "bread", "milk"};
//        for( int i = 0; i < items.length; i++ ) {
//            TextView item = new TextView( this );
//            item.setText( items[i] );
//            attOneCol.addView( item );
//        }

    }


    private void openTheDatabase() {
        try {
            theDatabase = this.openOrCreateDatabase("Shoppy", MODE_PRIVATE, null );
        } catch (Exception e) {
            Log.e("DATABASE ERROR","Zofi: Error opening Database");
        }

    }

    private void addMasterListProductData() {
        Cursor itterate = theDatabase.rawQuery( "select * from MasterList", null);

        int productColumn       = itterate.getColumnIndex("product");
        int freqColumn          = itterate.getColumnIndex("frequency");
        int avgPriceColumn      = itterate.getColumnIndex("avgPrice");
        int lowestPriceColumn   = itterate.getColumnIndex("lowestPrice");
        int totalSpentColumn    = itterate.getColumnIndex("totalSpent");

        LinearLayout attOneCol       = (LinearLayout) findViewById(R.id.AttOneCol);
        LinearLayout attTwoCol      = (LinearLayout) findViewById(R.id.AttTwoCol);
        LinearLayout attThreeCol    = (LinearLayout) findViewById(R.id.AttThreeCol);
        LinearLayout attFourCol     = (LinearLayout) findViewById(R.id.AttFourCol);
        LinearLayout attFiveCol     = (LinearLayout) findViewById(R.id.AttFiveCol);
        LinearLayout attButtonCol   = (LinearLayout) findViewById(R.id.AttButtonCol);

        itterate.moveToFirst();

        if (itterate != null && (itterate.getCount() > 0)) {
            do{
               String curProduct = itterate.getString(productColumn);
               String curFreq = itterate.getString(freqColumn);
               String curAvg = itterate.getString(avgPriceColumn);
               String curLow = itterate.getString(lowestPriceColumn);
               String curTotalSpent = itterate.getString(totalSpentColumn);

               addTextViewToLinearLayout( curProduct, attOneCol);              //TODO Must figure out how to implement order based on filter
               addTextViewToLinearLayout( curFreq, attTwoCol);
               addTextViewToLinearLayout( curAvg, attThreeCol);
               addTextViewToLinearLayout( curLow, attFourCol);
               addTextViewToLinearLayout( curTotalSpent, attFiveCol);
               addButtonToLinearLayout( "Details", attButtonCol);

            } while ( itterate.moveToNext() );

        }
    }


    private void addTextViewToLinearLayout (String toDisplay, LinearLayout layout){
        TextView text = new TextView( this );
        text.setText( toDisplay );
        layout.addView( text );
    }

    private void addButtonToLinearLayout (String toDisplay, LinearLayout layout){
        Button text = new Button( this );
        text.setText( toDisplay );
        layout.addView( text );
    }
}