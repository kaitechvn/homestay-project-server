package com.example.homestay.service;

import com.example.homestay.dto.reponse.HomestayResponse;
import com.example.homestay.dto.request.HomestayRequest;
import com.example.homestay.dto.request.HomestayUpdateRequest;
import com.example.homestay.model.Homestay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HomestayService {

    @Query("SELECT h FROM Homestay h ORDER BY h.price ASC")
    Page<Homestay> findAllOrderByPriceAsc(Pageable pageable);

    @Query("SELECT h FROM Homestay h ORDER BY h.price DESC")
    Page<Homestay> findAllOrderByPriceDesc(Pageable pageable);

    @Query("SELECT h FROM Homestay h WHERE h.price BETWEEN :minPrice AND :maxPrice ORDER BY h.price DESC")
    Page<Homestay> findByPriceRangeDesc(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );

    @Query("SELECT h FROM Homestay h WHERE " +
            "(:street IS NULL OR h.address LIKE %:street%) AND " +
            "(:district IS NULL OR h.district = :district) AND " +
            "(:city IS NULL OR h.city = :city)")
    Page<Homestay> findByAddressComponents(
            @Param("street") String street,
            @Param("district") String district,
            @Param("city") String city,
            Pageable pageable
    );


    // Create a new homestay
    HomestayResponse createHomestay(HomestayRequest createHomestayDto);

    // Update an existing homestay
    HomestayResponse updateHomestay(Long id, HomestayUpdateRequest updateHomestayDto);

    // Get a homestay by ID
//    Optional<Homestay> getHomestayById(Long id);

    // Get all homestays
//    List<Homestay> getAllHomestays();

    // Delete a homestay by ID
    void deleteHomestay(Long id);
}

