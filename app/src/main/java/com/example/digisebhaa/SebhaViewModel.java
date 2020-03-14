package com.example.digisebhaa;

import android.app.Application;

import com.example.digisebhaa.pojo.SebhaModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SebhaViewModel extends AndroidViewModel {

    private SebhaRepository sebhaRepository;
    private LiveData<List<SebhaModel>> getAllMorningDhikr;
    private LiveData<List<SebhaModel>> getAllEveningDhikr;
    private LiveData<List<SebhaModel>> getAllHadith;

    public SebhaViewModel(@NonNull Application application) {
        super(application);
        sebhaRepository = new SebhaRepository(application);
        getAllMorningDhikr = sebhaRepository.getAllMorningDhikr();
        getAllEveningDhikr = sebhaRepository.getAllEveningDhikr();
        getAllHadith = sebhaRepository.getAllHadith();
    }

    public void insert(SebhaModel model) {
        sebhaRepository.insert(model);
    }

    public void update(SebhaModel model) {
        sebhaRepository.update(model);
    }

    public void delete(SebhaModel model) {
        sebhaRepository.delete(model);
    }

    public LiveData<List<SebhaModel>> getAllMorningDhikr() {
        return getAllMorningDhikr;
    }

    public LiveData<List<SebhaModel>> getAllEveningDhikr() {
        return getAllEveningDhikr;
    }

    public LiveData<List<SebhaModel>> getAllHadith() {
        return getAllHadith;
    }
}
