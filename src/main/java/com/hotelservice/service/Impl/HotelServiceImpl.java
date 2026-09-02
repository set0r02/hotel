package com.hotelservice.service.Impl;

import com.hotelservice.dto.input.HotelInputDto;
import com.hotelservice.dto.output.HotelOutputDto;
import com.hotelservice.dto.output.HotelShortOutputDto;
import com.hotelservice.entity.Address;
import com.hotelservice.entity.ArrivalTime;
import com.hotelservice.entity.Contacts;
import com.hotelservice.entity.Hotel;
import com.hotelservice.exception.AmenitiesNotEmptyException;
import com.hotelservice.exception.HotelNotFoundException;
import com.hotelservice.exception.InvalidHistogramParameterException;
import com.hotelservice.mapper.HotelMapper;
import com.hotelservice.repository.HotelRepository;
import com.hotelservice.service.HotelService;
import com.hotelservice.specifications.HotelSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    public final HotelRepository hotelRepository;;
    private final HotelMapper hotelMapper;

    @Override
    @Transactional(readOnly = true)
    public HotelOutputDto getById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->
                        new HotelNotFoundException("Hotel with id " + id + " not found")
                );

        return hotelMapper.toHotelOutputDto(hotel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelShortOutputDto> getAll() {
        return hotelRepository.findAll()
                .stream()
                .map(hotelMapper::toHotelShortOutputDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelShortOutputDto> searchHotels(
            String name,
            String brand,
            String city,
            String country,
            List<String> amenities
    ) {
        Specification<Hotel> specification =
                HotelSpecifications.nameContains(name)
                .and(HotelSpecifications.brandEquals(brand))
                .and(HotelSpecifications.cityEquals(city))
                .and(HotelSpecifications.countryEquals(country))
                .and(HotelSpecifications.hasAmenities(amenities));

        return hotelRepository.findAll(specification)
                .stream()
                .map(hotelMapper::toHotelShortOutputDto)
                .toList();
    }

    @Override
    public HotelShortOutputDto createHotel(HotelInputDto hotelInputDto) {

        Address address = Address.builder()
                .houseNumber(hotelInputDto.address().houseNumber())
                .street(hotelInputDto.address().street())
                .city(hotelInputDto.address().city())
                .country(hotelInputDto.address().country())
                .postalCode(hotelInputDto.address().postCode())
                .build();

        Contacts contact = Contacts.builder()
                .phone(hotelInputDto.contacts().phone())
                .email(hotelInputDto.contacts().email())
                .build();

        ArrivalTime arrivalTime = ArrivalTime.builder()
                .checkIn(hotelInputDto.arrivalTime().checkIn())
                .checkOut(hotelInputDto.arrivalTime().checkOut())
                .build();

        Hotel hotel = Hotel.builder()
                .name(hotelInputDto.name())
                .description(hotelInputDto.description())
                .brand(hotelInputDto.brand())
                .address(address)
                .contacts(contact)
                .arrivalTime(arrivalTime)
                .build();

        return hotelMapper.toHotelShortOutputDto(hotelRepository.save(hotel));

    }

    @Override
    public HotelOutputDto addAmenities(Long id, Set<String> amenities) {

        if(amenities == null) {
            throw new AmenitiesNotEmptyException("Amenities must be not null");
        }

        Set<String> cleanedAmenities = amenities.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toSet());

        if (cleanedAmenities.isEmpty()) {
            throw new AmenitiesNotEmptyException(
                    "Amenities must contain at least one valid value"
            );
        }

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->
                        new HotelNotFoundException("Hotel with id " + id + " not found")
                );

        hotel.getAmenities().addAll(cleanedAmenities);

        return hotelMapper.toHotelOutputDto(hotelRepository.save(hotel));
    }

    @Override
    public Map<String, Long> getHistogram(String param) {
        
        List<Object[]> result = switch(param.toLowerCase()){
            case "brand" -> hotelRepository.countHotelsByBrand();
            case "city" -> hotelRepository.countHotelsByCity();
            case "country" -> hotelRepository.countHotelsByCountry();
            case "amenities" -> hotelRepository.countHotelsByAmenities();
            default -> throw new InvalidHistogramParameterException
                    ("Invalid histogram parameter: " + param);
        };

        Map<String, Long> histogram = new HashMap<>();

        for (Object[] row : result) {
            histogram.put(
                    (String) row[0],
                    (Long) row[1]
            );
        }

        return histogram;
    }


}
