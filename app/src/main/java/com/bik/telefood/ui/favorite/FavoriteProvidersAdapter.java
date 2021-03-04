package com.bik.telefood.ui.favorite;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.databinding.ItemProvidersBinding;
import com.bik.telefood.model.entity.Autherntication.vendors.VendorsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteProvidersAdapter extends RecyclerView.Adapter<FavoriteProvidersAdapter.ViewHolder> {
    private List<VendorsModel> data;

    public FavoriteProvidersAdapter(List<VendorsModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemProvidersBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemProvidersBinding itemProvidersBinding;

        public ViewHolder(@NonNull ItemProvidersBinding itemView) {
            super(itemView.getRoot());
            itemProvidersBinding = itemView;
        }

        public void bind(int position) {
            VendorsModel vendorsModel = data.get(position);
            itemProvidersBinding.tvFullName.setText(vendorsModel.getName());
            Picasso.get().load(vendorsModel.getAvatar()).fit().into(itemProvidersBinding.ivAvatar);
            itemProvidersBinding.rbSupplierRating.setRating((vendorsModel.getRating()));
        }
    }
}
