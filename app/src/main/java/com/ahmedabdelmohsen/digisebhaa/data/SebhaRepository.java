package com.ahmedabdelmohsen.digisebhaa.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ahmedabdelmohsen.digisebhaa.pojo.SebhaModel;

import java.util.List;

public class SebhaRepository {
    private final SebhaDao sebhaDao;
    private final LiveData<List<SebhaModel>> getAllMorningDhikr;
    private final LiveData<List<SebhaModel>> getAllEveningDhikr;
    private final LiveData<List<SebhaModel>> getAllHadith;


    public SebhaRepository(Application application) {
        SebhaDataBase dataBase = SebhaDataBase.getDataBase(application);
        sebhaDao = dataBase.sebhaDao();
        getAllMorningDhikr = sebhaDao.getAllMorningDhikr(0);
        getAllEveningDhikr = sebhaDao.getAllEveningDhikr(1);
        getAllHadith = sebhaDao.getAllHadith(2);

    }

    public LiveData<List<SebhaModel>> getGetAllMorningDhikr() {
        return getAllMorningDhikr;
    }

    public LiveData<List<SebhaModel>> getGetAllEveningDhikr() {
        return getAllEveningDhikr;
    }

    public LiveData<List<SebhaModel>> getGetAllHadith() {
        return getAllHadith;
    }


    public void insert(SebhaModel model) {
        new insertAsyncTask(sebhaDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<SebhaModel, Void, Void> {
        private final SebhaDao sebhaDao;

        insertAsyncTask(SebhaDao sebhaDao) {
            this.sebhaDao = sebhaDao;
        }

        @Override
        protected Void doInBackground(SebhaModel... sebhaModels) {
            sebhaDao.insert(sebhaModels[0]);
            return null;
        }
    }
}
