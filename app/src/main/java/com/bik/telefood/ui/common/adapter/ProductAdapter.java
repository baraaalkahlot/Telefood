package com.bik.telefood.ui.common.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ItemProductBinding;
import com.bik.telefood.model.entity.general.services.ServicesItemModel;
import com.bik.telefood.model.network.OnLoadingRequestListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ServicesItemModel> servicesItemModels;
    private OnLoadingRequestListener onLoadingRequestListener;
    private OnProductClickListener onProductClickListener;
    private boolean isLastPage = false;
    private boolean isLoading = true;
    private int pageNumber = 1;
    private Context context;

    public ProductAdapter(List<ServicesItemModel> servicesItemModels, OnProductClickListener onProductClickListener, Context context) {
        this.servicesItemModels = servicesItemModels;
        this.onProductClickListener = onProductClickListener;
        this.context = context;
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
        return new ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!isLoading && !isLastPage && position == servicesItemModels.size() - 2) {
            onLoadingRequestListener.onLoadRequest(++pageNumber);
        }
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return servicesItemModels.size();
    }

    public interface OnProductClickListener {
        void OnProductClick(int id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemProductBinding itemProductBinding;

        public ViewHolder(@NonNull ItemProductBinding itemView) {
            super(itemView.getRoot());
            itemProductBinding = itemView;
        }

        public void bind(int position) {
            ServicesItemModel servicesItemModel = servicesItemModels.get(position);
            Picasso.get()
                    .load(Uri.parse(servicesItemModel.getImg()))
                    .error(R.color.concrete)
                    .placeholder(R.color.concrete)
                    .into(itemProductBinding.ivProduct);
            itemProductBinding.tvProductName.setText(servicesItemModel.getName());
            itemProductBinding.tvProductPrice.setText(context.getString(R.string.bind_price, servicesItemModel.getPrice()));
            itemProductBinding.getRoot().setOnClickListener(v -> onProductClickListener.OnProductClick(servicesItemModel.getId()));
        }
    }
}
