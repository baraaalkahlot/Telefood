package com.bik.telefood.ui.support;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityMessageBinding;
import com.bik.telefood.model.entity.support.TicketModel;
import com.bik.telefood.model.network.ApiConstant;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SupportMessageActivity extends AppCompatActivity {

    private ActivityMessageBinding binding;
    private SupportViewModel supportViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportViewModel = new ViewModelProvider(this).get(SupportViewModel.class);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String id = getIntent().getStringExtra(AppConstant.TICKET_ID);
        binding.ivLocation.setImageResource(R.drawable.ic_send);


        loadChat(id);
        binding.ivLocation.setOnClickListener(v -> sendMessage(id, binding.etMessage.getText().toString().trim()));
    }

    private void loadChat(String id) {
        supportViewModel.showTicket(id, this, getSupportFragmentManager()).observe(this, showDetailsResponse -> {
            TicketModel ticketModel = showDetailsResponse.getTicket();
            binding.includeChatContact.tvTitle.setText(ticketModel.getSubject());
            SupportMessageAdapter supportMessageAdapter = new SupportMessageAdapter(showDetailsResponse.getMessages(), this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setStackFromEnd(true);
            binding.rvMessages.setLayoutManager(linearLayoutManager);
            binding.rvMessages.setAdapter(supportMessageAdapter);
            supportMessageAdapter.notifyDataSetChanged();
            binding.rvMessages.scrollToPosition(supportMessageAdapter.getItemCount() - 1);
        });
    }

    private void sendMessage(String id, String msg) {
        if (msg.isEmpty()) return;
        binding.etMessage.setText("");
        HashMap<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put(ApiConstant.ID, RequestBody.create(id, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));
        hashMap.put(ApiConstant.MESSAGE, RequestBody.create(msg, MediaType.parse(ApiConstant.MULTIPART_FORM_DATA)));

        supportViewModel.sendMessage(hashMap, null, this, getSupportFragmentManager()).observe(this, mainResponse -> {
            loadChat(id);
        });
    }
}