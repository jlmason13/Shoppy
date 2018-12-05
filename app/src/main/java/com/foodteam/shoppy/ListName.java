package com.foodteam.shoppy;

import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ListName {
    String[] badNames = new String[]{"and", "or", "insert", "new", "me", "update", "delete", "create", "table",
            "exists", "drop", "from", "if", "else", "select", "where", "sort", "by", "order", "null", "not",
            "primary", "unique", "default", "set", "into", "when", "values", "as", "join", "distinct", "aliases",
            "between", "except", "exists", "group", "having", "in", "insert", "intersect", "is", "limit",
            "subquery", "truncate", "union", "alter", "analyze", "attach", "database", "detach", "indexes", "literals",
            "system", "constraints", "views", "foreign"};

    public String toTableName(String s) {
        String newS = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                newS = newS + '_';
            } else {
                newS = newS + s.charAt(i);
            }
        }
        return newS;
    }

    public String toListName(String s) {
        String newS = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '_') {
                newS = newS + ' ';
            } else {
                newS = newS + s.charAt(i);
            }
        }
        return newS;
    }

    //returns true as long as no words in the given string are in the array above
    public Boolean validName(String name) {
        String[] nameAsWords = name.split(" ");
        for (int s = 0; s < nameAsWords.length; s++) {
            for (int bad = 0; bad < badNames.length; bad++) {
                if (nameAsWords[s].equals(badNames[bad])) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validateNumber(String s) {
        int decCount = 0;
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '.':
                    decCount++;
                    break;
                case '1':
                    break;
                case '2':
                    break;
                case '3':
                    break;
                case '4':
                    break;
                case '5':
                    break;
                case '6':
                    break;
                case '7':
                    break;
                case '8':
                    break;
                case '9':
                    break;
                case '0':
                    break;
                default:
                    //invalid input
                    return false;
            }
            if (decCount > 1) {
                return false;
            }
        }
        return true;
    }
}
