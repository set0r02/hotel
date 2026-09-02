package com.hotelservice.specifications;

import com.hotelservice.entity.Hotel;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Locale;

@UtilityClass
public class HotelSpecifications {

    public static Specification<Hotel> nameContains(String name) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("name")),
                        "%" + name.toLowerCase(Locale.ROOT) + "%"
                );
    }

    public static Specification<Hotel> brandEquals(String brand) {
        return (root, query, cb) ->
                cb.equal(
                        cb.lower(root.get("brand")),
                        brand.toLowerCase(Locale.ROOT)
                );
    }

    public static Specification<Hotel> cityEquals(String city) {
        return (root, query, cb) ->
                cb.equal(
                        cb.lower(root.get("address").get("city")),
                        city.toLowerCase(Locale.ROOT)
                );
    }

    public static Specification<Hotel> countryEquals(String country) {
        return (root, query, cb) ->
                cb.equal(
                        cb.lower(root.get("address").get("country")),
                        country.toLowerCase(Locale.ROOT)
                );
    }

    public static Specification<Hotel> hasAmenities(List<String> amenities) {
        return (root, query, cb) -> {
            if (amenities == null || amenities.isEmpty()) {
                return cb.conjunction();
            }

            Join<Hotel, String> amenitiesJoin =
                    root.join("amenities", JoinType.INNER);

            query.distinct(true);

            List<String> normalizedAmenities = amenities.stream()
                    .map(value -> value.toLowerCase(Locale.ROOT))
                    .toList();

            return cb.lower(amenitiesJoin).in(normalizedAmenities);
        };
    }

}
