package com.hotelservice.mapper;

import com.hotelservice.dto.output.HotelOutputDto;
import com.hotelservice.dto.output.HotelShortOutputDto;
import com.hotelservice.entity.Address;
import com.hotelservice.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(
            target = "address",
            source = "address",
            qualifiedByName = "addressToString"
    )
    @Mapping(
            target = "phone",
            source = "contacts.phone"
    )
    HotelShortOutputDto toHotelShortOutputDto(Hotel hotel);

    HotelOutputDto toHotelOutputDto(Hotel hotel);

    @Named("addressToString")
    default String addressToString(Address address) {
        if (address == null) {
            return null;
        }

        return "%d %s, %s, %s, %s".formatted(
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostalCode(),
                address.getCountry()
        );
    }

}
