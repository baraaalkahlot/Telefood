package com.bik.telefood.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bik.telefood.model.entity.Autherntication.CategoryModel;

import java.util.List;

@Dao
public interface CategoriesSectionDao {
    @Query("SELECT * FROM category_section_table")
    LiveData<List<CategoryModel>> getCategoryModelList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategoriesMainSectionItems(List<CategoryModel> categoryModelList);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateCategoriesSectionItems(List<CategoryModel> categoryModelList);

    @Query("DELETE FROM category_section_table")
    void deleteAll();
}
