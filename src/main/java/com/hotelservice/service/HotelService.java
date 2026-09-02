package com.hotelservice.service;

import com.hotelservice.dto.input.HotelInputDto;
import com.hotelservice.dto.output.HotelOutputDto;
import com.hotelservice.dto.output.HotelShortOutputDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public HotelShortOutputDto createHotel(HotelInputDto hotelInputDto);

    public HotelOutputDto addAmenities(Long id, Set<String> amenities);

    public Map<String, Long> getHistogram(String param);

}
