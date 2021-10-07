package devolon.fi.evcsms.controller;

import devolon.fi.evcsms.model.dto.LocationDto;
import devolon.fi.evcsms.model.dto.StationDto;
import devolon.fi.evcsms.model.dto.response.ResponseDto;
import devolon.fi.evcsms.service.station.StationService;
import devolon.fi.evcsms.utils.CreateValidationGroup;
import devolon.fi.evcsms.utils.UpdateValidationGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Tag(name = "API of Station")
@RestController
@RequestMapping("/api/station")
@RequiredArgsConstructor
@Validated
public class StationController {
    private final StationService stationService;

    @Operation(summary = "With This Api you can a create station")
    @PostMapping
    public ResponseEntity<ResponseDto<Long>> createStation(@Validated(CreateValidationGroup.class) @RequestBody StationDto stationDto) {
        return new ResponseEntity<>(new ResponseDto<>(stationService.create(stationDto)), HttpStatus.CREATED);
    }

    @Operation(summary = "With This Api you can find a station by it's id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<StationDto>> findById(@PathVariable Long id) {
        return new ResponseEntity<>(new ResponseDto<>(stationService.findById(id)), HttpStatus.OK);
    }

    @Operation(summary = "With This Api you can update a station")
    @PutMapping
    public ResponseEntity<ResponseDto<Void>> updateStation(@Validated(UpdateValidationGroup.class) @RequestBody StationDto stationDto) {
        stationService.update(stationDto);
        return new ResponseEntity<>(new ResponseDto<>(), HttpStatus.OK);
    }

    @Operation(summary = "With This Api you can delete a station")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteStation(@PathVariable Long id) {
        stationService.deleteById(id);
        return new ResponseEntity<>(new ResponseDto<>(), HttpStatus.OK);
    }

    @Operation(summary = "With This Api you can get all stations of one company",
            description = "By the id of company you can get all it's stations hierarchy with Pagination")
    @GetMapping("/company/{companyId}")
    public ResponseEntity<ResponseDto<List<StationDto>>> allStationOf(@PathVariable Long companyId,
                                                                      @RequestParam @Min(0) Integer page,
                                                                      @RequestParam @Min(1) @Max(10) Integer size) {
        return new ResponseEntity<>(new ResponseDto<>(stationService.getAllStationOfCompany(companyId, page, size)), HttpStatus.OK);
    }

    @Operation(summary = "With this Api you can get all stations with given radius form a point",
            description = "Get all stations within the radius of n kilometers from a point (latitude, longitude) ordered by distance. You should get with Pagination.")
    @PostMapping("/nearest-location")
    public ResponseEntity<ResponseDto<List<StationDto>>> nearestLocation(
            @RequestBody @Validated LocationDto locationDto,
            @RequestParam @Min(0) Integer page,
            @RequestParam @Min(1) @Max(10) Integer size) {
        return new ResponseEntity<>(new ResponseDto<>(stationService.nearestLocation(locationDto, page, size)), HttpStatus.OK);
    }
}
