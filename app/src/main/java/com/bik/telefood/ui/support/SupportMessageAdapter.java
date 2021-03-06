package com.bik.telefood.ui.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.databinding.ItemRecivedMessageBinding;
import com.bik.telefood.databinding.ItemSentMessaeBinding;
import com.bik.telefood.model.entity.support.SupportMessageModel;
import com.bik.telefood.model.network.ApiConstant;

import java.util.List;

public class SupportMessageAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private List<SupportMessageModel> messages;
    private Context context;

    public SupportMessageAdapter(List<SupportMessageModel> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    public void setMessages(List<SupportMessageModel> messages) {
        messages.addAll(messages);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            return new SentViewHolder(ItemSentMessaeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            return new ReceivedViewHolder(ItemRecivedMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_MESSAGE_SENT)
            ((SentViewHolder) holder).bind(position);
        else ((ReceivedViewHolder) holder).bind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getFrom().equals(ApiConstant.CHAT_TYPE_CURRENT_USER)) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SentViewHolder extends RecyclerView.ViewHolder implements ChatAttachmentAdapter.OnAttachClickListener {
        private final ItemSentMessaeBinding binding;

        public SentViewHolder(@NonNull ItemSentMessaeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.tvDate.setVisibility(View.GONE);
        }

        public void bind(int position) {
            SupportMessageModel supportMessageModel = messages.get(position);
            binding.tvSentMessage.setText(supportMessageModel.getMsg());
            if (!supportMessageModel.getMsgAttachment().isEmpty()) {
                binding.rvAttachment.setVisibility(View.VISIBLE);
                binding.tvSentMessage.setVisibility(View.GONE);
                ChatAttachmentAdapter chatAttachmentAdapter = new ChatAttachmentAdapter(supportMessageModel.getMsgAttachment(), this);
                binding.rvAttachment.setAdapter(chatAttachmentAdapter);
                chatAttachmentAdapter.notifyDataSetChanged();
            } else {
                binding.rvAttachment.setVisibility(View.GONE);
                binding.tvSentMessage.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onAttachClick(Uri uri) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(uri);
            context.startActivity(i);
        }
    }

    public class ReceivedViewHolder extends RecyclerView.ViewHolder implements ChatAttachmentAdapter.OnAttachClickListener {
        private final ItemRecivedMessageBinding binding;

        public ReceivedViewHolder(@NonNull ItemRecivedMessageBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.tvDate.setVisibility(View.GONE);
        }

        public void bind(int position) {
            SupportMessageModel supportMessageModel = messages.get(position);
            binding.tvRecivedMessage.setText(supportMessageModel.getMsg());
            if (!supportMessageModel.getMsgAttachment().isEmpty()) {
                binding.rvAttachment.setVisibility(View.VISIBLE);
                binding.tvRecivedMessage.setVisibility(View.GONE);
                ChatAttachmentAdapter chatAttachmentAdapter = new ChatAttachmentAdapter(supportMessageModel.getMsgAttachment(), this);
                binding.rvAttachment.setAdapter(chatAttachmentAdapter);
                chatAttachmentAdapter.notifyDataSetChanged();
            } else {
                binding.rvAttachment.setVisibility(View.GONE);
                binding.tvRecivedMessage.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onAttachClick(Uri uri) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(uri);
            context.startActivity(i);
        }
    }

}
