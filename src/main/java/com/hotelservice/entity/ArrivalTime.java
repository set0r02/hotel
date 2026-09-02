package com.hotelservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArrivalTime {

    @Column(name = "check_in", nullable = false)
    private LocalTime checkIn;
    @Column(name = "check_out")
    private LocalTime checkOut;

}
