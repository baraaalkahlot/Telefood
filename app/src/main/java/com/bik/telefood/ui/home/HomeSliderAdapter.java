package com.bik.telefood.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ItemProvidersCardBinding;
import com.bik.telefood.model.entity.Autherntication.vendors.FeaturedVendorsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeSliderAdapter extends RecyclerView.Adapter<HomeSliderAdapter.ViewHolder> {
    private OnHomeSliderClickListener onHomeSliderClickListener;
    private List<FeaturedVendorsModel> featuredVendorsModels;

    public HomeSliderAdapter(OnHomeSliderClickListener onHomeSliderClickListener, List<FeaturedVendorsModel> featuredVendorsModels) {
        this.onHomeSliderClickListener = onHomeSliderClickListener;
        this.featuredVendorsModels = featuredVendorsModels;
    }

    public void setFeaturedVendorsModels(List<FeaturedVendorsModel> featuredVendorsModels) {
        this.featuredVendorsModels = featuredVendorsModels;
        notifyDataSetChanged();
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
        return featuredVendorsModels.size();
    }

    public interface OnHomeSliderClickListener {
        void onHomeSliderClick(long id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemProvidersCardBinding itemCategoryBinding;

        public ViewHolder(@NonNull ItemProvidersCardBinding itemView) {
            super(itemView.getRoot());
            itemCategoryBinding = itemView;
        }

        public void bind(int position) {
            FeaturedVendorsModel featuredVendorsModel = featuredVendorsModels.get(position);
            Picasso.get()
                    .load(featuredVendorsModel.getImg())
                    .error(R.color.concrete)
                    .placeholder(R.color.concrete)
                    .into(itemCategoryBinding.ivProvidersCover);

            itemCategoryBinding.tvProviderName.setText(featuredVendorsModel.getName());
            itemCategoryBinding.rbSupplierRating.setRating(featuredVendorsModel.getRating());
            itemCategoryBinding.getRoot().setOnClickListener(v -> onHomeSliderClickListener.onHomeSliderClick(featuredVendorsModel.getId()));
        }
    }
}
