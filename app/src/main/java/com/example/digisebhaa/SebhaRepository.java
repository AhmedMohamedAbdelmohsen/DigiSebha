package com.example.digisebhaa;

import android.app.Application;
import android.os.AsyncTask;

import com.example.digisebhaa.pojo.SebhaModel;
import com.example.digisebhaa.pojo.SebhaDao;

import java.util.List;

import androidx.lifecycle.LiveData;

public class SebhaRepository {
    private SebhaDao sebhaDao;
    private LiveData<List<SebhaModel>> getAllMorningDhikr;

    SebhaRepository(Application application) {
        SebhaDataBase dataBase = SebhaDataBase.getDataBase(application);
        sebhaDao = dataBase.sebhaDao();
        getAllMorningDhikr = sebhaDao.getAllMorningDhikr();
    }

    LiveData<List<SebhaModel>> getGetAllMorningDhikr() {
        return getAllMorningDhikr;
    }

    void insert(SebhaModel model) {
        new insertAsyncTask(sebhaDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<SebhaModel, Void, Void> {
        private SebhaDao sebhaDao;

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
