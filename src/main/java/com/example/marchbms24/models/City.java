package com.example.marchbms24.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

//@Getter // Annotation
//@Setter
@Data
@Entity(name = "cities")
public class City extends BaseModel{
    private String name;
    @OneToMany(mappedBy = "city")
    private List<Theatre> theatres;
}
