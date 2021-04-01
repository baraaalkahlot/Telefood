package com.bik.telefood.ui.favorite;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.databinding.ItemFavoriteProductBinding;
import com.bik.telefood.model.entity.general.services.ServicesItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteProductAdapter extends RecyclerView.Adapter<FavoriteProductAdapter.ViewHolder> {
    private final List<ServicesItemModel> data;
    private final OnCardClickListener onCardClickListener;

    public FavoriteProductAdapter(List<ServicesItemModel> data, OnCardClickListener onCardClickListener) {
        this.data = data;
        this.onCardClickListener = onCardClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemFavoriteProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    public interface OnCardClickListener {
        void onClick(int id);

        void onFavClick(int id, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemFavoriteProductBinding binding;

        public ViewHolder(@NonNull ItemFavoriteProductBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(int position) {
            ServicesItemModel servicesItemModel = data.get(position);
            binding.tvProductName.setText(servicesItemModel.getName());
            binding.tvProductPrice.setText(servicesItemModel.getPrice());
            Picasso.get().load(servicesItemModel.getImg()).fit().into(binding.ivProduct);
            binding.btnFavorite.setOnClickListener(v -> onCardClickListener.onFavClick(servicesItemModel.getId(), position));
            binding.getRoot().setOnClickListener(v -> onCardClickListener.onClick(servicesItemModel.getId()));
        }
    }
}
