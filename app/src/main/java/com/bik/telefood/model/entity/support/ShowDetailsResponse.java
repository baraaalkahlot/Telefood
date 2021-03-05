package com.bik.telefood.model.entity.support;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowDetailsResponse extends MainResponse {
    @SerializedName("ticket")
    @Expose
    private TicketModel ticket;
    @SerializedName("messages")
    @Expose
    private List<SupportMessageModel> messages = null;

    public TicketModel getTicket() {
        return ticket;
    }

    public void setTicket(TicketModel ticket) {
        this.ticket = ticket;
    }

    public List<SupportMessageModel> getMessages() {
        return messages;
    }

    public void setMessages(List<SupportMessageModel> messages) {
        this.messages = messages;
    }
}
