package com.example.homestay.repository;

import com.example.homestay.model.Homestay;
import com.example.homestay.model.LockDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LockDateRepository extends JpaRepository<LockDate, Integer> {

    List<LockDate> findAllByHomestayId(Integer homestayId);
    Optional<LockDate> findByHomestayIdAndLockDate(Integer homestayId, LocalDate lockDate);
}
