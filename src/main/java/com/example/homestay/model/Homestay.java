package com.example.homestay.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "homestay")
public class Homestay extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    private String type;

    @Column(nullable = false)
    private Boolean status;

    @OneToMany
    @JoinColumn(name = "homestay_id")  // Foreign key in the Image table
    private List<Images> images = new ArrayList<>();

    private Integer guest;

    private Double rating;

    @Column(name = "review_count")
    private int reviewCount;

}
