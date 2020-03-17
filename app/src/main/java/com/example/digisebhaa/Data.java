package com.example.digisebhaa;

import com.example.digisebhaa.pojo.SebhaDao;
import com.example.digisebhaa.pojo.SebhaModel;

public class Data {
    public void getAllData(SebhaDao sebhaDao) {
        sebhaDao.insert(new SebhaModel("لا إله الا الله", "البخاري", "من قالها صباحا", 3, 0));
        sebhaDao.insert(new SebhaModel("سبحان الله", "البخاري", "من قالها صباحا", 3, 1));
        sebhaDao.insert(new SebhaModel("الحمد لله", "البخاري", "من قالها صباحا", 3, 2));

    }
}
