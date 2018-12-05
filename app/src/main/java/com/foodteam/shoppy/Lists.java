package com.foodteam.shoppy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;
import java.util.ArrayList;
import android.util.Log;
import android.widget.Toast;

public class Lists extends AppCompatActivity {
    SQLiteDatabase shoppy;// = openOrCreateDatabase("shoppyDB", MODE_PRIVATE, null);
    DBHandler shoppyHelp;
    String listname = "";
    String tablename = "";
    EditText newName;
    ListName obj = new ListName();
    ColorChanges clr = new ColorChanges();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        newName = (EditText) findViewById(R.id.newListName);
        try {
            //connect to db
            shoppy = this.openOrCreateDatabase("shoppyDB", MODE_PRIVATE, null );
            shoppyHelp = DBHandler.getInstance(getApplicationContext());

            View view = this.getWindow().getDecorView();
            clr.setWindowCOlor(shoppy, shoppyHelp, view, getWindow());
        } catch (Exception e) {
            Log.e("ERROR GETTING DATABASE", "Problem getting database");
            e.printStackTrace();
        }

        populateListView();
    }

    public void onDelete(View v) {
        //get the name of the current list
        TextView  text = (TextView) ((LinearLayout)v.getParent()).findViewById(R.id.nameOfList);
        String name = text.getText().toString();
        name = obj.toTableName(name);

        try {
            // remove list from shoppydb
            shoppyHelp.removeListTable(shoppy, name);
            Toast.makeText(this, "list "+obj.toListName(name)+" removed", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("DATABASE ERROR", "Problem removing list from database");
            Toast.makeText(this, "ERROR REMOVING LIST", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        //redraw the list o' lists
        populateListView();
    }

    public void gotoList(View v) {
        //get the name of the current list
        TextView  text = (TextView) ((LinearLayout)v.getParent()).findViewById(R.id.nameOfList);
        String name = text.getText().toString();

        Intent showList = new Intent( getApplicationContext(), List.class );
        showList.putExtra("nameOfTable", name);
        startActivity(showList);
    }

    //on click function for addlist button to add the new list to the database
    public void addList(View v) {
        //dont do anything if the text feild is empty
        listname = newName.getText().toString();
        listname = listname.trim();
        if (!listname.equals("")) {//!TextUtils.isEmpty(newName.getText().toString())) {
            //listname = newName.getText().toString();
            //listname = listname.trim();
            //get rid of spaces in listname for saving purposes
            tablename = obj.toTableName(listname);

            if (obj.validName(obj.toListName(tablename))) {
                boolean exists = false;
                //Make sure no existing lists share the name
                try {
                    Cursor cur = shoppyHelp.getRows(shoppy, "Lists", "listName");
                    if (cur != null && (cur.getCount() > 0)) {
                        for (int i = 0; i < cur.getCount(); i++) {
                            if (cur.getString(cur.getColumnIndex("listName")).equals(tablename)) {
                                exists = true;
                            }
                        }
                    }
                    cur.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!exists) {
                    try {
                        shoppyHelp.createItemLists(shoppy, tablename); //adds the list to Lists table
                        shoppyHelp.createListTable(shoppy, tablename); //creates a table called tablename
                    } catch (Exception e) {
                        Log.e("DATABASE ERROR", "Problem inserting new list into database");
                        Toast.makeText(this, "ERROR ADDING LIST", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    //let user know add was successful
                    Toast.makeText(this, "list " + listname + " added", Toast.LENGTH_LONG).show();

                    //"re-draw" the list of lists
                    populateListView();
                } else {
                    Toast.makeText(this, listname + " already exists", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, listname + " is not valid", Toast.LENGTH_LONG).show();
            }
            //empty the edittext
            newName.setText("");
        } else {
            Toast.makeText(this, "Type in the box please", Toast.LENGTH_LONG).show();
        }
    }

    //function for adding all existing lists to the listView so they shoe up on screen
    public void populateListView() {
        ListView list = (ListView)findViewById(R.id.listOlists);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View list_item = inflater.inflate(R.layout.list_helper, null, false);
        LinearLayout row = list_item.findViewById(R.id.existingList);
        ArrayList<String> theList = new ArrayList<>();

        try {
            Cursor cur = shoppyHelp.getRows(shoppy, "Lists", "listName");
            if (cur != null && cur.getCount() > 0) {
                for( int i = 0; i < cur.getCount(); i++ ) {
                   theList.add(obj.toListName(cur.getString(cur.getColumnIndex("listName"))));
                   cur.moveToNext();
                }
            }
            cur.close();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_helper, R.id.nameOfList, theList);
            list.setAdapter(adapter);
        } catch ( Exception e) {
            Log.e("POPULATE ERROR", "Problem with populateListView");
            e.printStackTrace();
            Toast.makeText(this, "populate Error", Toast.LENGTH_LONG).show();
        }
    }

    public void back(View view) {
        Intent home = new Intent( getApplicationContext(), MainMenu.class );
        startActivity(home);
    }

    public void testdelete(String s) {
        TextView b = findViewById(R.id.delete_button);
        TextView  text = (TextView) ((LinearLayout)b.getParent()).findViewById(R.id.nameOfList);
        text.setText(s);
        onDelete(b);
    }
}