package com.example.homestay.repository;

import com.example.homestay.dto.reponse.ReviewResponse;
import com.example.homestay.model.Homestay;
import com.example.homestay.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findAllByHomestay(Homestay homestay);
}
