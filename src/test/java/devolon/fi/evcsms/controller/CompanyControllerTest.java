package devolon.fi.evcsms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import devolon.fi.evcsms.model.dto.CompanyDto;
import devolon.fi.evcsms.service.company.CompanyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CompanyController.class})
@WebMvcTest
public class CompanyControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Autowired
    public CompanyControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Captor
    private ArgumentCaptor<CompanyDto> argumentCaptor;

    @Test
    public void postingANewCompanyShouldCreateAnewCompanyInDatabase() throws Exception {
        CompanyDto companyDto = CompanyDto.builder().name("TestingCompany").build();

        Mockito.when(companyService.create(argumentCaptor.capture())).thenReturn(1L);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.responseType", is("GENERAL")))
                .andExpect(jsonPath("$.response").exists());

        Assertions.assertEquals(argumentCaptor.getValue().getName(), "TestingCompany");

    }

    @Test
    public void companyNameCanNotBeEmpty() throws Exception {
        CompanyDto companyDto = CompanyDto.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
