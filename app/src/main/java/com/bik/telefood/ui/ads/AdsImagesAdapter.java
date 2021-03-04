package com.bik.telefood.ui.ads;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ItemAddAdsImageListBinding;
import com.squareup.picasso.Picasso;

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
        Log.d("wasd", "getItemCount: " + uriList.size());
        return uriList.size() + 1;
    }

    public interface OnCancelImageListener {
        void onAddImageClick();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemAddAdsImageListBinding itemCategoryBinding;

        public ViewHolder(@NonNull ItemAddAdsImageListBinding itemView) {
            super(itemView.getRoot());
            itemCategoryBinding = itemView;
        }

        private void bind(int position) {
            if (position < uriList.size()) {
                Uri uri = uriList.get(position);
                Picasso.get().load(uri).fit().into(itemCategoryBinding.ivAdsImage);
                Log.d("wasd", "bind: test " + position);
            } else {
                itemCategoryBinding.ivAdsImage.setImageResource(R.drawable.ic_add_photo);
                itemCategoryBinding.ivAdsImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                itemCategoryBinding.getRoot().setOnClickListener(v -> mListener.onAddImageClick());
            }
        }
    }
}
