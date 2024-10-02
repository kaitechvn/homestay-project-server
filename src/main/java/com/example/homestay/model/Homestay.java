package com.example.homestay.model;

import com.example.homestay.enums.HomestayStatus;
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

    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.ORDINAL)
    private HomestayStatus status;

    private Integer bedrooms;
    private Integer bathrooms;
    private String address;

    @OneToMany
    @JoinColumn(name = "homestay_id")
    private List<Images> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    private Integer guests;
    private Integer price;
    private Double rating;

    @Column(name = "review_count")
    private int reviewCount;

}
