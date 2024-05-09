package com.example.marchbms24.controllers;


import com.example.marchbms24.dtos.BookTicketRequestDto;
import com.example.marchbms24.dtos.BookTicketResponseDto;
import com.example.marchbms24.dtos.ResponseStatus;
import com.example.marchbms24.models.Ticket;
import com.example.marchbms24.services.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TicketControllerTest {

    /*
     3 scenarios:
     1. Validation fails - -ve
     2. Ticket service fails - -ve
     3. Ticket service gives a ticket obj - +ve
     */
    @MockBean
    private TicketService ticketService;

    @Autowired
    private TicketController ticketController;

    @Test
    public void testBookTicket_ShowIdNegative() throws Exception {
        //Arrange
        BookTicketRequestDto requestDto = new BookTicketRequestDto();
        requestDto.setShowId(-1);
        requestDto.setUserId(1);
        requestDto.setShowSeatIds(List.of(1,2,3,4));

        when(ticketService.bookTicket(requestDto.getUserId(),
                requestDto.getShowId(), requestDto.getShowSeatIds())).thenThrow(Exception.class);

        // Act
        BookTicketResponseDto responseDto = ticketController.bookTicket(requestDto);

        //Assert
        assertNotNull(responseDto, "Response dto should not be null");
        assertNotNull(responseDto.getResponse());
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponse().getResponseStatus());
        assertNotNull(responseDto.getResponse().getError());
        assertNull(responseDto.getTicket(), "Ticket should not be generated");
    }

    @Test
    public void testBookTicket_UserIdNegative() throws Exception {
        //Arrange
        BookTicketRequestDto requestDto = new BookTicketRequestDto();
        requestDto.setShowId(1);
        requestDto.setUserId(-1);
        requestDto.setShowSeatIds(List.of(1,2,3,4));

        when(ticketService.bookTicket(requestDto.getUserId(),
                requestDto.getShowId(), requestDto.getShowSeatIds())).thenThrow(Exception.class);

        // Act
        BookTicketResponseDto responseDto = ticketController.bookTicket(requestDto);

        //Assert
        assertNotNull(responseDto, "Response dto should not be null");
        assertNotNull(responseDto.getResponse());
        assertEquals(ResponseStatus.FAILURE, responseDto.getResponse().getResponseStatus());
        assertNotNull(responseDto.getResponse().getError());
        assertNull(responseDto.getTicket(), "Ticket should not be generated");
    }

    // TODO: Cover empty show seat ids and null show seat ids

    @Test
    public void testBookTicket_Positive() throws Exception {
        //Arrange
        BookTicketRequestDto requestDto = new BookTicketRequestDto();
        requestDto.setShowId(1);
        requestDto.setUserId(1);
        requestDto.setShowSeatIds(List.of(1,2,3,4));



        when(ticketService.bookTicket(requestDto.getUserId(),
                requestDto.getShowId(), requestDto.getShowSeatIds())).thenReturn(new Ticket());

        // Act
        BookTicketResponseDto responseDto = ticketController.bookTicket(requestDto);

        //Assert
        assertNotNull(responseDto, "Response dto should not be null");
        assertNotNull(responseDto.getResponse());
        assertEquals(ResponseStatus.SUCCESS, responseDto.getResponse().getResponseStatus());
        assertNull(responseDto.getResponse().getError());
        assertNotNull(responseDto.getTicket(), "Ticket should be generated");


    }



}