package com.foodteam.shoppy;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
    public void onCreate() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        ActivityController<Lists> activity = Robolectric.buildActivity(Lists.class, intent);
        Lists lists = activity.get();
        lists.clr = null;
        lists.onCreate(new Bundle());
    }

    @Test
    public void onCreateWithIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Activity activity = Robolectric.buildActivity(List.class, intent).create().get();
    }

    @Test
    public void onDelete() {
        listsActiv.testdelete("dummyList");
    }

    @Test
    public void badonDelete() {
        DBHandler save = listsActiv.shoppyHelp;
        listsActiv.shoppyHelp = null;
        listsActiv.testdelete("badlist");
        listsActiv.shoppyHelp = save;
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
    public void addBadList() {
        Button b = listsActiv.findViewById(R.id.createList);
        EditText e = listsActiv.findViewById(R.id.newListName);
        e.setText("update");
        listsActiv.addList(b);
    }

    @Test
    public void addExistingList() {
        Button b = listsActiv.findViewById(R.id.createList);
        EditText e = listsActiv.findViewById(R.id.newListName);
        e.setText("dummyList");
        listsActiv.addList(b);
    }

    @Test
    public void addEmptyList() {
        Button b = listsActiv.findViewById(R.id.createList);
        EditText e = listsActiv.findViewById(R.id.newListName);
        e.setText("");
        listsActiv.addList(b);
    }

    @Test
    public void badaddList() {
        Button b = listsActiv.findViewById(R.id.createList);
        EditText e = listsActiv.findViewById(R.id.newListName);
        e.setText("item");
        SQLiteDatabase save = listsActiv.shoppy;
        listsActiv.shoppy = null;
        listsActiv.addList(b);
        listsActiv.shoppy = save;
    }

    @Test
    public void otherbadaddList() {
        Button b = listsActiv.findViewById(R.id.createList);
        EditText e = listsActiv.findViewById(R.id.newListName);
        e.setText("item");
        DBHandler save = listsActiv.shoppyHelp;
        listsActiv.shoppyHelp = null;
        listsActiv.addList(b);
        listsActiv.shoppyHelp = save;
    }

    @Test
    public void testpopulateListView() {
        listsActiv.populateListView();
    }

    @Test
    public void badtestpopulateListView() {
        DBHandler save = listsActiv.shoppyHelp;
        listsActiv.shoppyHelp = null;
        listsActiv.populateListView();
        listsActiv.shoppyHelp = save;
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