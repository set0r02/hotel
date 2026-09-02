package com.hotelservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDto(

        @Schema(example = "9")
        @NotNull
        Long houseNumber,

        @Schema(example = "Pobediteley Avenue")
        @NotBlank
        String street,

        @Schema(example = "Minsk")
        @NotBlank
        String city,

        @Schema(example = "Belarus")
        @NotBlank
        String country,

        @Schema(example = "220004")
        @NotBlank
        String postCode
) {
}