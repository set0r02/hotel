package com.hotelservice;

import com.hotelservice.dto.AddressDto;
import com.hotelservice.dto.ArrivalTimeDto;
import com.hotelservice.dto.ContactsDto;
import com.hotelservice.dto.input.HotelInputDto;
import com.hotelservice.dto.output.HotelOutputDto;
import com.hotelservice.dto.output.HotelShortOutputDto;
import com.hotelservice.entity.Address;
import com.hotelservice.entity.ArrivalTime;
import com.hotelservice.entity.Contacts;
import com.hotelservice.entity.Hotel;
import com.hotelservice.mapper.HotelMapper;
import com.hotelservice.repository.HotelRepository;
import com.hotelservice.service.Impl.HotelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private Hotel hotel;
    private HotelShortOutputDto shortOutputDto;
    private HotelOutputDto outputDto;

    @BeforeEach
    void setUp() {
        Address address = Address.builder()
                .houseNumber(9L)
                .street("Pobediteley Avenue")
                .city("Minsk")
                .country("Belarus")
                .postalCode("220004")
                .build();

        Contacts contacts = Contacts.builder()
                .phone("+375 17 309-80-00")
                .email("doubletreeminsk.info@hilton.com")
                .build();

        ArrivalTime arrivalTime = ArrivalTime.builder()
                .checkIn(LocalTime.of(14, 0))
                .checkOut(LocalTime.of(12, 0))
                .build();

        hotel = Hotel.builder()
                .id(1L)
                .name("DoubleTree by Hilton Minsk")
                .description("Hotel description")
                .brand("Hilton")
                .address(address)
                .contacts(contacts)
                .arrivalTime(arrivalTime)
                .amenities(new HashSet<>())
                .build();

        shortOutputDto = new HotelShortOutputDto(
                1L,
                "DoubleTree by Hilton Minsk",
                "Hotel description",
                "9 Pobediteley Avenue, Minsk, 220004, Belarus",
                "+375 17 309-80-00"
        );

        outputDto = new HotelOutputDto(
                1L,
                "DoubleTree by Hilton Minsk",
                "Hotel description",
                "Hilton",
                null,
                null,
                null,
                Set.of()
        );
    }

    @Test
    void getById_shouldReturnHotel() {
        when(hotelRepository.findById(1L))
                .thenReturn(Optional.of(hotel));

        when(hotelMapper.toHotelOutputDto(hotel))
                .thenReturn(outputDto);

        HotelOutputDto result = hotelService.getById(1L);

        assertEquals(outputDto, result);

        verify(hotelRepository).findById(1L);
        verify(hotelMapper).toHotelOutputDto(hotel);
    }

    @Test
    void getAll_shouldReturnHotels() {
        when(hotelRepository.findAll())
                .thenReturn(List.of(hotel));

        when(hotelMapper.toHotelShortOutputDto(hotel))
                .thenReturn(shortOutputDto);

        List<HotelShortOutputDto> result = hotelService.getAll();

        assertEquals(1, result.size());
        assertEquals(shortOutputDto, result.getFirst());

        verify(hotelRepository).findAll();
    }

    @Test
    void searchHotels_shouldReturnHotels() {
        when(hotelRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(hotel));

        when(hotelMapper.toHotelShortOutputDto(hotel))
                .thenReturn(shortOutputDto);

        List<HotelShortOutputDto> result = hotelService.searchHotels(
                "DoubleTree",
                "Hilton",
                "Minsk",
                "Belarus",
                List.of("Free WiFi")
        );

        assertEquals(1, result.size());
        assertEquals(shortOutputDto, result.getFirst());

        verify(hotelRepository).findAll(any(Specification.class));
    }

    @Test
    void createHotel_shouldSaveHotel() {
        HotelInputDto inputDto = new HotelInputDto(
                "DoubleTree by Hilton Minsk",
                "Hotel description",
                "Hilton",
                new AddressDto(
                        9L,
                        "Pobediteley Avenue",
                        "Minsk",
                        "Belarus",
                        "220004"
                ),
                new ContactsDto(
                        "+375 17 309-80-00",
                        "doubletreeminsk.info@hilton.com"
                ),
                new ArrivalTimeDto(
                        LocalTime.of(14, 0),
                        LocalTime.of(12, 0)
                )
        );

        when(hotelRepository.save(any(Hotel.class)))
                .thenReturn(hotel);

        when(hotelMapper.toHotelShortOutputDto(hotel))
                .thenReturn(shortOutputDto);

        HotelShortOutputDto result =
                hotelService.createHotel(inputDto);

        assertEquals(shortOutputDto, result);

        verify(hotelRepository).save(any(Hotel.class));
        verify(hotelMapper).toHotelShortOutputDto(hotel);
    }

    @Test
    void addAmenities_shouldAddAmenities() {
        when(hotelRepository.findById(1L))
                .thenReturn(Optional.of(hotel));

        when(hotelRepository.save(hotel))
                .thenReturn(hotel);

        when(hotelMapper.toHotelOutputDto(hotel))
                .thenReturn(outputDto);

        HotelOutputDto result = hotelService.addAmenities(
                1L,
                Set.of("Free WiFi", "Parking")
        );

        assertTrue(hotel.getAmenities().contains("Free WiFi"));
        assertTrue(hotel.getAmenities().contains("Parking"));
        assertEquals(outputDto, result);

        verify(hotelRepository).save(hotel);
    }

    @Test
    void getHistogram_shouldReturnHistogram() {
        when(hotelRepository.countHotelsByCity())
                .thenReturn(List.of(
                        new Object[]{"Minsk", 2L},
                        new Object[]{"Moscow", 1L}
                ));

        Map<String, Long> result =
                hotelService.getHistogram("city");

        assertEquals(2L, result.get("Minsk"));
        assertEquals(1L, result.get("Moscow"));

        verify(hotelRepository).countHotelsByCity();
    }
}