package com.bik.telefood.ui.more.ads;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ItemMyProductBinding;
import com.bik.telefood.model.entity.general.services.ServicesItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdsAdapter extends RecyclerView.Adapter<MyAdsAdapter.ViewHolder> {

    private OnCardClickListener mListener;
    private List<ServicesItemModel> services;
    private Context context;

    public MyAdsAdapter(OnCardClickListener mListener, List<ServicesItemModel> services, Context context) {
        this.mListener = mListener;
        this.services = services;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMyProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public interface OnCardClickListener {
        void onClick(int id, Boolean favorite);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMyProductBinding itemProductBinding;

        public ViewHolder(@NonNull ItemMyProductBinding itemView) {
            super(itemView.getRoot());
            itemProductBinding = itemView;
            itemView.tvProductName.setBackground(null);
            itemView.tvProductPrice.setBackground(null);
        }

        public void bind(int position) {
            ServicesItemModel servicesItemModel = services.get(position);
            Picasso.get()
                    .load(Uri.parse(servicesItemModel.getImg()))
                    .error(R.color.concrete)
                    .placeholder(R.color.concrete)
                    .into(itemProductBinding.ivProduct);

            itemProductBinding.tvProductName.setText(servicesItemModel.getName());
            itemProductBinding.tvProductPrice.setText(context.getString(R.string.bind_price, servicesItemModel.getPrice()));
            itemProductBinding.getRoot().setOnClickListener(v -> mListener.onClick(servicesItemModel.getId(), servicesItemModel.getFavorite()));
        }
    }
}
