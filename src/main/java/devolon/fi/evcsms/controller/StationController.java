package devolon.fi.evcsms.controller;

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

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@RestController
@RequestMapping("/api/station")
@RequiredArgsConstructor
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

}
