package devolon.fi.evcsms.controller;

import devolon.fi.evcsms.model.dto.CompanyDto;
import devolon.fi.evcsms.model.dto.response.ResponseDto;
import devolon.fi.evcsms.service.company.CompanyService;
import devolon.fi.evcsms.utils.CreateValidationGroup;
import devolon.fi.evcsms.utils.UpdateValidationGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Tag(name = "API of Company")
@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService service;

    @Operation(summary = "With This Api you can a create company")
    @PostMapping
    public ResponseEntity<ResponseDto<Long>> createCompany(@Validated(CreateValidationGroup.class) @RequestBody CompanyDto companyDto) {
        return new ResponseEntity<>(new ResponseDto<>(service.create(companyDto)), HttpStatus.CREATED);
    }

    @Operation(summary = "With This Api you can find a company by it's id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<CompanyDto>> findById(@PathVariable Long id) {
        return new ResponseEntity<>(new ResponseDto<>(service.findById(id)), HttpStatus.OK);
    }

    @Operation(summary = "With this Api you can update a company")
    @PutMapping
    public ResponseEntity<ResponseDto<Void>> updateCompany(@Validated(UpdateValidationGroup.class) @RequestBody CompanyDto companyDto) {
        service.update(companyDto);
        return new ResponseEntity<>(new ResponseDto<>(), HttpStatus.OK);
    }

    @Operation(summary = "With this Api you can delete a company")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteCompany(@PathVariable Long id) {
        service.deleteById(id);
        return new ResponseEntity<>(new ResponseDto<>(), HttpStatus.OK);
    }
}
