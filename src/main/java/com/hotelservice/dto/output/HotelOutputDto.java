package com.hotelservice.dto.output;

import com.hotelservice.dto.AddressDto;
import com.hotelservice.dto.ArrivalTimeDto;
import com.hotelservice.dto.ContactsDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public record HotelOutputDto(

        @Schema(example = "1")
        Long id,

        @Schema(example = "DoubleTree by Hilton Minsk")
        String name,

        @Schema(example = "The DoubleTree by Hilton Hotel Minsk offers luxurious rooms...")
        String description,

        @Schema(example = "Hilton")
        String brand,

        AddressDto address,

        ContactsDto contacts,

        ArrivalTimeDto arrivalTime,

        @Schema(
                example = """
                        [
                          "Free parking",
                          "Free WiFi",
                          "Fitness center"
                        ]
                        """
        )
        Set<String> amenities
){

}
