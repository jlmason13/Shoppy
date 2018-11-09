package com.foodteam.shoppy;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import com.foodteam.shoppy.MainMenu;

import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

//Tell Mockito to validate if usage of framework is right.
@RunWith(MockitoJUnitRunner.class)
public class MainMenuTest {
    @Mock
    Context mcontext; String test = "test";

    @Test
    public void hellotest(){
        //Returns 'test' when (blahblah) is called
        when(mcontext.getPackageName()).thenReturn(test);
        //Create object of MainMenu with mock content
        MainMenu mainMenu = new MainMenu(mcontext);
        //Store return value of hello() into r.
        String r = mainMenu.hello();
        //Asserts that r must be the package name, as hello() dictates.
        assertEquals(r, mcontext.getPackageName());
    }

    //@Mock
    //private MainMenu mainMenu = Mockito.spy(new MainMenu());

    @Test
    public void testAlwaysTrueTest(){
        assertEquals("Hello Gradle", "Hello Gradle");
    }

    @Test
    public void createDatabaseTest() {
        //Create object of MainMenu with mock context
        //MainMenu mainMenu = new MainMenu(mcontext);
        //Check if there was a database file created by the name of "shoppyDB".
        //assertEquals("Database Created?", "shoppyDB.db", mainMenu.getApplicationContext().getDatabasePath("shoppyDB.db"));
        //assertNotNull("Database Created?", mainMenu.shoppyDB);
        //assertNotNull("Database Created?", mainMenu.getDatabasePath("shoppyDB"));

    }

    @Test
    public void onCreateButtonLists() {
        //assertNotNull("Button 'Lists' Exists?", mainMenu.findViewById(R.id.buttLists));
        //assertTrue(mainMenu.findViewById(R.id.buttLists).hasOnClickListeners());
    }

    @Test
    public void onCreateButtonMasterList() {
        //assertNotNull("Button 'MasterList' Exists?", mainMenu.findViewById(R.id.buttMasterList));
    }

    @Test
    public void onCreateButtonSettings() {
        //assertNotNull("Button 'Lists' Exists?", mainMenu.findViewById(R.id.buttSettings));
    }

}