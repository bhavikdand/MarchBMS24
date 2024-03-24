package com.example.marchbms24.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "movies")
public class Movie extends BaseModel{
    private String name;
    private Genre genre;
}
