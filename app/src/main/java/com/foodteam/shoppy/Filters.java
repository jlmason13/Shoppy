package com.foodteam.shoppy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class Filters extends AppCompatActivity {

    int[] filters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        initializeArray();

        Button applyFilters = findViewById(R.id.applyFilters);
        applyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatever = new Intent(Filters.this, ProductDetails.class);
                whatever.putExtra("filterslist", filters);
                startActivity(whatever);
            }
        });
    }

    private void initializeArray() {
        filters = new int[] {1, 1, 1, 1, 1, 1, 0};
    }

    public void onCheckBoxClicked(View view) {
        Boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.Date:
                if(checked) {
                    filters[0] = 1;
                } else {
                    filters[0] = 0;
                }
                break;

            case R.id.Store:
                if(checked) {
                    filters[1] = 1;
                } else {
                    filters[1] = 0;
                }
                break;

            case R.id.Brand:
                if(checked) {
                    filters[2] = 1;
                } else {
                    filters[2] = 0;
                }
                break;

            case R.id.LowestPrice:
                if(checked) {
                    filters[3] = 1;
                } else {
                    filters[3] = 0;
                }
                break;

            case R.id.AvgPrice:
                if(checked) {
                    filters[4] = 1;
                } else {
                    filters[4] = 0;
                }
                break;

            case R.id.HighestPrice:
                if(checked) {
                    filters[5] = 1;
                } else {
                    filters[5] = 0;
                }
                break;
        }
    }

    public void onRadioClicked(View view) {
        Boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.MostRecentPurchase:
                if(checked) {
                    filters[6] = 0;
                }
                break;

            case R.id.OldestPurchase:
                if(checked) {
                    filters[6] = 1;
                }
                break;

            case R.id.StoreAlephabetical:
                if(checked) {
                    filters[6] = 2;
                }
                break;

            case R.id.BrandAlephabetical:
                if(checked) {
                    filters[6] = 3;
                }
                break;

            case R.id.PriceAverage:
                if(checked) {
                    filters[6] = 4;
                }
                break;

            case R.id.PriceLowest:
                if(checked) {
                    filters[6] = 5;
                }
                break;

            case R.id.PriceHeighest:
                if(checked) {
                    filters[6] = 6;
                }
                break;
        }
    }
}