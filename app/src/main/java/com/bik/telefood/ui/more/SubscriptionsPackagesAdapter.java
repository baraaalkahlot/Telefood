package com.bik.telefood.ui.more;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.databinding.ItemSubscriptionBinding;
import com.bik.telefood.model.entity.general.PackageModel;

import java.util.List;

public class SubscriptionsPackagesAdapter extends RecyclerView.Adapter<SubscriptionsPackagesAdapter.ViewHolder> {
    private final List<PackageModel> packageModels;
    private OnSingleSelectionListener mListener;
    private int lastCheckedPosition = -1;

    public SubscriptionsPackagesAdapter(List<PackageModel> packageModels, OnSingleSelectionListener mListener) {
        this.packageModels = packageModels;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemSubscriptionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return packageModels.size();
    }

    public interface OnSingleSelectionListener {
        void onSelect(int packageId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemSubscriptionBinding binding;

        public ViewHolder(@NonNull ItemSubscriptionBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;
        }

        public void bind(int position) {
            PackageModel packageModel = packageModels.get(position);
            binding.rbSubscription.setText(packageModel.getTitle());
            binding.tvPrice.setText(packageModel.getPrice());
            binding.tvDesc.setText(packageModel.getDescription());

            binding.rbSubscription.setChecked(position == lastCheckedPosition);

            binding.rbSubscription.setOnClickListener(v -> {
                int copyOfLastCheckedPosition = lastCheckedPosition;
                lastCheckedPosition = getAdapterPosition();
                notifyItemChanged(copyOfLastCheckedPosition);
                notifyItemChanged(lastCheckedPosition);
                mListener.onSelect(packageModel.getId());
            });
        }
    }
}
