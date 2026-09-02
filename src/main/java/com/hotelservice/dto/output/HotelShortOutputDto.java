package com.hotelservice.dto.output;

public record HotelShortOutputDto(
        Long id,
        String name,
        String description,
        String address,
        String phone
) {
}
