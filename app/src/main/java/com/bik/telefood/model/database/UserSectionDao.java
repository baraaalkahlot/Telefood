package com.bik.telefood.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bik.telefood.model.entity.general.UserModel;

@Dao
public interface UserSectionDao {
    @Query("SELECT * FROM user_section_table LIMIT 1")
    LiveData<UserModel> getUserModel();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUserSectionItems(UserModel userModel);

    @Query("UPDATE user_section_table SET avatar = :m_avatar,name = :m_name,phone = :m_phone,governorateId = :m_governorate_id,cityId = :m_city_id,governorate = :m_governorate,city = :m_city,choosedPlanName = :m_choosedPlanName,remainingDaysInPlan = :m_remainingDaysInPlan")
    void updateUserSectionItems(String m_avatar, String m_name, String m_phone, String m_governorate_id, String m_city_id, String m_governorate, String m_city, String m_choosedPlanName, String m_remainingDaysInPlan);

    @Query("DELETE FROM user_section_table")
    void deleteAll();
}
