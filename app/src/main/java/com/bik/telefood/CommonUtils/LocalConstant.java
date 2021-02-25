package com.bik.telefood.CommonUtils;

import android.content.Context;

import androidx.fragment.app.FragmentManager;
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

    public LiveData<String> getCategoryNameById(int id, FragmentManager fragmentManager) {
        stringMutableLiveData = new MutableLiveData<>();
        CategoriesViewModel categoriesViewModel = new ViewModelProvider(viewModelStoreOwner).get(CategoriesViewModel.class);
        categoriesViewModel.getCategoriesListLiveData().observe(lifecycleOwner, c -> {
            if (c == null || c.isEmpty())
                categoriesViewModel.updateCategoriesList(context, fragmentManager);
            else {
                for (CategoryModel categoryModel : c) {
                    if (categoryModel.getId() == id) {
                        stringMutableLiveData.setValue(categoryModel.getTitle());
                        break;
                    }
                }
            }
        });
        return stringMutableLiveData;
    }
}
