package com.bik.telefood.ui.more.ads;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.databinding.ItemMyProductBinding;

public class MyAdsAdapter extends RecyclerView.Adapter<MyAdsAdapter.ViewHolder> {

    private OnCardClickListener mListener;

    public MyAdsAdapter(OnCardClickListener mListener) {
        this.mListener = mListener;
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
        return 20;
    }

    public interface OnCardClickListener {
        void onClick();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull ItemMyProductBinding itemView) {
            super(itemView.getRoot());
            constraintLayout = itemView.constraintLayoutProduct;
        }

        public void bind(int position) {
            constraintLayout.setOnClickListener(v -> mListener.onClick());
        }
    }
}
