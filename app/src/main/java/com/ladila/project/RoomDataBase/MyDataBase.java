package com.ladila.project.RoomDataBase;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.RoomDatabase;

import com.ladila.project.Pojo.ClsApiData;
import com.ladila.project.Pojo.DBPojo.DBResult;

@Database(entities = DBResult.class,version = 1, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    public abstract MyDao myDao();

}
