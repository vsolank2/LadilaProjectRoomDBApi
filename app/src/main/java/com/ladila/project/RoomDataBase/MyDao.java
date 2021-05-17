package com.ladila.project.RoomDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ladila.project.Pojo.DBPojo.DBResult;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    void addResult(DBResult dbResult);

    @Update
    void updateResult(DBResult dbResult);

    @Delete
    void deleteResult(DBResult dbResult);

    @Query("SELECT * FROM DBResult")
    List<DBResult> getAll();
}
