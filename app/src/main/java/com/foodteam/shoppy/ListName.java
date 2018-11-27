package com.foodteam.shoppy;

public class ListName {
    private String name;

    public void setName( String s ) {
        name = s;
    }

    public String getName() {
        return name;
    }

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
}
