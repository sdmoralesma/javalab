package com.smorales.javalab.middleware.boundary.rest;

public class Cursor {

    private String column;
    private String row;


    public Cursor() {
    }

    public Cursor(String empty) {
        super();
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }
}
