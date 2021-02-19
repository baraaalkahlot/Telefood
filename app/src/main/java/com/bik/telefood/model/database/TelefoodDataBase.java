package com.bik.telefood.model.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.bik.telefood.model.entity.Autherntication.CategoryModel;
import com.bik.telefood.model.entity.general.UserModel;

@Database(entities = {UserModel.class, CategoryModel.class}, version = 1, exportSchema = false)
@TypeConverters({CategoriesTypeConverter.class})
public abstract class TelefoodDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "telefood_db";
    private static TelefoodDataBase sInstance;

    public static TelefoodDataBase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    TelefoodDataBase.class,
                    DATABASE_NAME
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return sInstance;
    }

    public abstract UserSectionDao userSectionDao();

    public abstract CategoriesSectionDao categoriesSectionDao();

}