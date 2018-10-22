package com.foodteam.shoppy;

import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
import android.widget.ImageButton;

public class MasterList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);

        ImageButton gotoFrontBtn = (ImageButton) findViewById(R.id.returnToMainMenu );
        //gotoFrontBtn.setBackgroundResource(R.color.accent_1);
        gotoFrontBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent( getApplicationContext(), MainMenu.class );
                startActivity(startIntent);

            }
        });

    }

}