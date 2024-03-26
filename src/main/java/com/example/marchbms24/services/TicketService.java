package com.example.marchbms24.services;

import com.example.marchbms24.models.Ticket;

import java.util.List;

public interface TicketService {

    Ticket bookTicket(int userId, int showId, List<Integer> showSeatIds) throws Exception;
}
