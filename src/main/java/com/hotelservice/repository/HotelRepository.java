package com.hotelservice.repository;

import com.hotelservice.entity.Hotel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
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

    @Query("""
            select h.brand, count(h)
            from Hotel h
            group by h.brand
            """)
    List<Object[]> countHotelsByBrand();

    @Query("""
            select h.address.city, count(h)
            from Hotel h
            group by h.address.city
            """)
    List<Object[]> countHotelsByCity();

    @Query("""
            select h.address.country, count(h)
            from Hotel h
            group by h.address.country
            """)
    List<Object[]> countHotelsByCountry();

    @Query("""
            select amenity, count(h)
            from Hotel h
            join h.amenities amenity
            group by amenity
            """)
    List<Object[]> countHotelsByAmenities();
}
