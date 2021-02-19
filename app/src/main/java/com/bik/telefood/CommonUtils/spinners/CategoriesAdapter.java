package com.bik.telefood.CommonUtils.spinners;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bik.telefood.R;
import com.bik.telefood.model.entity.Autherntication.CategoryModel;

import java.util.List;

public class CategoriesAdapter extends ArrayAdapter<CategoryModel> {

    private final Context mContext;
    private final List<CategoryModel> categoryModels;

    public CategoriesAdapter(@NonNull Context context, List<CategoryModel> categoryModels) {
        super(context, 0, categoryModels);
        mContext = context;
        this.categoryModels = categoryModels;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.dropdown_menu_popup_item, parent, false);

        CategoryModel categoryModel = categoryModels.get(position);

        TextView name = listItem.findViewById(R.id.tv_spinner);
        name.setText(categoryModel.getTitle());

        return listItem;
    }
}
