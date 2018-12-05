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
import android.widget.Toast;

public class MasterListFilters extends AppCompatActivity {
//
    private int[] filters;
    SQLiteDatabase shoppy;
    DBHandler shoppyHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list_filters);
        setArray();

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

        Button applyFilters = findViewById(R.id.applyfilters);
        applyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatever = new Intent(MasterListFilters.this, MasterList.class);
                whatever.putExtra("filtersList", filters);
                startActivity(whatever);
            }
        });
    }

    protected void setArray() {
        filters = new int[] {1, 1, 1, 1, 1, 0};
    }

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.Name:
                if(checked) {
                    filters[0] = 1;
                } else {
                    filters[0] = 0;
                }
                break;

            case R.id.Frequency:
                if(checked) {
                    filters[1] = 1;
                } else {
                    filters[1] = 0;
                }
                break;

            case R.id.PriceAvg:
                if(checked) {
                    filters[2] = 1;
                } else {
                    filters[2] = 0;
                }
                break;

            case R.id.LowPrice:
                if(checked) {
                    filters[3] = 1;
                } else {
                    filters[3] = 0;
                }
                break;

            case R.id.TotalSpent:
                if(checked) {
                    filters[4] = 1;
                } else {
                    filters[4] = 0;
                }
                break;

            default: break;
        }

        //testing to make ensure that the proper checkbox is affecting the array
        //every time a checkbox is clicked, adjust the array
        /*
        String test = "";

        for(int i = 0; i < 6; i++) {
            test += filters[i];
        }
        Toast.makeText(this, test, Toast.LENGTH_SHORT).show();
        */
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.NameAlphabetical:
                if(checked) {
                    filters[5] = 0;
                }
                break;

            case R.id.FrequencyLH:
                if(checked) {
                    filters[5] = 1;
                }
                break;

            case R.id.FrequencyHL:
                if(checked) {
                    filters[5] = 2;
                }
                break;

            case R.id.Lowest:
                if(checked) {
                    filters[5] = 3;
                }
                break;

            case R.id.TotalLH:
                if(checked) {
                    filters[5] = 4;
                }
                break;

            case R.id.TotalHL:
                if(checked) {
                    filters[5] = 5;
                }
                break;

            default: break;
        }

        //testing to make ensure that the proper checkbox is affecting the array
        //every time a checkbox is clicked, adjust the array
        /*
        String test = "";

        for(int i = 0; i < 6; i++) {
            test += filters[i];
        }
        Toast.makeText(this, test, Toast.LENGTH_SHORT).show();
        */
    }
}
