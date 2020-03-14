package com.example.digisebhaa;

import android.app.Application;
import android.os.AsyncTask;

import com.example.digisebhaa.pojo.SebhaModel;
import com.example.digisebhaa.pojo.SebhaDao;

import java.util.List;

import androidx.lifecycle.LiveData;

public class SebhaRepository {
    private SebhaDao sebhaDao;
    private LiveData<List<SebhaModel>> allMorningDhikr;
    private LiveData<List<SebhaModel>> allEveningDhikr;
    private LiveData<List<SebhaModel>> allHadith;

    public SebhaRepository(Application application) {
        SebhaDataBase sebhaDataBase = SebhaDataBase.getInstance(application);
        sebhaDao = sebhaDataBase.sebhaDao();
        allMorningDhikr = sebhaDao.getAllMorningDhikr();
        allEveningDhikr = sebhaDao.getAllEveningDhikr();
        allHadith = sebhaDao.getAllHadith();
    }

    public void insert(SebhaModel model) {
        new InsertSebhaAsyncTask(sebhaDao).execute(model);
    }

    public void update(SebhaModel model) {
        new UpdateSebhaAsyncTask(sebhaDao).execute(model);
    }

    public void delete(SebhaModel model) {
        new DeleteSebhaAsyncTask(sebhaDao).execute(model);
    }

    public LiveData<List<SebhaModel>> getAllMorningDhikr() {
        return allMorningDhikr;
    }

    public LiveData<List<SebhaModel>> getAllEveningDhikr() {
        return allEveningDhikr;
    }

    public LiveData<List<SebhaModel>> getAllHadith() {
        return allHadith;
    }

    public static class InsertSebhaAsyncTask extends AsyncTask<SebhaModel, Void, Void> {

        private SebhaDao sebhaDao;

        private InsertSebhaAsyncTask(SebhaDao sebhaDao) {
            this.sebhaDao = sebhaDao;
        }

        @Override
        protected Void doInBackground(SebhaModel... sebhaModels) {
            sebhaDao.insert(sebhaModels[0]);
            return null;
        }
    }

    public static class UpdateSebhaAsyncTask extends AsyncTask<SebhaModel, Void, Void> {

        private SebhaDao sebhaDao;

        private UpdateSebhaAsyncTask(SebhaDao sebhaDao) {
            this.sebhaDao = sebhaDao;
        }

        @Override
        protected Void doInBackground(SebhaModel... sebhaModels) {
            sebhaDao.update(sebhaModels[0]);
            return null;
        }
    }

    public static class DeleteSebhaAsyncTask extends AsyncTask<SebhaModel, Void, Void> {
        private SebhaDao sebhaDao;

        private DeleteSebhaAsyncTask(SebhaDao sebhaDao) {
            this.sebhaDao = sebhaDao;
        }

        @Override
        protected Void doInBackground(SebhaModel... sebhaModels) {
            sebhaDao.delete(sebhaModels[0]);
            return null;
        }
    }

}
