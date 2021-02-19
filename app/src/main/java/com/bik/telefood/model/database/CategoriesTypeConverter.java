package com.bik.telefood.model.database;

import androidx.room.TypeConverter;

import com.bik.telefood.model.entity.Autherntication.CategoryModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CategoriesTypeConverter {
    @TypeConverter
    public List<CategoryModel> fromJson(String jsonString) {
        Type type = new TypeToken<List<CategoryModel>>() {
        }.getType();
        return new Gson().fromJson(jsonString, type);
    }

    @TypeConverter
    public String toJson(List<CategoryModel> categoryModels) {
        Type type = new TypeToken<List<CategoryModel>>() {
        }.getType();
        return new Gson().toJson(categoryModels, type);
    }
}
