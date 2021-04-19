package com.bik.telefood.ui.more.vendors;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ItemProvidersBinding;
import com.bik.telefood.model.entity.Autherntication.vendors.VendorsModel;
import com.bik.telefood.model.network.OnLoadingRequestListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VendorsAdapter extends RecyclerView.Adapter<VendorsAdapter.ViewHolder> {
    private List<VendorsModel> vendorsModels;
    private OnLoadingRequestListener onLoadingRequestListener;
    private OnProvidersClickListener onProvidersClickListener;
    private boolean isLastPage = false;
    private boolean isLoading = true;
    private int pageNumber = 1;

    public VendorsAdapter(List<VendorsModel> vendorsModels, OnProvidersClickListener onProvidersClickListener) {
        this.vendorsModels = vendorsModels;
        this.onProvidersClickListener = onProvidersClickListener;
    }

    public void setOnLoadingRequestListener(OnLoadingRequestListener onLoadingRequestListener) {
        this.onLoadingRequestListener = onLoadingRequestListener;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public void resetPager() {
        isLastPage = false;
        isLoading = true;
        pageNumber = 1;
        notifyDataSetChanged();
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemProvidersBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!isLoading && !isLastPage && position == vendorsModels.size() - 2) {
            onLoadingRequestListener.onLoadRequest(++pageNumber);
        }
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return vendorsModels.size();
    }

    public interface OnProvidersClickListener {
        void OnProvidersClick(int id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemProvidersBinding binding;

        public ViewHolder(@NonNull ItemProvidersBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.btnFavorite.setVisibility(View.GONE);
        }

        public void bind(int position) {
            VendorsModel vendorsModel = vendorsModels.get(position);
            Picasso.get()
                    .load(Uri.parse(vendorsModel.getAvatar()))
                    .error(R.drawable.ic_baseline_person)
                    .placeholder(R.drawable.ic_baseline_person)
                    .into(binding.ivAvatar);
            binding.tvFullName.setText(vendorsModel.getName());
            binding.getRoot().setOnClickListener(v -> onProvidersClickListener.OnProvidersClick(vendorsModel.getId()));
            binding.rbSupplierRating.setRating(vendorsModel.getRating());
        }
    }
}
