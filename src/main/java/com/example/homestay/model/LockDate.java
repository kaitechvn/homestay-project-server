package com.example.homestay.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "homestay_locked_dates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LockDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "homestay_id")
    private Integer homestayId;

    @Column(name = "date")
    private LocalDate lockDate;
}
