package com.tmquang.score.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "years")
public class Year {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "year", unique = true)
    private String year;

    @Column(name = "tuition")
    private Integer tuition;

    public Year() {
    }

    public Year(Integer id, String year, Integer tuition) {
        this.id = id;
        this.year = year;
        this.tuition = tuition;
    }

}
