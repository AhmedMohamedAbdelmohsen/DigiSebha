package com.ahmedabdelmohsen.digisebhaa;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ahmedabdelmohsen.digisebhaa.pojo.SebhaModel;

import java.util.List;

public class SebhaViewModel extends AndroidViewModel {
    private SebhaRepository repository;
    private LiveData<List<SebhaModel>> getAllMorningDhikr;
    private LiveData<List<SebhaModel>> getAllEveningDhikr;
    private LiveData<List<SebhaModel>> getAllHadith;


    public SebhaViewModel(@NonNull Application application) {
        super(application);
        repository = new SebhaRepository(application);
        getAllMorningDhikr = repository.getGetAllMorningDhikr();
        getAllEveningDhikr = repository.getGetAllEveningDhikr();
        getAllHadith = repository.getGetAllHadith();

    }

    LiveData<List<SebhaModel>> GetAllMorningDhikr() {
        return getAllMorningDhikr;
    }

    LiveData<List<SebhaModel>> GetAllEveningDhikr() {
        return getAllEveningDhikr;
    }

    LiveData<List<SebhaModel>> GetAllHadith() {
        return getAllHadith;
    }


    public void insert(SebhaModel model) {
        repository.insert(model);
    }
}
