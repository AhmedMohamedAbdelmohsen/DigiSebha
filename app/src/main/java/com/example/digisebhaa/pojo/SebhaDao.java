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

    @Update
    void update(SebhaModel model);

    @Delete
    void delete(SebhaModel model);

    @Query("SELECT * FROM sebha_table WHERE type = 0")
    LiveData<List<SebhaModel>> getAllMorningDhikr();

    @Query("SELECT * FROM sebha_table WHERE type = 1")
    LiveData<List<SebhaModel>> getAllEveningDhikr();

    @Query("SELECT * FROM sebha_table WHERE type = 2")
    LiveData<List<SebhaModel>> getAllHadith();



}
