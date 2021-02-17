package com.bik.telefood.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bik.telefood.model.entity.general.UserModel;


@Dao
public interface UserSectionDao {
    @Query("SELECT * FROM user_section_table LIMIT 1")
    LiveData<UserModel> getUserModel();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMainSectionItems(UserModel userModel);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateMainSectionItems(UserModel userModel);

    @Query("DELETE FROM user_section_table")
    void deleteAll();
}
