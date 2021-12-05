package com.example.ncrexample.model;

public class Bluetooth {

    int id;
    String lastDate;

    public Bluetooth() {
    }

    public Bluetooth(String lastDate) {
        this.lastDate = lastDate;
    }

    public Bluetooth(int id, String lastDate) {
        this.id = id;
        this.lastDate = lastDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }


    @Override
    public String toString() {
        return lastDate;
    }

}
