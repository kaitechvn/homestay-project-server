package com.example.homestay.model;

import com.example.homestay.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "booking")
public class Booking extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "homestay_id")
    private Homestay homestay;

    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private Integer guests;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private Integer totalAmount;
    private Boolean isReview;

    @Override
    public String toString() {
        return "Booking{id=" + id +
                ", checkinDate=" + checkinDate +
                ", checkoutDate=" + checkoutDate +
                ", status=" + status.name() + "}";
    }
}

