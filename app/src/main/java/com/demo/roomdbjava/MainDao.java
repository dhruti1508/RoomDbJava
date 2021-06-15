package com.demo.roomdbjava;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public class MainDao
{
    //insert query
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData)
    { }

    //delete query
    @Delete
    void delete(MainData mainData)
    { }

    //delete all query
    @Delete
    void reset(List<MainData> mainData)
    {}

    //update queryy
    @Query("UPDATE table_name SET text = :sText WHERE ID = :sID")
    void update(int sID,String sText)
    {}

    //get all data query
    @Query("select * from table_name")
    List<MainData> getAll() {
        return null;
    }
}
