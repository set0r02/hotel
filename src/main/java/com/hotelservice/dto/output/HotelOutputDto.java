package com.hotelservice.dto.output;

import com.hotelservice.dto.AddressDto;
import com.hotelservice.dto.ArrivalTimeDto;
import com.hotelservice.dto.ContactsDto;

import java.util.Set;

public record HotelOutputDto(

    Long id,
    String name,
    String description,
    String brand,
    AddressDto address,
    ContactsDto contacts,
    ArrivalTimeDto arrivalTime,
    Set<String> amenities
){

}
