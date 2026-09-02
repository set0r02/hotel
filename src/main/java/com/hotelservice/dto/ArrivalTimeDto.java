package com.hotelservice.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record ArrivalTimeDto(

        @NotNull
        LocalTime checkIn,

        LocalTime checkOut
) {
}
