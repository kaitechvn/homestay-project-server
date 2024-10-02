package com.example.homestay.repository;

import com.example.homestay.model.Homestay;
import com.example.homestay.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Images, Integer> {

    List<Images> findByHomestay(Homestay homestay);
    Optional<Images> findByUrl(String url);
}
