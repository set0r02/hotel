package com.hotelservice.controller;

import com.hotelservice.dto.input.HotelInputDto;
import com.hotelservice.dto.output.HotelOutputDto;
import com.hotelservice.dto.output.HotelShortOutputDto;
import com.hotelservice.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/property-view")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelOutputDto> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getById(id));
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelShortOutputDto>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelShortOutputDto>> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) List<String> amenities
    ){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.searchHotels(
                name,
                brand,
                city,
                country,
                amenities
        ));
    }

    @PostMapping("/hotels")
    public ResponseEntity<HotelShortOutputDto> createHotel(@Valid @RequestBody HotelInputDto hotelInputDto){
        HotelShortOutputDto hotelShortOutputDto = hotelService.createHotel(hotelInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelShortOutputDto);
    }

    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<HotelOutputDto> addAmenities(
            @PathVariable Long id,
            @RequestBody Set<String> amenities)
    {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.addAmenities(id, amenities));
    }

    @GetMapping("//histogram/{param}")
    public ResponseEntity<Map<String, Long>> getHistogram(@PathVariable String param){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHistogram(param));
    }
}
