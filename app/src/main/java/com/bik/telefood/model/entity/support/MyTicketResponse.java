package com.bik.telefood.model.entity.support;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyTicketResponse extends MainResponse {
    @SerializedName("tickets")
    @Expose
    private List<TicketModel> tickets = null;

    public List<TicketModel> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketModel> tickets) {
        this.tickets = tickets;
    }
}
