package com.foodteam.shoppy;

import android.app.ActionBar;
import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
import android.widget.LinearLayout;


public class MasterList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);

        Button gotoFrontBtn = (Button) findViewById(R.id.gotoFilters );
        gotoFrontBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent( getApplicationContext(), Filters.class );
                startActivity(startIntent);

            }
        });

//        LinearLayout attOneCol = (LinearLayout) findViewById(R.id.AttributeOneColumn);
//        LayoutParams params = new LayoutParams( LayoutParams.MATCH_PARENT, );       //CONTINUE ME
//        String[] items = { "egg", "bread", "milk"};
//        for( int i = 0; i < items.length; i++ ) {
//
//        }

    }

}