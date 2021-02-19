package com.bik.telefood.ui.ads;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.databinding.ItemAddAdsImageListBinding;

import java.util.List;

public class AdsImagesAdapter extends RecyclerView.Adapter<AdsImagesAdapter.ViewHolder> {
    private List<Uri> uriList;
    private OnCancelImageListener mListener;

    public AdsImagesAdapter(List<Uri> uriList, OnCancelImageListener mListener) {
        this.uriList = uriList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemAddAdsImageListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (uriList.isEmpty()) {
            mListener.onClick();
        }
        return uriList.size();
    }

    public interface OnCancelImageListener {
        void onClick();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemAddAdsImageListBinding itemCategoryBinding;

        public ViewHolder(@NonNull ItemAddAdsImageListBinding itemView) {
            super(itemView.getRoot());
            itemCategoryBinding = itemView;
        }

        public void bind(int position) {
            Uri uri = uriList.get(position);
            itemCategoryBinding.ivAdsImage.setImageURI(uri);
            itemCategoryBinding.ivRemoveImage.setOnClickListener(v -> {
                uriList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, uriList.size());
            });
        }
    }
}
