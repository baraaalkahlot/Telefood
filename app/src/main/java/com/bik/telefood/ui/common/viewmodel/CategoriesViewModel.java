package com.bik.telefood.ui.common.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bik.telefood.model.entity.Autherntication.CategoryModel;
import com.bik.telefood.ui.common.Repository.CategoriesRepository;

import java.util.List;

public class CategoriesViewModel extends AndroidViewModel {

    private CategoriesRepository categoriesRepository;
    private LiveData<List<CategoryModel>> categoriesListLiveData;

    public CategoriesViewModel(@NonNull Application application) {
        super(application);
        categoriesRepository = CategoriesRepository.getInstance(application);
        categoriesListLiveData = categoriesRepository.getUserModel();
    }

    public LiveData<List<CategoryModel>> getCategoriesListLiveData() {
        return categoriesListLiveData;
    }

    public void updateCategoriesList(Context context, FragmentManager fragmentManager) {
        categoriesRepository.updateUserModel(context, fragmentManager);
    }


    public void deleteCategoriesTable() {
        categoriesRepository.deleteCategoriesTable();
    }
}
