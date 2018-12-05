package com.foodteam.shoppy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class Filters extends AppCompatActivity {

    //brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent

    int[] filters;
    String product;
    String origin;
    SQLiteDatabase shoppy;
    DBHandler shoppyHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        initializeArray();

        try {
            //connect to db
            shoppy = this.openOrCreateDatabase("shoppyDB", MODE_PRIVATE, null );
            shoppyHelp = DBHandler.getInstance(getApplicationContext());


        } catch (Exception e) {
            Log.e("ERROR GETTING DATABASE", "Problem getting database");
            // Toast.makeText(this, "uh oh!", Toast.LENGTH_LONG).show();
        }
        ColorChanges obj = new ColorChanges();
        View view = this.getWindow().getDecorView();
        obj.setWindowCOlor(shoppy, shoppyHelp, view, getWindow());

        if(savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if(extra == null) {
                product = null;
            } else {
                product = extra.getString("THEPRODUCTNAME");
                origin = extra.getString("nameOfTable");
            }
        } else {
            product = (String) savedInstanceState.getSerializable("THEPRODUCTNAME");
            origin = (String) savedInstanceState.getSerializable("nameOfTable");
        }

        Button applyFilters = findViewById(R.id.applyFilters);
        applyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatever = new Intent(Filters.this, ProductDetails.class);
                whatever.putExtra("filterslist", filters);
                whatever.putExtra("THEPRODUCTNAME", product);
                whatever.putExtra("nameOfTable", origin);
                startActivity(whatever);
            }
        });
    }

    protected void initializeArray() {
        filters = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 0};
    }

    public void onCheckBoxClicked(View view) {
        Boolean checked = ((CheckBox) view).isChecked();

        //brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent

        switch(view.getId()) {
            case R.id.brand:
                if(checked) {
                    filters[0] = 1;
                } else {
                    filters[0] = 0;
                }
                break;

            case R.id.size:
                if(checked) {
                    filters[1] = 1;
                } else {
                    filters[1] = 0;
                }
                break;

            case R.id.frequency:
                if(checked) {
                    filters[2] = 1;
                } else {
                    filters[2] = 0;
                }
                break;

            case R.id.avgPrice:
                if(checked) {
                    filters[3] = 1;
                } else {
                    filters[3] = 0;
                }
                break;

            case R.id.lowestPrice:
                if(checked) {
                    filters[4] = 1;
                } else {
                    filters[4] = 0;
                }
                break;

            case R.id.highestPrice:
                if(checked) {
                    filters[5] = 1;
                } else {
                    filters[5] = 0;
                }
                break;

            case R.id.store:
                if(checked) {
                    filters[6] = 1;
                } else {
                    filters[6] = 0;
                }
                break;

            case R.id.totalSpent:
                if(checked) {
                    filters[7] = 1;
                } else {
                    filters[7] = 0;
                }
                break;

            case R.id.date:
                if(checked) {
                    filters[8] = 1;
                } else {
                    filters[8] = 0;
                }
                break;

            default: break;
        }
    }

    public void onRadioClicked(View view) {
        Boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.MostRecentPurchase:
                if(checked) {
                    filters[9] = 0;
                }
                break;

            case R.id.OldestPurchase:
                if(checked) {
                    filters[9] = 1;
                }
                break;

            case R.id.StoreAlephabetical:
                if(checked) {
                    filters[9] = 2;
                }
                break;

            case R.id.BrandAlephabetical:
                if(checked) {
                    filters[9] = 3;
                }
                break;

            case R.id.PriceAverage:
                if(checked) {
                    filters[9] = 4;
                }
                break;

            case R.id.PriceLowest:
                if(checked) {
                    filters[9] = 5;
                }
                break;

            case R.id.PriceHeighest:
                if(checked) {
                    filters[9] = 6;
                }
                break;

            default: break;
        }
    }
}