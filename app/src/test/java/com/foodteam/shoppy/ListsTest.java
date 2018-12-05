package com.foodteam.shoppy;

import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
@RunWith(RobolectricTestRunner.class)
public class ListsTest {
    SQLiteDatabase db;
    DBHandler help;
    Lists listsActiv;

    @Before
    public void prepareForTesting() {
        MainMenu activityMM = Robolectric.setupActivity(MainMenu.class);
        help = DBHandler.getInstance(activityMM.getApplicationContext());
        db = activityMM.shoppyDB;
        help.onCreate(db);

        help.createItemLists(db, "dummyList");

        //setup the activity I want to test
        listsActiv = Robolectric.setupActivity(Lists.class);
    }

    @Test
    public void onDelete() {
        listsActiv.testdelete("dummyList");
    }

    @Test
    public void gotoList() {
        TextView b = listsActiv.findViewById(R.id.nameOfList);
        listsActiv.gotoList(b);
    }

    @Test
    public void addList() {
        Button b = listsActiv.findViewById(R.id.createList);
        EditText e = listsActiv.findViewById(R.id.newListName);
        e.setText("dummy list");
        listsActiv.addList(b);
    }

    @Test
    public void testpopulateListView() {
        listsActiv.populateListView();
    }

    @Test
    public void testback() {
        Button b = listsActiv.findViewById(R.id.back);
        listsActiv.back(b);
    }

    @After
    public void getRidOfFakeInfo() {
        help.removeListTable(db, "dummyList");
    }
}