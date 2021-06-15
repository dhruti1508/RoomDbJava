package com.demo.roomdbjava;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

//add database entities
@Database(entities = {MainData.class},version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase
{
    //create database instance
    private static RoomDB database;

    //define databasse name
    private static String DATABASE_NAME = "database";

    public synchronized static RoomDB getInstance(Context context)
    {
        //check condition
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(),RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    //create Dao
    public abstract MainDao mainDao();
}
