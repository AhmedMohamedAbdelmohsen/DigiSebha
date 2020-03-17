package com.example.digisebhaa.pojo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SebhaDao {
    @Insert
    void insert(SebhaModel model);

//    @Update
//    void update(SebhaModel model);

    @Query("DELETE FROM sebha_table")
    void deleteALL();

    @Query("SELECT * FROM sebha_table ORDER BY text ASC")
    LiveData<List<SebhaModel>> getAllMorningDhikr();

//    @Query("SELECT * FROM sebha_table WHERE type IN (:x)")
//    LiveData<List<SebhaModel>> getAllMorningDhikr(int x);




}
