package com.bik.telefood.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.databinding.ItemProvidersCardBinding;

public class HomeSliderAdapter extends RecyclerView.Adapter<HomeSliderAdapter.ViewHolder> {
    private OnHomeSliderClickListener onHomeSliderClickListener;

    public HomeSliderAdapter(OnHomeSliderClickListener onHomeSliderClickListener) {
        this.onHomeSliderClickListener = onHomeSliderClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemProvidersCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public interface OnHomeSliderClickListener {
        void onHomeSliderClick(int id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemProvidersCardBinding itemCategoryBinding;

        public ViewHolder(@NonNull ItemProvidersCardBinding itemView) {
            super(itemView.getRoot());
            itemCategoryBinding = itemView;
        }

        public void bind(int position) {
/*            Picasso.get()
                    .load(categoryModel.getImg())
                    .error(R.color.concrete)
                    .placeholder(R.color.concrete)
                    .into(itemCategoryBinding.ivCategoryImage);*/

            itemCategoryBinding.tvProviderName.setText("براء اياد الكحلوت");
//            itemCategoryBinding.getRoot().setOnClickListener(v -> onHomeSliderClickListener.onHomeSliderClick(categoryModel.getId()));
        }
    }
}
