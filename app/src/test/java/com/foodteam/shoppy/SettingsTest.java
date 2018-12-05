package com.foodteam.shoppy;
    import android.content.Context;
    import org.junit.Before;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import static org.hamcrest.core.AllOf.allOf;
    import static org.junit.Assert.assertEquals;
    import android.content.Context;
    import android.content.Intent;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.widget.Button;
    import android.widget.TextView;
    import org.junit.Rule;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import org.robolectric.Robolectric;
    import org.robolectric.RobolectricTestRunner;
    import org.robolectric.annotation.Config;
    import static org.hamcrest.core.AllOf.allOf;
    import static org.junit.Assert.assertEquals;
    import static org.junit.Assert.assertThat;
    import static org.junit.Assert.assertTrue;
    import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class SettingsTest {

    Settings activitySettings;

    //The testing database variable
    SQLiteDatabase theDatabase;

    @Before
    public void prepareTesting(){
        MainMenu activityMM = Robolectric.setupActivity(MainMenu.class);
        DBHandler Handler = DBHandler.getInstance(activityMM.getApplicationContext());
        theDatabase = activityMM.shoppyDB;
        Handler.onCreate(theDatabase);

        activitySettings = Robolectric.setupActivity(Settings.class);
    }

    @Test
    public void Red(){
        //click button
        Button submit = activitySettings.findViewById(R.id.red);
        submit.performClick();
    }

    @Test
    public void Orange(){
        //click button
        Button submit = activitySettings.findViewById(R.id.orange);
        submit.performClick();
    }

    @Test
    public void Yellow(){
        //click button
        Button submit = activitySettings.findViewById(R.id.yellow);
        submit.performClick();
    }

    @Test
    public void Blue(){
        //click button
        Button submit = activitySettings.findViewById(R.id.blue);
        submit.performClick();
    }

    @Test
    public void Green(){
        //click button
        Button submit = activitySettings.findViewById(R.id.green);
        submit.performClick();
    }

    @Test
    public void Purple(){
        //click button
        Button submit = activitySettings.findViewById(R.id.purple);
        submit.performClick();
    }

    @Test
    public void Pink(){
        //click button
        Button submit = activitySettings.findViewById(R.id.pink);
        submit.performClick();
    }

    @Test
    public void White(){
        //click button
        Button submit = activitySettings.findViewById(R.id.white);
        submit.performClick();
    }

    @Test
    public void Return(){
        //click button
        Button submit = activitySettings.findViewById(R.id.Back);
        submit.performClick();
    }

    @Test
    public void StartFresh(){

        //Inserting things to delete.... testing clearing datatbase
        theDatabase.execSQL("insert into Lists (listName) values ('GroceryList')");
        theDatabase.execSQL("CREATE TABLE IF NOT EXISTS GroceryList" +
                "( product VARCHAR primary key, inCart int) ;");
        theDatabase.execSQL( "insert into GroceryList (product, inCart) values ('lembasBread', 0);");

        theDatabase.execSQL("insert into MasterList " +
                "(product, frequency, avgPrice, lowestPrice, totalSpent) " +
                "values ( 'lembasBread', 0, 0.00, 0.00, 0.00 );");

        theDatabase.execSQL("CREATE TABLE IF NOT EXISTS lembasBread" +
                "(brand VARCHAR, size integer, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), highestPrice float(9,2), store VARCHAR, totalSpent float(9,2) " +
                ", primary key(brand, size) );");

        theDatabase.execSQL( "insert into lembasBread" +
                "(brand, size, frequency, avgPrice, lowestPrice, highestPrice, store, totalSpent ) " +
                "values ( 'WoodElf_Bakery', 12, 1, 4.42, 4.42, 4.42, 'Lothlorien', 4.42);" );

        //click button
        Button submit = activitySettings.findViewById(R.id.StartFresh);
        submit.performClick();

        //check that tables are empty
        Cursor checkMaster = theDatabase.rawQuery("select * from MasterList;", null);
        Cursor checkList = theDatabase.rawQuery("select * from Lists;", null);
        assertTrue( (checkList.getCount() == 0) && (checkMaster.getCount() == 0) );
    }
}
