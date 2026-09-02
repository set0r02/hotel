package com.hotelservice.service;

import com.hotelservice.dto.output.HotelOutputDto;
import com.hotelservice.dto.output.HotelShortOutputDto;
import com.hotelservice.entity.Hotel;

import java.util.List;

public interface HotelService {

    public HotelOutputDto getById(Long id);

    public List<HotelShortOutputDto> getAll();

    public List<HotelShortOutputDto> searchHotels(
            String name,
            String brand,
            String city,
            String country,
            List<String> amenities
    );


}
