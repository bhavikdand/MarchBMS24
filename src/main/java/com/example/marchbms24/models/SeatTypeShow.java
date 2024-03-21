package com.example.marchbms24.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class SeatTypeShow extends BaseModel{
    private SeatType seatType;
    @ManyToOne
    private Show show;
    private double price;
}
