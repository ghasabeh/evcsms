package devolon.fi.evcsms.service;

import devolon.fi.evcsms.mapper.CompanyMapper;
import devolon.fi.evcsms.model.dto.CompanyDto;
import devolon.fi.evcsms.model.entity.CompanyEntity;
import devolon.fi.evcsms.repository.CompanyRepository;
import devolon.fi.evcsms.service.company.CompanyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

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
        when(companyRepository.save(companyEntity)).thenReturn(companyEntity);
        Long id = companyService.create(dto);

        Assertions.assertNotNull(id);
        verify(companyRepository, times(1)).save(any());
    }
}
