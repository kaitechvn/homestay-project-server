package com.example.homestay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "image")
@Setter
@Getter
@NoArgsConstructor
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "image_url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "homestay_id")
    @JsonIgnore
    private Homestay homestay;

}
