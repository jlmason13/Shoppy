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
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
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
    DBHandler shoppyHelp;

    private String listname = "";
    private String tablename = "";
    EditText newName;
    ListName obj = new ListName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        parentLinearLayout = (LinearLayout) findViewById(R.id.listArea);
        newName = (EditText) findViewById(R.id.newListName);
        try {
            //connect to db
            shoppy = this.openOrCreateDatabase("shoppyDB", MODE_PRIVATE, null );
            shoppyHelp = DBHandler.getInstance(getApplicationContext());


        } catch (Exception e) {
            Log.e("ERROR GETTING DATABASE", "Problem getting database");
           // Toast.makeText(this, "uh oh!", Toast.LENGTH_LONG).show();
        }
        populateListView();
    }

    public void onDelete(View v) {
        //get the name of the current list
        TextView  text = (TextView) ((LinearLayout)v.getParent()).findViewById(R.id.nameOfList);
        String name = text.getText().toString();
        name = obj.toTableName(name);
        //SHOULD ADD AN ARE YOU SURE POP UP

        try {
            // remove list from shoppydb
            shoppyHelp.removeListTable(shoppy, name);
            Toast.makeText(this, "list "+obj.toListName(name)+" removed", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("DATABASE ERROR", "Problem removing list from database");
            Toast.makeText(this, "ERROR REMOVING LIST", Toast.LENGTH_LONG).show();
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
        if (!TextUtils.isEmpty(newName.getText().toString())) {
            listname = newName.getText().toString();
            listname = listname.trim();
            //get rid of spaces in listname for saving purposes
            tablename = obj.toTableName(listname);

            boolean exists = false;
            //Make sure no existing lists share the name
            try {
                Cursor cur = shoppyHelp.getRows(shoppy, "Lists", "listName");
                if (cur != null && (cur.getCount() > 0)) {
                    for( int i = 0; i < cur.getCount(); i++ ) {
                        if (cur.getString(cur.getColumnIndex("listName")).equals(tablename)) {
                            exists = true;
                        }
                    }
                }
                cur.close();
            } catch ( Exception e) {
                e.printStackTrace();
            }

            if (!exists) {
                try {
                    shoppyHelp.createItemLists(shoppy, tablename); //adds the list to Lists table
                    shoppyHelp.createListTable(shoppy, tablename); //creates a table called tablename
                } catch (Exception e) {
                    Log.e("DATABASE ERROR", "Problem inserting new list into database");
                    Toast.makeText(this, "ERROR ADDING LIST", Toast.LENGTH_LONG).show();
                }
                //let user know add was successful
                Toast.makeText(this, "list " + listname + " added", Toast.LENGTH_LONG).show();

                //"re-draw" the list of lists
                populateListView();
            } else {
                Toast.makeText(this, listname + " already exists", Toast.LENGTH_LONG).show();
            }

            //empty the edittext
            newName.setText("");
        } else {
            Toast.makeText(this, "Type in the box please", Toast.LENGTH_LONG).show();
        }
    }

    //function for adding all existing lists to the listView so they shoe up on screen
    private void populateListView() {
        //LinearLayout list = (LinearLayout) findViewById(R.id.listOlists);
       // list.removeAllViews();

        //TableLayout list = (TableLayout) findViewById(R.id.listOlists);
        //list.removeAllViews(); //clear it

        ListView list = (ListView)findViewById(R.id.listOlists);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View list_item = inflater.inflate(R.layout.list_helper, null, false);

        LinearLayout row = list_item.findViewById(R.id.existingList);
        //TextView name = (TextView)row.findViewById(R.id.nameOfList);
        //TextView delete = (TextView)row.findViewById(R.id.delete_button);

        ArrayList<String> theList = new ArrayList<>();

        try {
            Cursor cur = shoppyHelp.getRows(shoppy, "Lists", "listName");
            if (cur != null && cur.getCount() > 0) {
                for( int i = 0; i < cur.getCount(); i++ ) {
                   /* TableRow tr = new TableRow(this);


                    if(row.getParent()!= null) {
                       // ((ViewGroup) row.getParent()).removeView(row);
                    }

                    name = row.findViewById(R.id.nameOfList);
                    name.setText(obj.toListName(cur.getString(cur.getColumnIndex("listName"))));

                    tr.addView(name);  //add the views to the row
                    tr.addView(delete);  //add the views to the row
                    list.addView(tr); //add the row to list of lists
                    */
                   theList.add(cur.getString(cur.getColumnIndex("listName")));
                    cur.moveToNext();
                }
            }



          /*  Cursor cur = db.rawQuery("select listName from Lists", null);
            if (cur != null) {
                cur.moveToFirst();
            }

            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        String currentList = cur.getString(cur.getColumnIndex("listName"));
                        results.add(currentList);
                    } while (cur.moveToNext());
                }
            }
            ListView list = (ListView) findViewById(R.id.listOlists);
            list.setAdapter(new ArrayAdapter<String>(this, R.layout.list_helper));
        */
            cur.close();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_helper, R.id.nameOfList, theList);
            list.setAdapter(adapter);
        } catch ( Exception e) {
            Log.e("POPULATE ERROR", "Problem with populateListView");
            e.printStackTrace();
            Toast.makeText(this, "populate Error", Toast.LENGTH_LONG).show();
        }
    }

    private void checkNumLists(){
        Button add = (Button)findViewById(R.id.createList);
        int count = shoppyHelp.countLists();

        if (count < 10) {
            add.setClickable(true);
        } else {
            add.setClickable(false);
        }
        //return count;
    }

    public void back(View view) {
        Intent home = new Intent( getApplicationContext(), MainMenu.class );
        startActivity(home);
    }
}