package com.bik.telefood.ui.support;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityTechnicalSupportBinding;
import com.bik.telefood.model.entity.support.TicketModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.List;

public class TechnicalSupportActivity extends AppCompatActivity {

    private static final int ACTION_GO_TO_ADD_TICKET = 111;
    private SupportTicketAdapter supportTicketAdapter;
    private List<TicketModel> tickets;
    private SkeletonScreen skeletonScreen;
    private SupportViewModel supportViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTechnicalSupportBinding binding = ActivityTechnicalSupportBinding.inflate(getLayoutInflater());
        supportViewModel = new ViewModelProvider(this).get(SupportViewModel.class);
        setContentView(binding.getRoot());

        tickets = new ArrayList<>();
        supportTicketAdapter = new SupportTicketAdapter(tickets, this);
        binding.rvTicket.setAdapter(supportTicketAdapter);
        skeletonScreen = Skeleton.bind(binding.rvTicket)
                .adapter(supportTicketAdapter)
                .color(R.color.mercury)
                .duration(500)
                .load(R.layout.item_technical_support)
                .show();

        loadTicketList();
        binding.fabContactUs.setOnClickListener(v -> startActivityForResult(new Intent(this, ContactUsActivity.class), ACTION_GO_TO_ADD_TICKET));
    }

    private void loadTicketList() {
        supportViewModel.getMyTicket(this, getSupportFragmentManager()).observe(this, myTicketResponse -> {
            skeletonScreen.hide();
            tickets.addAll(myTicketResponse.getTickets());
            supportTicketAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_GO_TO_ADD_TICKET && resultCode == RESULT_OK) {
            skeletonScreen.show();
            loadTicketList();
        }
    }
}