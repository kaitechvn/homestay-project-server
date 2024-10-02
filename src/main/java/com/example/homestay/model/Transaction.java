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

    @Column(name = "trans_no")
    private String transNo;

    @Column(name = "payment_method")
    private String method;

    @Enumerated(EnumType.STRING)  // Use Enum for transaction statuses
    private PaymentChannel channel;

    @Enumerated(EnumType.STRING)  // Use Enum for transaction statuses
    private TransactionStatus status;

    private Integer amount;

    @Column(name = "bill_no")
    private String billNo;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDate createDate;

}
