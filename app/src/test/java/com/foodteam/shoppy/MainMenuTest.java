package com.foodteam.shoppy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

//Tell Mockito to validate if usage of framework is right.
@RunWith(MockitoJUnitRunner.class)
public class MainMenuTest {
    @Mock
    Context mcontext;
    String test = "test";
    //SQLiteDatabase shoppyDB;

    @Test
    public void hello(){
        //Returns 'test' when (blahblah) is called
        when(mcontext.getPackageName()).thenReturn(test);
        //Create object of MainMenu with mock content
        MainMenu mainMenu = new MainMenu(/*mcontext*/);
        //Store return value of hello() into r.
        String r = mainMenu.hello();
        //Asserts that r must be the package name, as hello() dictates.
        assertEquals(r, mcontext.getPackageName());
    }

    @Mock //Get an active & real version of mainMenu to be used.
    private MainMenu mainMenu = Mockito.spy(new MainMenu());
    //This must be done because one cannot simply call
    //MainMenu m = new MainMenu(); and expect it to work.
    //Android Studio doesn't play by normal JUnit rules.

    @Test
    public void testAlwaysTrueTest(){
        assertEquals("Hello Gradle", "Hello Gradle");
    }

    @Test
    public void createDatabase(){
        MainMenu mainMenu = new MainMenu(/*mcontext*/);
        assertNotNull(mainMenu.shoppyDB.getPath());
        mainMenu.shoppyDB.execSQL("SELECT * FROM MasterList");
    }

    @Test
    public void onCreate(){

    }

    @Test
    public void createDatabaseTest() {
        //Create object of MainMenu with mock context
        MainMenu mainMenu = new MainMenu(/*mcontext*/);
        //Check if there was a database file created by the name of "shoppyDB".
        //assertEquals("Database Created?", "shoppyDB.db", mainMenu.getApplicationContext().getDatabasePath("shoppyDB.db"));
        //assertNotNull("Database Created?", mainMenu.shoppyDB);
        assertNotNull("Database Created?", mainMenu.getDatabasePath("shoppyDB"));

    }

    @Test
    public void onCreateButtonLists() {
        assertNotNull("Button 'Lists' Exists?", mainMenu.findViewById(R.id.buttLists));
        //assertTrue(mainMenu.findViewById(R.id.buttLists).hasOnClickListeners());
    }

    @Test
    public void onCreateButtonMasterList() {
        assertNotNull("Button 'MasterList' Exists?", mainMenu.findViewById(R.id.buttMasterList));
    }

    @Test
    public void onCreateButtonSettings() {
        assertNotNull("Button 'Lists' Exists?", mainMenu.findViewById(R.id.buttSettings));
    }

}