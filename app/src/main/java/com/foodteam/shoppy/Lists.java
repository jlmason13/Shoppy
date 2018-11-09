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


public class Lists extends AppCompatActivity {
    SQLiteDatabase shoppy;// = openOrCreateDatabase("shoppyDB.db", MODE_PRIVATE, null);
    private LinearLayout parentLinearLayout;
    private int listcount = 0;
    private String listname = "";
    EditText newName;
    ArrayList<String> results = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);


        parentLinearLayout = (LinearLayout) findViewById(R.id.listArea);

        newName = (EditText)findViewById(R.id.newListName);
        shoppy = openOrCreateDatabase("shoppyDB", MODE_PRIVATE, null);
        populateListView();

        //connect to db
        try (SQLiteDatabase shoppy = openOrCreateDatabase("shoppyDB.db", MODE_PRIVATE, null)) {

            // create list button goes to empty list page
           /* Button createlistbutton = findViewById(R.id.button_createlist);
            createlistbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //txt box for list name
                    String listName = "Enter List Name";
                    LinearLayout linearLayout = findViewById(R.id.listArea);
                   // setContentView(linearLayout);
                   // linearLayout.setOrientation(LinearLayout.VERTICAL);
                    EditText nameHolder = new EditText(getBaseContext());
                    nameHolder.setText(listName);
                  //  listName = nameHolder.getText().toString();
                    linearLayout.addView(nameHolder);


                    listName = nameHolder.toString();
                /////THIS IS WRONG - FIND A WAY TO GET TO NEW INTENT WITH CLICK OF ENTER KEY
                    nameHolder.setFocusableInTouchMode(true);
                    nameHolder.requestFocus();
                    nameHolder.setOnKeyListener(new View.OnKeyListener() {
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            // If the event is a key-down event on the "enter" button
                            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                // Perform action on key press
                               // setImeOptions(6);

                                //shoppy.insert("List", null, listName); //add new list to database
                                //create <listName> table


                                Intent showList = new Intent( getApplicationContext(), List.class );
                                startActivity(showList);
                                return true;
                            }
                            return false;
                        }
                    });

*/

                    /*
                    nameHolder.setOnKeyListener(new View.OnKeyListener() {
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                                //create table titled <txt from box>


                                //go to list page w/ info from ^table
                                Intent showList = new Intent( getApplicationContext(), List.class );
                                startActivity(showList);
                            }
                            return false;
                            if (event.getAction() == KeyEvent.ACTION_DOWN)
                            {
                                switch (keyCode)
                                {
                                    case KeyEvent.KEYCODE_DPAD_CENTER:
                                    case KeyEvent.KEYCODE_ENTER:
                                        addCourseFromTextBox();
                                        return true;
                                    default:
                                        break;
                                }
                            }
                            return false;
                        }

                    });*/
                  // Intent showList = new Intent( getApplicationContext(), List.class );
                  // startActivity(showList);
            //    }
           // });


            // dynamically add views to go to other existing lists
            // for each list in table lists
           Cursor cur = shoppy.rawQuery("select listName from Lists", new String [] {});

            while (cur.moveToNext()) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.list_helper, null);
                // Add the new row
                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
            }



            cur.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        // remove list from hoppydb
        // should have an are you sure pop up
    }

    public void gotoList(View v) {
        // figure out way to pass name of list so you see the right one
        Intent showList = new Intent( getApplicationContext(), List.class );
        startActivity(showList);
    }




    //on click funtion for addlist button to add the new list to the database
    public void addList(View v) {
        if (!TextUtils.isEmpty(newName.getText().toString())) {
            listname = newName.getText().toString();

            //get rid of spaces in listname for saving purposes
            String tablename = "";
            for (int i = 0; i < listname.length()-1; i++) {
                if (listname.charAt(i) == ' ') {
                    tablename = tablename + '_';
                } else {
                    tablename = tablename + listname.charAt(i);
                }
            }

            //add row to Lists table
            String sql = new StringBuilder().append("INSERT INTO Lists (listName) VALUES(\"").append(tablename).append("\");").toString();
            shoppy.execSQL("INSERT INTO Lists VALUES(\")"+ tablename +"\") ");

            //create table called newName.getText().toString()
            sql = new StringBuilder().append("CREATE TABLE  IF NOT EXISTS ").append(tablename).append(" ( product VARCHAR primary key, inCart BOOLEAN );").toString();
            shoppy.execSQL(sql);
        }
        populateListView();
    }

    //function for adding all existing lists to the listView so they shoe up on screen
    private void populateListView() {
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


    }



}