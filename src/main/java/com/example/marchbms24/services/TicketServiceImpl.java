package com.example.marchbms24.services;

import com.example.marchbms24.exceptions.InvalidBookTicketRequestException;
import com.example.marchbms24.exceptions.SeatsUnavailableException;
import com.example.marchbms24.models.*;
import com.example.marchbms24.repositories.ShowRepository;
import com.example.marchbms24.repositories.ShowSeatRepository;
import com.example.marchbms24.repositories.TicketRepository;
import com.example.marchbms24.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService{

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(UserRepository userRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public Ticket bookTicket(int userId, int showId, List<Integer> showSeatIds) throws Exception {
        /*
        1. Check if the user is valid
        2. Showid in showSeatIds and given showId should match
        3. Start transaction (SERIALIZABLE)
        4. select * from show_seats where id in (showSeatIds) and seat_status = 'Available' and show_id = {{showId}} for update
        5. if all seats are not available
        6. throw error and rollback the transaction
        7. Update show_seats set seat_status = 'BLOCKED' where ids in (showSeatIds)
        8. Generate ticket object
        9. Store ticket object in DB and return
         */
        Optional<User> userOptional = this.userRepository.findById(userId);
        User user;
        if(userOptional.isPresent()){
            user = userOptional.get();
        } else {
            throw new InvalidBookTicketRequestException("User is invalid");
        }

        Show show = this.showRepository.findById(showId).orElseThrow(() -> new InvalidBookTicketRequestException("Show id is invalid"));
        ShowSeat showSeat = this.showSeatRepository.findById(showSeatIds.get(0)).orElseThrow(() -> new InvalidBookTicketRequestException("Seat id is invalid"));
        if(showSeat.getShow().getId() != showId){
            throw new InvalidBookTicketRequestException("Given seats dont belong to the same show");
        }

        List<ShowSeat> showSeats = null;//this.showSeatRepository.findShowSeatsByIdInAndSeatStatus_AvailableAndShow(showSeatIds, show);

        if(showSeats.size() != showSeatIds.size()){
            throw new SeatsUnavailableException("Some of the seats you are trying to book are unavailable");
        }

        for(ShowSeat ss: showSeats){
            ss.setBookedBy(user);
            ss.setSeatStatus(SeatStatus.BLOCKED);
        }
        showSeatRepository.saveAll(showSeats);

        Ticket ticket = new Ticket();
        ticket.setMovie(show.getMovie());
        ticket.setShow(show);
        ticket.setShowSeats(showSeats);
        ticket.setUser(user);

        return ticketRepository.save(ticket);
    }
}
