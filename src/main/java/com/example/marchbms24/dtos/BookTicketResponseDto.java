package com.example.marchbms24.dtos;

import com.example.marchbms24.models.Ticket;
import lombok.Data;

@Data
public class BookTicketResponseDto {
    private Response response;
    private Ticket ticket;
}
