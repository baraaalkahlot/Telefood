package com.bik.telefood.ui.support;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ItemAddAdsImageListBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatAttachmentAdapter extends RecyclerView.Adapter<ChatAttachmentAdapter.ViewHolder> {
    private List<String> uriList;
    private OnAttachClickListener mListener;

    public ChatAttachmentAdapter(List<String> uriList, OnAttachClickListener mListener) {
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
        return uriList.size();
    }

    public interface OnAttachClickListener {
        void onAttachClick(Uri uri);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemAddAdsImageListBinding itemCategoryBinding;

        public ViewHolder(@NonNull ItemAddAdsImageListBinding itemView) {
            super(itemView.getRoot());
            itemCategoryBinding = itemView;
        }

        private void bind(int position) {
            String s = uriList.get(position);
            Uri uri = Uri.parse(s);
            Picasso.get().load(uri).fit().error(R.drawable.ic_baseline_picture_as_pdf).into(itemCategoryBinding.ivAdsImage);
            itemCategoryBinding.getRoot().setOnClickListener(v -> mListener.onAttachClick(uri));
        }
    }
}
