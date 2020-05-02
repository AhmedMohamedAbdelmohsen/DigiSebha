package com.ahmedabdelmohsen.digisebhaa.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ahmedabdelmohsen.digisebhaa.pojo.SebhaModel;

@Database(entities = {SebhaModel.class}, version = 8, exportSchema = false)
public abstract class SebhaDataBase extends RoomDatabase {
    public static SebhaDataBase instance;

    public abstract SebhaDao sebhaDao();

    static SebhaDataBase getDataBase(final Context context) {
        if (instance == null) {
            synchronized (SebhaDataBase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext()
                            , SebhaDataBase.class, "sebha_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new populateDataBaseAsync(instance).execute();
        }
    };

    private static class populateDataBaseAsync extends AsyncTask<Void, Void, Void> {
        private SebhaDao sebhaDao;

        populateDataBaseAsync(SebhaDataBase dataBase) {
            sebhaDao = dataBase.sebhaDao();
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            sebhaDao.deleteALL();
            Data data = new Data();
            data.getAllData(sebhaDao);
            return null;

        }
    }

}
