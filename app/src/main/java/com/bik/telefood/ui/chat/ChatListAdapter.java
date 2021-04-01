package com.bik.telefood.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ItemRealChatListItemBinding;
import com.bik.telefood.model.entity.chat.RoomModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private final OnCardClickListener mListener;
    private final List<RoomModel> myRoomModels;
    private Context context;

    public ChatListAdapter(OnCardClickListener mListener, List<RoomModel> myRoomModels, Context context) {
        this.mListener = mListener;
        this.myRoomModels = myRoomModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemRealChatListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return myRoomModels.size();
    }

    public interface OnCardClickListener {
        void onClick(int id, String providerName);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemRealChatListItemBinding itemView;

        public ViewHolder(@NonNull ItemRealChatListItemBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
            itemView.ivProfileImage.setBackground(null);
            itemView.tvTitle.setBackground(null);
            itemView.constraintLayoutChatContact.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        public void bind(int position) {
            RoomModel roomModel = myRoomModels.get(position);

            itemView.tvTitle.setText(roomModel.getUserName());
            Picasso.get().load(roomModel.getUserAvatar()).fit().placeholder(R.drawable.ic_baseline_person).error(R.drawable.ic_baseline_person).into(itemView.ivProfileImage);
            itemView.getRoot().setOnClickListener(v -> mListener.onClick(roomModel.getRoomId(), roomModel.getUserName()));
        }
    }
}
