package com.example.digisebhaa.pojo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SebhaDao {
    @Insert
    void insert(SebhaModel model);

//    @Update
//    void update(SebhaModel model);

    @Query("DELETE FROM sebha_table")
    void deleteALL();

    @Query("SELECT * FROM sebha_table WHERE type IN (:x) ORDER BY text ASC")
    LiveData<List<SebhaModel>> getAllMorningDhikr(int x);

    @Query("SELECT * FROM sebha_table WHERE type IN (:x) ORDER BY text ASC")
    LiveData<List<SebhaModel>> getAllEveningDhikr(int x);

    @Query("SELECT * FROM sebha_table WHERE type IN (:x) ORDER BY text ASC")
    LiveData<List<SebhaModel>> getAllHadith(int x);

//    @Query("SELECT * FROM sebha_table WHERE type IN (:x)")
//    LiveData<List<SebhaModel>> getAllMorningDhikr(int x);


}
