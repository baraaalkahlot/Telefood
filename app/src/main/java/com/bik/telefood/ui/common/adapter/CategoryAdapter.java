package com.bik.telefood.ui.common.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ItemCategoryBinding;
import com.bik.telefood.model.entity.Autherntication.CategoryModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final List<CategoryModel> categoryModelList;
    private OnCategorySelectListener onCategorySelectListener;
    private Activity context;

    public CategoryAdapter(List<CategoryModel> categoryModelList, OnCategorySelectListener onCategorySelectListener, Activity context) {
        this.categoryModelList = categoryModelList;
        this.onCategorySelectListener = onCategorySelectListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size() + 1;
    }

    public interface OnCategorySelectListener {
        void onSelect(int id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemCategoryBinding itemCategoryBinding;

        public ViewHolder(@NonNull ItemCategoryBinding itemView) {
            super(itemView.getRoot());
            itemCategoryBinding = itemView;
        }

        public void bind(int position) {
            if (position < categoryModelList.size()) {
                CategoryModel categoryModel = categoryModelList.get(position);
                Picasso.get()
                        .load(categoryModel.getImg())
                        .error(R.color.concrete)
                        .placeholder(R.color.concrete)
                        .into(itemCategoryBinding.ivCategoryImage);

                itemCategoryBinding.ivCategoryImage.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_ellipse_light_gray, context.getTheme()));
                itemCategoryBinding.tvCategoryName.setText(categoryModel.getTitle());
                itemCategoryBinding.getRoot().setOnClickListener(v -> onCategorySelectListener.onSelect(categoryModel.getId()));
            } else {
                itemCategoryBinding.ivCategoryImage.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_ellipse_lochinvar, context.getTheme()));
                itemCategoryBinding.tvCategoryName.setText((R.string.title_all));
                itemCategoryBinding.ivCategoryImage.setImageResource(R.drawable.ic_category_all);
                itemCategoryBinding.getRoot().setOnClickListener(v -> onCategorySelectListener.onSelect(AppConstant.CATEGORY_ALL_ID));
            }
        }
    }
}
