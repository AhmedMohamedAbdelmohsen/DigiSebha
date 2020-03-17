package com.example.digisebhaa;

import android.app.Application;

import com.example.digisebhaa.pojo.SebhaModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SebhaViewModel extends AndroidViewModel {
    private SebhaRepository repository;
    private LiveData<List<SebhaModel>> getAllMorningDhikr;

    public SebhaViewModel(@NonNull Application application) {
        super(application);
        repository = new SebhaRepository(application);
        getAllMorningDhikr = repository.getGetAllMorningDhikr();
    }

    LiveData<List<SebhaModel>> GetAllMorningDhikr(){
        return getAllMorningDhikr;
    }

    public void insert(SebhaModel model){
        repository.insert(model);
    }
}
