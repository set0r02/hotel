package com.hotelservice.dto.input;

import com.hotelservice.dto.AddressDto;
import com.hotelservice.dto.ArrivalTimeDto;
import com.hotelservice.dto.ContactsDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HotelInputDto(

        @NotBlank
        String name,

        String description,

        @NotBlank
        String brand,

        @Valid
        @NotNull
        AddressDto address,

        @Valid
        @NotNull
        ContactsDto contacts,

        @Valid
        @NotNull
        ArrivalTimeDto arrivalTime
) {
}
