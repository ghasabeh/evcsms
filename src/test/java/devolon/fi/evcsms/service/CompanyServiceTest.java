package devolon.fi.evcsms.service;

import devolon.fi.evcsms.mapper.CompanyMapper;
import devolon.fi.evcsms.model.dto.CompanyDto;
import devolon.fi.evcsms.model.entity.CompanyEntity;
import devolon.fi.evcsms.repository.CompanyRepository;
import devolon.fi.evcsms.service.company.CompanyService;
import devolon.fi.evcsms.service.company.CompanyServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@SpringBootTest
public class CompanyServiceTest {
    private CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceTest(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @MockBean
    private CompanyMapper companyMapper;

    @Test
    void createACompany() {
        CompanyService companyService = new CompanyServiceImpl(companyRepository, companyMapper);
        CompanyDto dto = CompanyDto.builder().name("TestingCompany").build();
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setName("TestingCompany");
        Mockito.when(companyMapper.map(dto)).thenReturn(companyEntity);
        Long id = companyService.create(dto);

        Assertions.assertNotNull(id);
        Assertions.assertEquals(1, companyRepository.count());
    }

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }
}
