package devolon.fi.evcsms.controller;

import devolon.fi.evcsms.model.dto.CompanyDto;
import devolon.fi.evcsms.service.company.CompanyService;
import devolon.fi.evcsms.utils.CreateValidationGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService service;

    @PostMapping
    public ResponseEntity<Long> createCompany(@Validated(CreateValidationGroup.class) @RequestBody CompanyDto companyDto) {
        return new ResponseEntity<>(service.create(companyDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }
}
