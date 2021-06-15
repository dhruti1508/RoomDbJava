package com.demo.roomdbjava;


import android.arch.persistence.room.Entity;

import java.io.Serializable;

//define table name
@Entity(tableName = "table_name")
public class MainData implements Serializable
{
    //create id coloum
    private int ID;

    //create text coloum
    private String text;

    //generate Getter and Setter
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
