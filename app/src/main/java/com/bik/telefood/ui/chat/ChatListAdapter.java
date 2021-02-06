package com.bik.telefood.ui.chat;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.databinding.ItemChatContactBinding;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private OnCardClickListener mListener;

    public ChatListAdapter(OnCardClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemChatContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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

        public ViewHolder(@NonNull ItemChatContactBinding itemView) {
            super(itemView.getRoot());
            constraintLayout = itemView.constraintLayoutChatContact;
        }

        public void bind(int position) {

            constraintLayout.setOnClickListener(v -> mListener.onClick());
        }
    }
}
