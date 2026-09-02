package com.hotelservice.service.Impl;

import com.hotelservice.dto.input.HotelInputDto;
import com.hotelservice.dto.output.HotelOutputDto;
import com.hotelservice.dto.output.HotelShortOutputDto;
import com.hotelservice.entity.Address;
import com.hotelservice.entity.ArrivalTime;
import com.hotelservice.entity.Contacts;
import com.hotelservice.entity.Hotel;
import com.hotelservice.mapper.HotelMapper;
import com.hotelservice.repository.HotelRepository;
import com.hotelservice.service.HotelService;
import com.hotelservice.specifications.HotelSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    public final HotelRepository hotelRepository;;
    private final HotelMapper hotelMapper;

    @Override
    public HotelOutputDto getById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Hotel with id " + id + " not found")
                );

        return hotelMapper.toHotelOutputDto(hotel);
    }

    @Override
    public List<HotelShortOutputDto> getAll() {
        return hotelRepository.findAll()
                .stream()
                .map(hotelMapper::toHotelShortOutputDto)
                .toList();
    }

    @Override
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


}
