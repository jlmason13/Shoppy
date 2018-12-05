package com.foodteam.shoppy;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import static org.junit.Assert.*;
@RunWith(RobolectricTestRunner.class)
public class ListTest {
    SQLiteDatabase db;
    DBHandler Handler;
    List listActiv;

    @Before
    public void prepareForTesting() {
        MainMenu activityMM = Robolectric.setupActivity(MainMenu.class);
        Handler = DBHandler.getInstance(activityMM.getApplicationContext());
        db = activityMM.shoppyDB;
        Handler.onCreate(db);

        for (int i = 0; i < 5; i++) {
            Handler.addProduct(db, "dummyList", "item" + i);
        }
        Handler.addProduct(db, "dummyList", "dummyProduct");

        //setup the activity to test
        listActiv = Robolectric.setupActivity(List.class);
        listActiv.tablename = "dummyList";
        listActiv.productname = "dummyProd";
    }

    @Test
    public void onCreate() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        ActivityController<List> activity = Robolectric.buildActivity(List.class, intent);
        List list = activity.get();
        list.clr = null;
        list.onCreate(new Bundle());
    }

    @Test
    public void onCreateWithIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("nameOfTable", "dummyList");
        Activity activity = Robolectric.buildActivity(List.class, intent).create().get();
    }

    @Test
    public void deleteItem() {
        //Button b = listActiv.findViewById(R.id.addItemButton);
        listActiv.testdelete("item1");
    }

    @Test
    public void baddeleteItem() {
        DBHandler save = listActiv.shoppyHelp;
        listActiv.shoppyHelp = null;
        listActiv.testdelete("and");
        listActiv.shoppyHelp = save;
    }

    @Test
    public void prodDets() {
        TextView tv = listActiv.findViewById(R.id.productName);
        tv.setText("doesnt exist");
        listActiv.prodDetails(tv);
    }

    @Test
    public void prodDetsforExisting() {
        TextView tv = listActiv.findViewById(R.id.productName);
        tv.setText("dummyProduct");
        listActiv.prodDetails(tv);
    }

    @Test
    public void addProduct() {
        Button b = listActiv.findViewById(R.id.addItemButton);
        EditText e = listActiv.findViewById(R.id.newProdName);
        e.setText("dummy item");
        listActiv.addProduct(b);
    }

    @Test
    public void addBadProduct() {
        Button b = listActiv.findViewById(R.id.addItemButton);
        EditText e = listActiv.findViewById(R.id.newProdName);
        e.setText("update");
        listActiv.addProduct(b);
    }

    @Test
    public void addExistingProduct() {
        listActiv.tablename = "dummyList";
        Button b = listActiv.findViewById(R.id.addItemButton);
        EditText e = listActiv.findViewById(R.id.newProdName);
        e.setText("dummyProduct");
        listActiv.addProduct(b);
    }

    @Test
    public void badaddProduct() {
        Button b = listActiv.findViewById(R.id.addItemButton);
        EditText e = listActiv.findViewById(R.id.newProdName);
        DBHandler save = listActiv.shoppyHelp;
        listActiv.shoppyHelp = null;
        e.setText("stuff");
        listActiv.addProduct(b);
        listActiv.shoppyHelp = save;
    }

    @Test
    public void otherbadaddProduct() {
        Button b = listActiv.findViewById(R.id.addItemButton);
        EditText e = listActiv.findViewById(R.id.newProdName);
        listActiv.tablename = "badTable";
        e.setText("dummyProduct");
        listActiv.addProduct(b);
    }

    @Test
    public void addEmptyProduct() {
        Button b = listActiv.findViewById(R.id.addItemButton);
        EditText e = listActiv.findViewById(R.id.newProdName);
        e.setText("");
        listActiv.addProduct(b);
    }

    @Test
    public void testpopulateListView() {
        listActiv.tablename = "dummyList";
        listActiv.testpopulateListView("dummyLists");
    }

    @Test
    public void testdone() {
        Button b = listActiv.findViewById(R.id.doneShopping);
        listActiv.doneShopping(b);
    }

    @Test
    public void testInCart() {
        CheckBox c = listActiv.findViewById(R.id.inCart);
        c.setChecked(true);
        listActiv.cart(c);
    }

    @Test
    public void testCart() {
        CheckBox c = listActiv.findViewById(R.id.inCart);
        c.setChecked(false);
        listActiv.cart(c);
    }

    @Test
    public void badtestCart() {
        CheckBox c = listActiv.findViewById(R.id.inCart);
        c.setChecked(false);
        listActiv.tablename = "badTable";
        listActiv.cart(c);
    }

    @Test
    public void testback() {
        Button b = listActiv.findViewById(R.id.back);
        listActiv.back(b);
    }

    @Test
    public void addSuggestion() {
        //when click on suggestion
        Button b = listActiv.findViewById(R.id.suggestProd);
        b.setText("suggestion");
        listActiv.addSuggestion(b);
    }

    @Test
    public void addBadSuggestion() {
        //when click on suggestion
        Button b = listActiv.findViewById(R.id.suggestProd);
        b.setText("no suggestions");
        listActiv.addSuggestion(b);
    }

    @Test
    public void setSuggestion() {
        listActiv.setSuggestion();
    }

    @Test
    public void badsetSuggestion() {
        listActiv.tablename = "badTable";
        listActiv.setSuggestion();
    }

    @Test
    public void newSug() {
        ImageButton b = listActiv.findViewById(R.id.newSuggestion);
        listActiv.newSug(b);
    }

    @After
    public void getRidOfFakeInfo() {
        for (int i = 0; i < 5; i++) {
           Handler.deleteProd(db, "dummyList", "item" + i);
        }
    }

}