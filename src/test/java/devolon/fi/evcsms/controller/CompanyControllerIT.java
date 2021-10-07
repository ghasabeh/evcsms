package devolon.fi.evcsms.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import devolon.fi.evcsms.model.dto.CompanyDto;
import devolon.fi.evcsms.model.dto.response.ResponseDto;
import devolon.fi.evcsms.model.dto.response.ResponseType;
import devolon.fi.evcsms.repository.CompanyRepository;
import devolon.fi.evcsms.utils.exception.BaseExceptionHandler;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static devolon.fi.evcsms.util.JsonHelper.jsonToObject;
import static devolon.fi.evcsms.util.JsonHelper.jsonify;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private CompanyController companyController;
    @Autowired
    private BaseExceptionHandler baseExceptionHandler;

    @BeforeAll
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.companyController, this.baseExceptionHandler).build();// Standalone context
    }

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void createACompanyWithoutParent() throws Exception {
        String jsonResponse = callCreateCompanyApi(CompanyDto.builder().name("test1").build());
        ResponseDto<Long> responseDto = jsonToObject(jsonResponse, new TypeReference<ResponseDto<Long>>() {
        });
        ResponseDto<CompanyDto> findResponse = jsonToObject(callFindByIdCompany(responseDto.getResponse()), new TypeReference<ResponseDto<CompanyDto>>() {
        });

        Assertions.assertEquals("test1", findResponse.getResponse().getName());
        Assertions.assertEquals("0", findResponse.getResponse().getPath());
    }

    @Test
    public void createACompanyWithParent() throws Exception {
        ResponseDto<Long> parentResponseDto = jsonToObject(callCreateCompanyApi(CompanyDto.builder().name("parent").build()), new TypeReference<ResponseDto<Long>>() {
        });
        CompanyDto parentCompany = new CompanyDto();
        parentCompany.setId(parentResponseDto.getResponse());
        CompanyDto childCompany = CompanyDto.builder().name("child").parent(parentCompany).build();
        ResponseDto<Long> childResponse = jsonToObject(callCreateCompanyApi(childCompany), new TypeReference<ResponseDto<Long>>() {
        });
        ResponseDto<CompanyDto> findResponse = jsonToObject(callFindByIdCompany(childResponse.getResponse()), new TypeReference<ResponseDto<CompanyDto>>() {
        });

        Assertions.assertEquals("child", findResponse.getResponse().getName());
        Assertions.assertEquals("0," + findResponse.getResponse().getParent().getId(), findResponse.getResponse().getPath());
        Assertions.assertEquals("parent", findResponse.getResponse().getParent().getName());
    }

    @Test
    public void canNotCreateDuplicateCompanyName() throws Exception {
        String jsonResponse = callCreateCompanyApi(CompanyDto.builder().name("test1").build());
        ResponseDto<Long> responseDto = jsonToObject(jsonResponse, new TypeReference<ResponseDto<Long>>() {
        });
        mockMvc.perform(MockMvcRequestBuilders.post("/api/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonify(CompanyDto.builder().name("test1").build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseType", is(ResponseType.EXCEPTION.getValue())))
                .andExpect(jsonPath("$.response.statusCode", is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andReturn().getResponse().getContentAsString();
    }

    @AfterEach
    public void tearDown() {
        companyRepository.deleteAll();
    }

    private String callFindByIdCompany(Long id) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get("/api/company/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseType", is(ResponseType.GENERAL.getValue())))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.response.name").exists())
                .andExpect(jsonPath("$.response.id").exists())
                .andReturn().getResponse().getContentAsString();
    }

    private String callCreateCompanyApi(CompanyDto companyDto) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonify(companyDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseType", is(ResponseType.GENERAL.getValue())))
                .andExpect(jsonPath("$.response").exists())
                .andReturn().getResponse().getContentAsString();
    }
}
