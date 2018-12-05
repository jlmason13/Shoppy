package com.foodteam.shoppy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class List extends AppCompatActivity {
    SQLiteDatabase shoppy;
    DBHandler shoppyHelp;
    String tablename = "dummyList";
    ListName obj = new ListName();
    String productname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //get name of table from previous page
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                //tablename = null;
            } else {
                tablename = extras.getString("nameOfTable");
            }
        }

        //set title of page
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(obj.toListName(tablename));

        try {
            //connect to db
            shoppy = this.openOrCreateDatabase("shoppyDB", MODE_PRIVATE, null );
            shoppyHelp = DBHandler.getInstance(getApplicationContext());


        } catch (Exception e) {
            Log.e("ERROR GETTING DATABASE", "Problem getting database");
            // Toast.makeText(this, "uh oh!", Toast.LENGTH_LONG).show();
        }
        ColorChanges obj = new ColorChanges();
        View view = this.getWindow().getDecorView();
        obj.setWindowCOlor(shoppy, shoppyHelp, view, getWindow());

        setSuggestion();
        populateListView();
    }

    public void deleteProduct(View v) {
        //get the name of the current product
        TextView  text = (TextView) ((LinearLayout)v.getParent()).findViewById(R.id.productName);
        String prod = text.getText().toString();
        prod = obj.toTableName(prod);

        try {
            // remove item from list in shoppydb
            shoppyHelp.deleteProd(shoppy, tablename, prod);
            Toast.makeText(this, "list item "+ obj.toListName(prod) +" removed", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("DATABASE ERROR", "Problem removing item from list in database");
            Toast.makeText(this, "ERROR REMOVING ITEM", Toast.LENGTH_LONG).show();
        }

        //redraw the list o' lists
        populateListView();
    }

    //onclick for product details button
    public void prodDetails(View v) {
        //get the name of the current product
        TextView  text = (TextView) ((LinearLayout)v.getParent()).findViewById(R.id.productName);
        String prod = text.getText().toString();
        prod = obj.toTableName(prod);

        boolean exists = shoppyHelp.findTable(shoppy, prod );
        if(exists) {
            Intent details = new Intent(getApplicationContext(), ProductDetails.class);
            details.putExtra("THEPRODUCTNAME", prod);
            startActivity(details);
        } else {
            Toast.makeText(this, "You have no data for "+obj.toListName(prod), Toast.LENGTH_LONG).show();
        }
    }

    //onclick for adding products to list
    public void addProduct(View v) {
        EditText newItem = (EditText)findViewById(R.id.newProdName);
        //dont do anything if the text field is empty
        if (!TextUtils.isEmpty(newItem.getText().toString())) {
            productname = newItem.getText().toString();
            productname = productname.trim();

            boolean exists = false;
            //Make sure no existing item shares the name
            try {
                Cursor cur = shoppyHelp.getRows(shoppy, tablename, "product");
                if (cur != null && (cur.getCount() > 0)) {
                    for( int i = 0; i < cur.getCount(); i++ ) {
                        if (cur.getString(cur.getColumnIndex("product")).equals(tablename)) {
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
                    shoppyHelp.addProduct(shoppy, tablename, obj.toTableName(productname));
                } catch (Exception e) {
                    Log.e("DATABASE ERROR", "Problem inserting new item into database");
                    Toast.makeText(this, "ERROR ADDING ITEM", Toast.LENGTH_LONG).show();
                }
                //"re-draw" the list
                populateListView();
            } else {
                Toast.makeText(this, productname + " already exists", Toast.LENGTH_LONG).show();
            }

            //empty the edittext
            newItem.setText("");
        } else {
            Toast.makeText(this, "Type in the box please", Toast.LENGTH_LONG).show();
        }
    }

    public void back(View v) {
        finish();
    }

    //set inCart depending on if checkbox is clicked
    public void cart(View v) {
        //get the name of the current product
        TextView  text = (TextView) ((LinearLayout)v.getParent()).findViewById(R.id.productName);
        String prod = text.getText().toString();
        prod = obj.toTableName(prod);

        int cart;

        CheckBox check = (CheckBox)v;
        if(check.isChecked()){
            cart = 1;
            Toast.makeText(this, prod+" in cart", Toast.LENGTH_LONG).show();
        } else {
            cart = 0;
        }

        //set inCart for specific product to be int cart
        try {
            shoppyHelp.cart(shoppy, tablename, prod, cart);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DATABASE ERROR", "Problem changing inCart value");
            Toast.makeText(this, "ERROR WITH CART ", Toast.LENGTH_LONG).show();
        }
    }

    public void doneShopping(View view) {
        Intent details = new Intent( getApplicationContext(), EnterDetails.class );
        details.putExtra("nameOfTable", tablename);
        startActivity(details);
    }

    //public for testing
    public void populateListView() {
        ListView list = (ListView)findViewById(R.id.list);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View list_item = inflater.inflate(R.layout.list_item, null, false);
        LinearLayout row = list_item.findViewById(R.id.productRow);
        ArrayList<String> theList = new ArrayList<>();

        try {
            Cursor cur = shoppyHelp.getRows(shoppy, tablename, "product");
            if (cur != null && cur.getCount() > 0) {
                for( int i = 0; i < cur.getCount(); i++ ) {
                    theList.add(obj.toListName(cur.getString(cur.getColumnIndex("product"))));
                    cur.moveToNext();
                }
            }
            cur.close();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.productName, theList);
            list.setAdapter(adapter);

        } catch ( Exception e ) {
            Log.e("POPULATE ERROR", "Problem with populateListView");
            e.printStackTrace();
            Toast.makeText(this, "populate Error", Toast.LENGTH_LONG).show();
        }
    }

    public void addSuggestion(View v) {
        //when click on suggestion
        Button b = findViewById(R.id.suggestProd);
        String prod = obj.toTableName(b.getText().toString());
        prod = prod.trim();

        if (!prod.equals("no_suggestions") && !prod.equals("no suggestions")) {
            //need to add to the list
            shoppyHelp.addProduct(shoppy, tablename, prod);

            //re draw list
            populateListView();

            //set new suggestion
            setSuggestion();
        }
    }

    public void setSuggestion() {
        Button b = findViewById(R.id.suggestProd);
        try {
            b.setText(obj.toListName(shoppyHelp.getSuggestion(shoppy, tablename)));
        } catch (Exception e) {
            Log.e("Suggestion ERROR", "Problem with setSuggestion");
            e.printStackTrace();
        }
    }

    public void newSug(View v) {
        setSuggestion();
    }

    public void testpopulateListView(String s) {
        tablename = s;
        populateListView();
    }

    public void testdelete(String s) {
        ImageButton b = findViewById(R.id.deleteProduct);
        TextView  text = (TextView) ((LinearLayout)b.getParent()).findViewById(R.id.productName);
        text.setText(s);
        deleteProduct(b);
    }
}