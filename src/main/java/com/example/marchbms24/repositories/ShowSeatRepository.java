package com.example.marchbms24.repositories;

import com.example.marchbms24.models.Show;
import com.example.marchbms24.models.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Integer> {
    Optional<ShowSeat> findById(int showSeatId);

//    @Query(value = "select * from show_seats")

//    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
//    List<ShowSeat> findShowSeatsByIdInAndSeatStatus_AvailableAndShow(List<Integer> showSeatIds, Show show);

}
