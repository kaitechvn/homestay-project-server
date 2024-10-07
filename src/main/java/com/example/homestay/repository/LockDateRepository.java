package com.example.homestay.repository;

import com.example.homestay.model.Homestay;
import com.example.homestay.model.LockDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LockDateRepository extends JpaRepository<LockDate, Integer> {

    List<LockDate> findAllByHomestayId(Integer homestayId);
    Optional<LockDate> findByHomestayIdAndLockDate(Integer homestayId, LocalDate lockDate);

    // Custom query to check if any locked dates exist between checkin and checkout dates
    @Query("SELECT CASE WHEN COUNT(ld) > 0 THEN TRUE ELSE FALSE END " +
            "FROM LockDate ld " +
            "WHERE ld.homestayId = :homestayId " +
            "AND ld.lockDate BETWEEN :checkinDate AND :checkoutDate")
    boolean existsByHomestayIdAndLockDateBetween(
            @Param("homestayId") Integer homestayId,
            @Param("checkinDate") LocalDate checkinDate,
            @Param("checkoutDate") LocalDate checkoutDate
    );

}

