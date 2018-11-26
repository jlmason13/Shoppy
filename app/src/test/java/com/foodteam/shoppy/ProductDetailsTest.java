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
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

//Tell Mockito to validate if usage of framework is right.
@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsTest {
    @Mock
    Context mcontext;

    @Mock
    private ProductDetails proDet = Mockito.spy(new ProductDetails());

    @Test
    public void getDetails() {
        assertNotNull(proDet.shoppyDB.getPath());
    }

    @Test
    public void onCreate() {
        String name = "";

    }
}