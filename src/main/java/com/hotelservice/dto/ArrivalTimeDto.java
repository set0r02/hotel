package com.hotelservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record ArrivalTimeDto(

        @Schema(
                description = "Hotel check-in time",
                example = "14:00"
        )
        @NotNull
        LocalTime checkIn,

        @Schema(
                description = "Hotel check-out time",
                example = "12:00"
        )
        LocalTime checkOut
) {
}
