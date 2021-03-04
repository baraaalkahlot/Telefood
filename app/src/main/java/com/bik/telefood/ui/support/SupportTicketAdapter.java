package com.bik.telefood.ui.support;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ItemTechnicalSupportBinding;
import com.bik.telefood.model.entity.support.TicketModel;
import com.bik.telefood.model.network.ApiConstant;

import java.util.List;

public class SupportTicketAdapter extends RecyclerView.Adapter<SupportTicketAdapter.ViewHolder> {

    private List<TicketModel> tickets;
    private Context context;

    public SupportTicketAdapter(List<TicketModel> tickets, Context context) {
        this.tickets = tickets;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTechnicalSupportBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTechnicalSupportBinding binding;

        public ViewHolder(@NonNull ItemTechnicalSupportBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            itemView.getRoot().setCardBackgroundColor(Color.WHITE);
            itemView.tvTitleNotification.setBackground(null);
            itemView.tvContent.setBackground(null);
        }

        public void bind(int position) {
            TicketModel ticketModel = tickets.get(position);
            binding.tvTitleNotification.setText(ticketModel.getSubject());
            binding.tvContent.setText(ticketModel.getDescription());

            if (ticketModel.getStatus().equals(ApiConstant.OPEN)) {
                binding.tvTicketStatus.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.shape_ticket_status_open, context.getTheme()));
                binding.tvTicketStatus.setText(R.string.label_open);
            } else {
                binding.tvTicketStatus.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.shape_ticket_status_closed, context.getTheme()));
                binding.tvTicketStatus.setText(R.string.label_closed);
            }
        }
    }
}
