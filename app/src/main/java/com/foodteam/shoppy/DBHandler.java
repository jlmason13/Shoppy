package com.foodteam.shoppy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DBHandler extends SQLiteOpenHelper {
    private static DBHandler sInstance;

    private static final String DATABASE_NAME = "shoppyDB";
    private static final String TABLE_MASTERLIST = "MasterList";
    private static final String TABLE_LISTS = "Lists";
    private static final String CREATE_TABLE_MASTERLIST = "CREATE TABLE IF NOT EXISTS " + TABLE_MASTERLIST + "(product VARCHAR primary key, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), totalSpent float(9,2));";
    private static final String CREATE_TABLE_LISTS = "CREATE TABLE IF NOT EXISTS " + TABLE_LISTS + "(listName VARCHAR primary key);";
    private static final String CREATE_TABLE_DUMMYLIST = "CREATE TABLE IF NOT EXISTS dummyList(product VARCHAR unique, inCart int);"; //Used for testing
    private static final String CREATE_TABLE_DUMMYPRODUCT = "CREATE TABLE IF NOT EXISTS dummyProduct(brand VARCHAR, size integer, frequency integer, avgPrice float(9,2), lowestPrice float (9,2), highestPrice float(9,2), store VARCHAR, totalSpent float(9,2),  primary key (brand, size)); ";
    private static final String CREATE_TABLE_SETTINGS = "CREATE TABLE IF NOT EXISTS settings(color integer default 0);";
    private static final int DATABASE_VERSION = 1;

    //getInstance() ensures only one DBHandler will exist at any time.
    //If sInstance not initialized, it will be created. If one created,
    //It will be returned. Call this by DBHandler.getInstance(context)
    //and NOT ... = new DBHandler(context).
    public static synchronized DBHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DBHandler(context.getApplicationContext());
        }
        return sInstance;
    }
    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create two tables, MasterList & Lists
        db.execSQL(CREATE_TABLE_MASTERLIST);
        db.execSQL(CREATE_TABLE_LISTS);
        db.execSQL(CREATE_TABLE_DUMMYLIST);
        db.execSQL(CREATE_TABLE_DUMMYPRODUCT);
        db.execSQL(CREATE_TABLE_SETTINGS);

        //necessary for testing
        addProduct(db, "dummyList", "dummyProduct");

        //ContentValues values = new ContentValues();
        //values.put("color", 0);
        //db.insert("settings", null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTERLIST + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTS + ";");
    }

    public void closeDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()){
            db.close();
        }
    }
//---------------------------------------------
    //change value of color
    public void setColor(SQLiteDatabase db, int color) {
        ContentValues values = new ContentValues();
        values.put("color", color);
        db.update("settings", values, "color != ?", new String[] {Integer.toString(color)}  );
    }

    public int getColor(SQLiteDatabase db) {
        Cursor c = db.rawQuery("select color from settings", null);
        c.moveToFirst();
        int color = c.getInt(c.getColumnIndex("color"));
        c.close();
        return color;
    }
/*
    //This creates a whole new row for the product. TESTED
    public void createItemMasterList(String p, int f, float a, float l, float t){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("product", p);
        values.put("frequency", f);
        values.put("avgPrice", a);
        values.put("lowestPrice", l);
        values.put("totalSpent", t);
        //insert row
        db.insert("MasterList", null, values);
    }
*/
    //This creates a whole new row for the List.
    public void createItemLists(SQLiteDatabase db, String p){
        ContentValues values = new ContentValues();
        values.put("listName", p);
        //insert row
        db.insert("Lists", null, values);
    }
/*
    //Update "product" in MasterList. UNTESTED
    public void updateProductMasterList(String p){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("product", p);
        db.update("MasterList", values, "product = ?", new String[] {String.valueOf(p)}  );
    }

    //Delete "product" in MasterList. TESTED
    public void deleteProductMasterList(String p){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("MasterList", "product = ?", new String[] {String.valueOf(p)} );
    }

    //Tells you how many items are in MasterList. TESTED
    public int countMasterList(){
        String count = " SELECT * FROM " + TABLE_MASTERLIST;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(count, null);
        int n = c.getCount();
        c.close();
        return n;
    }

    //Tells you how many items are in Lists.
    public int countLists(){
        String count = " SELECT * FROM " + TABLE_LISTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(count, null);
        int n = c.getCount();
        c.close();
        return n;
    }
*/
    //----- stuff for list/s -----

    //get suggestions
    public String getSuggestion(SQLiteDatabase db, String tablename) {
        //String query = "SELECT product FROM Masterlist WHERE NOT EXISTS (SELECT product from " + tablename + " ) ORDER BY RANDOM() /*LIMIT 1*/;";//"select product, MAX(frequency) from MasterList;";
        String query = "SELECT MasterList.product as prod FROM Masterlist LEFT JOIN " + tablename + " ON MasterList.product = "+ tablename + ".product WHERE " + tablename + ".product is null ORDER BY RANDOM();";
        Cursor cur = db.rawQuery(query, null);
        if (cur.getCount() > 0) {
            if (cur != null) {
                cur.moveToFirst();
            }
            if (cur.getString(cur.getColumnIndex("prod")) != null) {
                return cur.getString(cur.getColumnIndex("prod"));
            }
        }
        cur.close();
        return "no suggestions";
    }

    //creates a list table with the passed in name
    public void createListTable(SQLiteDatabase db, String listname) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + listname + "(/*id INTEGER PRIMARY KEY, */product VARCHAR unique, inCart int);");
    }

    //insert row into list table
    public void addProduct(SQLiteDatabase db, String listname, String product) {
        ContentValues values = new ContentValues();
        values.put("product", product);
        db.insert(listname, null, values);
    }

    //put product in cart (edit product row in list)
    public void cart(SQLiteDatabase db, String listname, String product, int inCart) {
      //needs to change the value of inCart int listname where product = product;
        ContentValues values = new ContentValues();
        values.put("inCart", inCart);

        db.update(listname, values, "product = ?", new String[] {product} );
    }

    //remove product from list
    public void deleteProd(SQLiteDatabase db, String listname, String product) {
        db.delete(listname, "product = ?", new String[] {String.valueOf(product)} );
    }

    //remove list table and removes that row from lists
    public void removeListTable(SQLiteDatabase db, String listname) {
        db.execSQL("DROP TABLE IF EXISTS " + listname + ";");
        db.delete("Lists", "listName = ?", new String[] {String.valueOf(listname)} );
    }

    //retrieve data from table
    public Cursor getRows(SQLiteDatabase db, String table, String column) {
        //used for list and lists, column will either be product or
        //use in populateListView function

        String query = "select " + column + " from " + table;
        Cursor cur = db.rawQuery(query, null);
        if (cur != null) {
            cur.moveToFirst();
        }
        return cur;
    }

    public boolean findTable(SQLiteDatabase db, String tablename) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tablename+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

}
