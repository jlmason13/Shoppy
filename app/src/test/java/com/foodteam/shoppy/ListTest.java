package com.foodteam.shoppy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

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

        //setup the activity I want to test
        listActiv = Robolectric.setupActivity(List.class);
        listActiv.tablename = "dummyList";
    }

    @Test
    public void deleteItem() {
        //Button b = listActiv.findViewById(R.id.addItemButton);
        listActiv.testdelete("item1");
    }

    @Test
    public void prodDets() {
        TextView tv = listActiv.findViewById(R.id.productName);
        listActiv.prodDetails(tv);
    }

    @Test
    public void addProduct() {
        Button b = listActiv.findViewById(R.id.addItemButton);
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
    public void testCart() {
        CheckBox c = listActiv.findViewById(R.id.inCart);
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
        listActiv.addSuggestion(b);
    }

    @Test
    public void setSuggestion() {
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