package com.hotelservice.controller;

import com.hotelservice.dto.input.HotelInputDto;
import com.hotelservice.dto.output.HotelOutputDto;
import com.hotelservice.dto.output.HotelShortOutputDto;
import com.hotelservice.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Hotels",
        description = "Operations for working with hotels"
)
public class HotelController {

    private final HotelService hotelService;


    @Operation(
            summary = "Get hotel by id",
            description = "Return detailed information about a specific hotel"
    )
    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelOutputDto> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getById(id));
    }

    @Operation(
            summary = "Get all hotels",
            description = "Return all hotels with short information"
    )
    @GetMapping("/hotels")
    public ResponseEntity<List<HotelShortOutputDto>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAll());
    }

    @Operation(
            summary = "Search hotels",
            description = "Search hotels by name, brand, city, country and amenities"
    )
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

    @Operation(
            summary = "Create hotel",
            description = "Create a new hotel"
    )
    @PostMapping("/hotels")
    public ResponseEntity<HotelShortOutputDto> createHotel(@Valid @RequestBody HotelInputDto hotelInputDto){
        HotelShortOutputDto hotelShortOutputDto = hotelService.createHotel(hotelInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelShortOutputDto);
    }

    @Operation(
            summary = "Add amenities",
            description = "Add amenities to an existing hotel"
    )
    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<HotelOutputDto> addAmenities(
            @PathVariable Long id,
            @RequestBody Set<String> amenities)
    {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.addAmenities(id, amenities));
    }

    @Operation(
            summary = "Get hotel histogram",
            description = """
                    Return number of hotels grouped by parameter.
                    Allowed parameters: brand, city, country, amenities.
                    """
    )
    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Long>> getHistogram(@PathVariable String param){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHistogram(param));
    }
}
