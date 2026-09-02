package com.hotelservice.repository;

import com.hotelservice.entity.Hotel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long>, JpaSpecificationExecutor<Hotel> {

    @Override
    @EntityGraph(attributePaths = "amenities")
    Optional<Hotel> findById(Long id);

    @EntityGraph(attributePaths = "amenities")
    List<Hotel> findAll();
}
