package com.hotelservice.dto.input;

import com.hotelservice.dto.AddressDto;
import com.hotelservice.dto.ArrivalTimeDto;
import com.hotelservice.dto.ContactsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HotelInputDto(

        @Schema(
                example = "DoubleTree by Hilton Minsk"
        )
        @NotBlank
        String name,

        @Schema(
                example = "The DoubleTree by Hilton Hotel Minsk offers luxurious rooms..."
        )
        String description,

        @Schema(
                example = "Hilton"
        )
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
