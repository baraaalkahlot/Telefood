package com.bik.telefood.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ItemRecivedMessageBinding;
import com.bik.telefood.databinding.ItemSentMessaeBinding;
import com.bik.telefood.databinding.ReceviedItemLocationBinding;
import com.bik.telefood.databinding.SentItemLocationBinding;
import com.bik.telefood.model.network.ApiConstant;
import com.google.firebase.firestore.GeoPoint;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_MAP_LEFT = 3;
    private static final int VIEW_TYPE_MAP_RIGHT = 4;
    private static final String TIME_FORMAT = "h:mm a";
    private final String myId;
    private OnMapClickListener onMapClickListener;

    private Context mContext;
    private List<MessageModel> mMessageList;

    public MessageAdapter(OnMapClickListener onMapClickListener, Context context, List<MessageModel> messageList, String myId) {
        this.onMapClickListener = onMapClickListener;
        mContext = context;
        mMessageList = messageList;
        this.myId = myId;
    }

    public static String getConvertedTimeStampToDate(Date date) throws NullPointerException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    @Override
    public int getItemViewType(int position) {

        if (String.valueOf(mMessageList.get(position).getSender_id()).equals(myId)) {
            // If the current user is the sender of the message
            if (mMessageList.get(position).getType().equals(ApiConstant.MESSAGE_TYPE_LOCATION))
                return VIEW_TYPE_MAP_RIGHT;
            else return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            if (mMessageList.get(position).getType().equals(ApiConstant.MESSAGE_TYPE_LOCATION))
                return VIEW_TYPE_MAP_LEFT;
            else return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            return new SentMessageHolder(ItemSentMessaeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            return new ReceivedMessageHolder(ItemRecivedMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else if (viewType == VIEW_TYPE_MAP_LEFT)
            return new ReceivedMapMessageHolder(ReceviedItemLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        else
            return new SentMapMessageHolder(SentItemLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel message = mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MAP_LEFT:
                ((ReceivedMapMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MAP_RIGHT:
                ((SentMapMessageHolder) holder).bind(message);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public interface OnMapClickListener {
        void onMapClick(GeoPoint geoPoint);
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {

        private final ItemSentMessaeBinding binding;

        SentMessageHolder(ItemSentMessaeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(MessageModel messageModel) {
            binding.tvSentMessage.setText(messageModel.getMessage());

            try {
                String date = getConvertedTimeStampToDate(messageModel.getTimestamp().toDate());
                binding.tvDate.setText(date);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        private final ItemRecivedMessageBinding binding;

        ReceivedMessageHolder(ItemRecivedMessageBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(MessageModel messageModel) {
            binding.tvRecivedMessage.setText(messageModel.getMessage());

            try {
                String date = getConvertedTimeStampToDate(messageModel.getTimestamp().toDate());
                binding.tvDate.setText(date);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private class ReceivedMapMessageHolder extends RecyclerView.ViewHolder {

        private final ReceviedItemLocationBinding binding;

        ReceivedMapMessageHolder(ReceviedItemLocationBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(MessageModel messageModel) {
            Picasso.get().load(R.drawable.map).fit().into(binding.ivLocation);
            binding.getRoot().setOnClickListener(v -> onMapClickListener.onMapClick(messageModel.getLocation()));
            try {
                String date = getConvertedTimeStampToDate(messageModel.getTimestamp().toDate());
                binding.tvDate.setText(date);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private class SentMapMessageHolder extends RecyclerView.ViewHolder {

        private final SentItemLocationBinding binding;

        SentMapMessageHolder(SentItemLocationBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(MessageModel messageModel) {
            Picasso.get().load(R.drawable.map).fit().into(binding.ivLocation);
            binding.getRoot().setOnClickListener(v -> onMapClickListener.onMapClick(messageModel.getLocation()));
            try {
                String date = getConvertedTimeStampToDate(messageModel.getTimestamp().toDate());
                binding.tvDate.setText(date);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}