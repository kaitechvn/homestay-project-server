package com.example.homestay.model;

import com.example.homestay.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "transaction")
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private String transNo;

    @Column(name = "payment_method")
    private String method;

    @Enumerated(EnumType.STRING)  // Use Enum for transaction statuses
    private TransactionStatus status;

    private Integer amount;

    private String billNo;

    @CreationTimestamp
    private LocalDate createDate;


}
