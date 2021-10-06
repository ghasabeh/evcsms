package devolon.fi.evcsms.controller;

import devolon.fi.evcsms.model.dto.LocationDto;
import devolon.fi.evcsms.model.dto.StationDto;
import devolon.fi.evcsms.model.dto.response.ResponseDto;
import devolon.fi.evcsms.service.station.StationService;
import devolon.fi.evcsms.utils.CreateValidationGroup;
import devolon.fi.evcsms.utils.UpdateValidationGroup;
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
@RestController
@RequestMapping("/api/station")
@RequiredArgsConstructor
@Validated
public class StationController {
    private final StationService stationService;

    @PostMapping
    public ResponseEntity<ResponseDto<Long>> createStation(@Validated(CreateValidationGroup.class) @RequestBody StationDto stationDto) {
        return new ResponseEntity<>(new ResponseDto<>(stationService.create(stationDto)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<StationDto>> findById(@PathVariable Long id) {
        return new ResponseEntity<>(new ResponseDto<>(stationService.findById(id)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto<Void>> updateStation(@Validated(UpdateValidationGroup.class) @RequestBody StationDto stationDto) {
        stationService.update(stationDto);
        return new ResponseEntity<>(new ResponseDto<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteStation(@PathVariable Long id) {
        stationService.deleteById(id);
        return new ResponseEntity<>(new ResponseDto<>(), HttpStatus.OK);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<ResponseDto<List<StationDto>>> allStationOf(@PathVariable Long companyId,
                                                                      @RequestParam @Min(0) Integer page,
                                                                      @RequestParam @Min(1) @Max(10) Integer size) {
        return new ResponseEntity<>(new ResponseDto<>(stationService.getAllStationOfCompany(companyId, page, size)), HttpStatus.OK);
    }

    @PostMapping("/nearest-location")
    public ResponseEntity<ResponseDto<List<StationDto>>> nearestLocation(
            @RequestBody @Validated LocationDto locationDto,
            @RequestParam @Min(0) Integer page,
            @RequestParam @Min(1) @Max(10) Integer size) {
        return new ResponseEntity<>(new ResponseDto<>(stationService.nearestLocation(locationDto, page, size)), HttpStatus.OK);
    }
}
