package com.bik.telefood.ui.common.Repository;

import android.app.Application;
import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;

import com.bik.telefood.model.database.CategoriesSectionDao;
import com.bik.telefood.model.database.TelefoodDataBase;
import com.bik.telefood.model.entity.Autherntication.CategoryModel;
import com.bik.telefood.model.entity.Autherntication.CategoryResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import java.util.List;

import retrofit2.Response;

public class CategoriesRepository {
    private static final String TAG = CategoriesRepository.class.getName();
    private static CategoriesRepository sInstance;
    private final NetworkUtils networkUtils;
    private final CategoriesSectionDao categoriesSectionDao;

    private CategoriesRepository(Application application) {
        networkUtils = NetworkUtils.getInstance(application);
        categoriesSectionDao = TelefoodDataBase.getInstance(application).categoriesSectionDao();
    }

    public static CategoriesRepository getInstance(Application application) {
        if (sInstance == null) {
            sInstance = new CategoriesRepository(application);
        }
        return sInstance;
    }

    public LiveData<List<CategoryModel>> getUserModel() {
        return categoriesSectionDao.getCategoryModelList();
    }

    public void updateUserModel(Context context, FragmentManager fragmentManager) {
        deleteCategoriesTable();
        networkUtils.getApiInterface().getCategoriesList().enqueue(new BaseCallBack<CategoryResponse>(context, fragmentManager, false) {
            @Override
            protected void onFinishWithSuccess(CategoryResponse result, Response<CategoryResponse> response) {
                CategoryResponse categoryResponse = response.body();
                if (categoryResponse != null && categoryResponse.getCategories() != null) {
                    categoriesSectionDao.addCategoriesMainSectionItems(categoryResponse.getCategories());
                }
            }
        });
    }

    public void deleteCategoriesTable() {
        categoriesSectionDao.deleteAll();
    }
}
