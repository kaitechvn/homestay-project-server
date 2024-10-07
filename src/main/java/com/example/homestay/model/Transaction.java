package com.example.homestay.model;

import com.example.homestay.enums.PaymentChannel;
import com.example.homestay.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @Enumerated(EnumType.STRING)
    private PaymentChannel channel;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private Integer amount;
    private String billNo;

    @CreationTimestamp
    private LocalDate createdDate;

}
