package devolon.fi.evcsms.service.station;

import devolon.fi.evcsms.model.dto.StationDto;
import devolon.fi.evcsms.model.entity.StationEntity;
import devolon.fi.evcsms.service.BaseCrudService;

import java.util.List;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
public interface StationService extends BaseCrudService<StationEntity, StationDto> {
    List<StationDto> getAllStationOfCompany(Long companyId, int page, int size);
}
