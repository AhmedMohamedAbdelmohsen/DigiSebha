package com.example.digisebhaa;

import android.content.Context;
import android.os.AsyncTask;

import com.example.digisebhaa.pojo.SebhaModel;
import com.example.digisebhaa.pojo.SebhaDao;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {SebhaModel.class}, version = 1)
public abstract class SebhaDataBase extends RoomDatabase {
    public static SebhaDataBase instance;

    public abstract SebhaDao sebhaDao();

    public static synchronized SebhaDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SebhaDataBase.class, "sebha_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new SebhaDataBase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();

        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private SebhaDao sebhaDao;

        private PopulateDBAsyncTask(SebhaDataBase dataBase) {
            sebhaDao = dataBase.sebhaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            sebhaDao.insert(new SebhaModel("سبحان الله", "البخاري",
                    "من قالها حين يصبح", 3, 0));
            sebhaDao.insert(new SebhaModel("سبحان الله", "البخاري",
                    "من قالها حين يصبح", 3, 0));
            sebhaDao.insert(new SebhaModel("سبحان الله", "البخاري",
                    "من قالها حين يمسي", 3, 1));
            sebhaDao.insert(new SebhaModel("سبحان الله", "البخاري",
                    "من قالها حين يمسي", 3, 1));
            sebhaDao.insert(new SebhaModel("الكلمة الطيبة صدقة", "سلم", "", 0, 2));
            sebhaDao.insert(new SebhaModel("الكلمة الطيبة صدقة", "مسلم", "", 0, 2));
            return null;
        }
    }
}
