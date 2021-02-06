package com.bik.telefood.ui.chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.databinding.ActivityChatListBinding;

public class ChatListActivity extends AppCompatActivity implements ChatListAdapter.OnCardClickListener {
    private ActivityChatListBinding binding;
    private ChatListAdapter chatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatListAdapter = new ChatListAdapter(this);
        binding.rvContactList.setAdapter(chatListAdapter);
    }

    @Override
    public void onClick() {
        startActivity(new Intent(this, MessageActivity.class));
    }
}