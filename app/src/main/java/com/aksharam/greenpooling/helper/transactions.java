package com.aksharam.greenpooling.helper;

public class transactions {
    private String method, amount, year;

    public transactions() {
    }

    public transactions(String method, String amount, String year) {
        this.method = method;
        this.amount = amount;
        this.year = year;
    }

    public String getmethod() {
        return method;
    }

    public void setmethod(String name) {
        this.method = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getamount() {
        return amount;
    }

    public void setamount(String amount) {
        this.amount = amount;
    }
}