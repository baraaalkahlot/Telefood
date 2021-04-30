package com.bik.telefood.ui.chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityChatListBinding;
import com.bik.telefood.model.entity.chat.RoomModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity implements ChatListAdapter.OnCardClickListener {
    private ChatListAdapter chatListAdapter;
    private ChatListViewModel chatListViewModel;
    private List<RoomModel> myRoomModels;
    private SkeletonScreen chatSkeleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.bik.telefood.databinding.ActivityChatListBinding binding = ActivityChatListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatListViewModel = new ViewModelProvider(this).get(ChatListViewModel.class);
        myRoomModels = new ArrayList<>();

        Intent intent = getIntent();
        String roomId = intent.getStringExtra(AppConstant.ROOM_ID);
        chatListAdapter = new ChatListAdapter(roomId, this, myRoomModels, this);
        binding.rvContactList.setHasFixedSize(true);
        binding.rvContactList.setAdapter(chatListAdapter);
        chatSkeleton = Skeleton.bind(binding.rvContactList)
                .adapter(chatListAdapter)
                .color(R.color.mercury)
                .duration(500)
                .load(R.layout.item_real_chat_list_item)
                .show();
        loadData();
    }

    private void loadData() {
        myRoomModels.clear();
        chatListViewModel.getMyRooms(this, getSupportFragmentManager()).observe(this, myRoomModel -> {
            chatSkeleton.hide();
            myRoomModels.addAll(myRoomModel.getRooms());
            chatListAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onClick(int roomId, String providerName) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra(AppConstant.ROOM_ID, String.valueOf(roomId));
        intent.putExtra(AppConstant.USER_NAME, providerName);
        startActivity(intent);
    }
}