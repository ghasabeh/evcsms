package devolon.fi.evcsms.controller.station;

import com.fasterxml.jackson.core.type.TypeReference;
import devolon.fi.evcsms.controller.StationController;
import devolon.fi.evcsms.model.dto.LocationDto;
import devolon.fi.evcsms.model.dto.StationDto;
import devolon.fi.evcsms.model.dto.response.ResponseDto;
import devolon.fi.evcsms.model.dto.response.ResponseType;
import devolon.fi.evcsms.repository.CompanyRepository;
import devolon.fi.evcsms.repository.StationRepository;
import devolon.fi.evcsms.utils.exception.BaseExceptionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static devolon.fi.evcsms.util.JsonHelper.jsonToObject;
import static devolon.fi.evcsms.util.JsonHelper.jsonify;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StationControllerIT {
    private MockMvc mockMvc;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private StationController stationController;
    @Autowired
    private BaseExceptionHandler baseExceptionHandler;

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(this.stationController, this.baseExceptionHandler).build();
    }

    @Sql({"/company.sql", "/station.sql"})
    @Test
    public void allStationsOfCompanyIncludingChildCompany() throws Exception {
        List<StationDto> stationBelongsToA = callApiGetStationsBelongsToCompany(1L);
        List<StationDto> stationBelongsToB = callApiGetStationsBelongsToCompany(2L);
        List<StationDto> stationBelongsToC = callApiGetStationsBelongsToCompany(3L);

        assertTrue(stationBelongsToA.stream().map(StationDto::getName).toList()
                .containsAll(Arrays.asList("sa1", "sa2", "sa2", "sb1", "sb2", "sc1", "sc2", "sc3")));
        assertEquals(8, stationBelongsToA.size());

        assertTrue(stationBelongsToB.stream().map(StationDto::getName).toList()
                .containsAll(Arrays.asList("sb1", "sb2", "sc1", "sc2", "sc3")));
        assertEquals(5, stationBelongsToB.size());

        assertTrue(stationBelongsToC.stream().map(StationDto::getName).toList()
                .containsAll(Arrays.asList("sc1", "sc2", "sc3")));
        assertEquals(3, stationBelongsToC.size());
    }

    private List<StationDto> callApiGetStationsBelongsToCompany(Long id) throws Exception {
        String jsonResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/station/company/{companyId}", id)
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseType", is(ResponseType.GENERAL.getValue())))
                .andReturn().getResponse().getContentAsString();
        return jsonToObject(jsonResponse, new TypeReference<ResponseDto<List<StationDto>>>() {
        }).getResponse();
    }

    /*
     | Station Name | Distance From (0,0) point |
     |--------------|---------------------------|
     | sa1          | 14  Km                    |
     | sa2          | 19  Km                    |
     | sa3          | 20  Km                    |
     | sb1          | 51  Km                    |
     | sb2          | 76  Km                    |
     | sc1          | 417 Km                    |
     | sc2          | 574 Km                    |
     | sc3          | 731 Km                    |
     |--------------|---------------------------|
     * This distances compute approximately from website : NATIONAL HURRICANE CENTER and CENTRAL PACIFIC HURRICANE CENTER
     * https://www.nhc.noaa.gov/gccalc.shtml
    **/
    @Sql({"/company.sql", "/station.sql"})
    @Test
    public void getAllStationsDistantFromAPointWithinRadiusOf() throws Exception {
        List<StationDto> stationsNearZeroWithRadius20km = callApiGetAllStationsDistantFromAPointWithinRadiusOf(new LocationDto("0.000000", "0.000000", 20D));
        List<StationDto> stationsNearZeroWithRadius80km = callApiGetAllStationsDistantFromAPointWithinRadiusOf(new LocationDto("0.000000", "0.000000", 80D));
        List<StationDto> stationsNearZeroWithRadius600km = callApiGetAllStationsDistantFromAPointWithinRadiusOf(new LocationDto("0.000000", "0.000000", 600D));

        assertTrue(stationsNearZeroWithRadius20km.stream().map(StationDto::getName).toList()
                .containsAll(Arrays.asList("sa1", "sa2", "sa2")));
        assertEquals(3, stationsNearZeroWithRadius20km.size());

        assertTrue(stationsNearZeroWithRadius80km.stream().map(StationDto::getName).toList()
                .containsAll(Arrays.asList("sa1", "sa2", "sa2", "sb1", "sb2")));
        assertEquals(5, stationsNearZeroWithRadius80km.size());

        assertTrue(stationsNearZeroWithRadius600km.stream().map(StationDto::getName).toList()
                .containsAll(Arrays.asList("sa1", "sa2", "sa2", "sb1", "sb2", "sc1", "sc2")));
        assertEquals(7, stationsNearZeroWithRadius600km.size());
    }

    private List<StationDto> callApiGetAllStationsDistantFromAPointWithinRadiusOf(LocationDto locationDto) throws Exception {
        String jsonResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/station/nearest-location")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonify(locationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseType", is(ResponseType.GENERAL.getValue())))
                .andReturn().getResponse().getContentAsString();
        return jsonToObject(jsonResponse, new TypeReference<ResponseDto<List<StationDto>>>() {
        }).getResponse();
    }

    @AfterEach
    public void tearDown() {
        stationRepository.deleteAll();
        companyRepository.deleteAll();
    }
}
