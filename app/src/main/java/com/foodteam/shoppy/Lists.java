package com.foodteam.shoppy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class Lists extends AppCompatActivity {
    SQLiteDatabase shoppy;// = openOrCreateDatabase("shoppyDB.db", MODE_PRIVATE, null);
    private LinearLayout parentLinearLayout;
    private int listcount = 0;
    private String listname = "";
    private String tablename = "";
    EditText newName;
    ArrayList<String> results = new ArrayList<String>();
    DBHandler shoppyHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        shoppyHelp = DBHandler.getInstance(getApplicationContext());

        parentLinearLayout = (LinearLayout) findViewById(R.id.listArea);
        newName = (EditText)findViewById(R.id.newListName);

        //connect to db
        /*try {
            shoppy = openOrCreateDatabase("shoppyDB", MODE_PRIVATE, null);
            populateListView();
        } catch (Exception e) {
            Log.e("DATABASE ERROR", "Problem getting database");
        }*/



    }


    //Create List Button
        //when clicked:
            //txt box for list name
            //create table titled <txt from box>
            //go to list page w/ info from ^table



    //need to add buttons (list button and remove list buttons) for each exiting list?
        // table of lists will exist in database
        // a new view w/ button to enter list and button to delete list per row in listOlists



  /*  public void createList(View v) {
        if ( listcount <= 10 ) {
            listcount++;

            EditText newName = findViewById(R.id.newListName);



            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.list_helper, null);

            // Add the new row
            //figure out how to set button text for new row to what was in the edit text
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
            TextView nameoflist = findViewById(R.id.nameOfList);
            nameoflist.setText(newName.getText());
            //clear the edit text
            newName.setText("");

            //add list to lists table
            //create list table

        }
    }*/

    public void onDelete(View v) {
       // parentLinearLayout.removeView((View) v.getParent());
       // ((ViewGroup)v.getParent()).removeView(v);
        if ( listcount > 0) {
            //THIS DOESNT WORK IT GET RID OF ALL LIST BUTTONS
            //((ViewGroup) v.getParent().getParent()).removeView((View) v.getParent());


            ((ViewGroup)v.getParent()).removeAllViews();

            listcount--;
        }
        // remove list from shoppydb
        // should have an are you sure pop up
    }

    public void gotoList(View v) {
        // figure out way to pass name of list so you see the right one
        Intent showList = new Intent( getApplicationContext(), List.class );
        showList.putExtra("nameOfTable", tablename);
        startActivity(showList);
    }




    //on click funtion for addlist button to add the new list to the database
    public void addList(View v) {
        if (!TextUtils.isEmpty(newName.getText().toString())) {
            listname = newName.getText().toString();

            //get rid of spaces in listname for saving purposes
            tablename = "";
            for (int i = 0; i < listname.length()-1; i++) {
                if (listname.charAt(i) == ' ') {
                    tablename = tablename + '_';
                } else {
                    tablename = tablename + listname.charAt(i);
                }
            }

            try {
                //add row to Lists table
                String sql = new StringBuilder().append("INSERT INTO Lists (listName) VALUES(\"").append(tablename).append("\");").toString();
                shoppy.execSQL("INSERT INTO Lists VALUES(\")" + tablename + "\") ");

                //create table called newName.getText().toString()
                String make = new StringBuilder().append("CREATE TABLE  IF NOT EXISTS ").append(tablename).append(" ( product VARCHAR primary key, inCart BOOLEAN );").toString();
                shoppy.execSQL(make);

                //"re-draw" the list of lists
                populateListView();
            } catch (Exception e) {
                Log.e("DATABASE ERROR", "Problem inserting into database");
                Toast.makeText(this, "Database Error", Toast.LENGTH_LONG).show();
            }
        }
    }

    //function for adding all existing lists to the listView so they shoe up on screen
    private void populateListView() { //FIX THIS METHOD IT MAKE SHOPPY CRASH
        Cursor cur; cur = shoppy.rawQuery("select listName from Lists", null);
        if (cur!= null) {
           cur.moveToFirst();
        }

        if (cur != null ) {
            if  (cur.moveToFirst()) {
                do {
                    String currentList = cur.getString(cur.getColumnIndex("listName"));
                    results.add(currentList);
                }while (cur.moveToNext());
            }
        }
        ListView list = (ListView) findViewById(R.id.listOlists);
        list.setAdapter(new ArrayAdapter<String>(this, R.layout.list_helper));

        /*String[] existingLists = new String[]{listname};
        int[] toViewIDs = new int[]{R.id.nameOfList};
        SimpleCursorAdapter curAdapt = new SimpleCursorAdapter(getBaseContext(), R.layout.list_helper, cur, existingLists, toViewIDs, 0);
        ListView list = (ListView) findViewById(R.id.listOlists);
        list.setAdapter(curAdapt);
        */

        cur.close();
    }


  //  public static String getListName() {
    //    return tablename;
    //}

}