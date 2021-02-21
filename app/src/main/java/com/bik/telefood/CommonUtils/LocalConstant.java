package com.bik.telefood.CommonUtils;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bik.telefood.model.entity.Autherntication.CategoryModel;
import com.bik.telefood.ui.common.viewmodel.CategoriesViewModel;

public class LocalConstant {

    private final ViewModelStoreOwner viewModelStoreOwner;
    private final LifecycleOwner lifecycleOwner;
    private final Context context;
    private MutableLiveData<String> stringMutableLiveData;

    public LocalConstant(LifecycleOwner lifecycleOwner, ViewModelStoreOwner viewModelStoreOwner, Context context) {
        this.viewModelStoreOwner = viewModelStoreOwner;
        this.lifecycleOwner = lifecycleOwner;
        this.context = context;
    }

    public LiveData<String> getCategoryNameById(int id) {
        stringMutableLiveData = new MutableLiveData<>();
        CategoriesViewModel categoriesViewModel = new ViewModelProvider(viewModelStoreOwner).get(CategoriesViewModel.class);
        categoriesViewModel.getCategoriesListLiveData().observe(lifecycleOwner, c -> {
            for (CategoryModel categoryModel : c) {
                if (categoryModel.getId() == id) {
                    stringMutableLiveData.setValue(categoryModel.getTitle());
                    break;
                }
            }
        });
        return stringMutableLiveData;
    }
}
