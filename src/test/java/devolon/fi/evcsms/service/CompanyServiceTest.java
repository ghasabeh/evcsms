package devolon.fi.evcsms.service;

import devolon.fi.evcsms.mapper.CompanyMapper;
import devolon.fi.evcsms.model.dto.CompanyDto;
import devolon.fi.evcsms.model.entity.CompanyEntity;
import devolon.fi.evcsms.model.entity.CompanyTreePathMaker;
import devolon.fi.evcsms.repository.CompanyRepository;
import devolon.fi.evcsms.service.company.CompanyServiceImpl;
import devolon.fi.evcsms.utils.exception.CustomEntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyTreePathMaker companyTreePathMaker;

    @Mock
    private CompanyMapper companyMapper;

    @Autowired
    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void createACompany() {
        CompanyDto dto = CompanyDto.builder().name("TestingCompany").build();
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(1L);
        companyEntity.setName("TestingCompany");
        when(companyMapper.map(dto)).thenReturn(companyEntity);
        doNothing().when(companyTreePathMaker).prePersist(companyEntity);
        when(companyRepository.save(companyEntity)).thenReturn(companyEntity);
        Long id = companyService.create(dto);

        Assertions.assertNotNull(id);
        verify(companyRepository, times(1)).save(any());
    }

    @Test
    void updateACompany() {
        CompanyDto companyDto = CompanyDto.builder().name("TestingCompany").build();
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(1L);
        companyEntity.setName("TestingCompany");
        when(companyMapper.map(companyDto)).thenReturn(companyEntity);
        doNothing().when(companyTreePathMaker).preUpdate(companyEntity);
        when(companyRepository.save(companyEntity)).thenReturn(companyEntity);
        companyService.update(companyDto);

        verify(companyMapper).map(companyDto);
        verify(companyRepository).save(any());
    }


    @Test
    void findByIdACompany() {
        Long searchedCompanyId = 1L;
        CompanyDto companyDto = CompanyDto.builder().name("TestingCompany").build();
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(1L);
        companyEntity.setName("TestingCompany");
        when(companyRepository.findById(searchedCompanyId)).thenReturn(Optional.of(companyEntity));
        when(companyMapper.map(companyEntity)).thenReturn(companyDto);

        companyService.findById(searchedCompanyId);
        verify(companyRepository).findById(searchedCompanyId);
        verify(companyMapper).map(companyEntity);
    }

    @Test
    void findByIdACompanyIfNotExists() {
        Long searchedCompanyId = 1L;
        when(companyRepository.findById(searchedCompanyId)).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomEntityNotFoundException.class, () ->
                companyService.findById(searchedCompanyId));

        verify(companyRepository).findById(searchedCompanyId);
    }

    @Test
    void deleteByIdACompany() {
        Long wantToDeleteId = 1L;
        doNothing().when(companyRepository).deleteById(wantToDeleteId);
        companyService.deleteById(wantToDeleteId);

        verify(companyRepository).deleteById(wantToDeleteId);
    }

    @Test
    void deleteACompanyIfNotExist() {
        Long wantToDeleteId = 1L;
        doThrow(EmptyResultDataAccessException.class)
                .when(companyRepository).deleteById(wantToDeleteId);
        Assertions.assertThrows(EmptyResultDataAccessException.class, () ->
                companyService.deleteById(wantToDeleteId));

        verify(companyRepository).deleteById(wantToDeleteId);
    }

}
