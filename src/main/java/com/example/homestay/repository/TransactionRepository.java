package com.example.homestay.repository;

import com.example.homestay.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Optional<Transaction> findByBillNo(String billNo);
}
