package com.bik.telefood.ui.ads;

import android.content.Context;
import android.net.Uri;
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
    private Context context;

    public AdsImagesAdapter(List<Uri> uriList, OnCancelImageListener mListener, Context context) {
        this.uriList = uriList;
        this.mListener = mListener;
        this.context = context;
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
            } else {
                itemCategoryBinding.ivAdsImage.setImageResource(R.drawable.ic_add_photo);
                itemCategoryBinding.ivAdsImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                itemCategoryBinding.getRoot().setOnClickListener(v -> mListener.onAddImageClick());
            }
        }
    }
}
