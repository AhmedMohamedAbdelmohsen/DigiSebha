package com.ahmedabdelmohsen.digisebhaa.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ahmedabdelmohsen.digisebhaa.pojo.SebhaModel;

import java.util.List;

@Dao
public interface SebhaDao {
    @Insert
    void insert(SebhaModel model);

//    @Update
//    void update(SebhaModel model);

    @Query("DELETE FROM sebha_table")
    void deleteALL();

    @Query("SELECT * FROM sebha_table WHERE type IN (:x) ORDER BY periority ASC")
    LiveData<List<SebhaModel>> getAllMorningDhikr(int x);

    @Query("SELECT * FROM sebha_table WHERE type IN (:x) ORDER BY periority ASC")
    LiveData<List<SebhaModel>> getAllEveningDhikr(int x);

    @Query("SELECT * FROM sebha_table WHERE type IN (:x) ORDER BY periority ASC")
    LiveData<List<SebhaModel>> getAllHadith(int x);

//    @Query("SELECT * FROM sebha_table WHERE type IN (:x)")
//    LiveData<List<SebhaModel>> getAllMorningDhikr(int x);


}
