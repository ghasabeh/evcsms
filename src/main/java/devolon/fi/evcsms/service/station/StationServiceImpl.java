package devolon.fi.evcsms.service.station;

import devolon.fi.evcsms.mapper.StationMapper;
import devolon.fi.evcsms.model.dto.LocationDto;
import devolon.fi.evcsms.model.dto.StationDto;
import devolon.fi.evcsms.model.entity.CompanyEntity;
import devolon.fi.evcsms.model.entity.StationEntity;
import devolon.fi.evcsms.model.enums.station.UnitInCalculationDistanceBetweenTwoPoints;
import devolon.fi.evcsms.repository.StationRepository;
import devolon.fi.evcsms.utils.exception.CustomEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    @Override
    public Long create(StationDto dto) {
        StationEntity stationEntity = stationMapper.map(dto);
        validateCompanyId(stationEntity.getCompany());
        return stationRepository.save(stationEntity).getId();
    }

    private void validateCompanyId(CompanyEntity company) {
        if (Objects.isNull(company.getId())) {
            throw new CustomEntityNotFoundException("Company Not Found");
        }
    }

    @Override
    public void update(StationDto dto) {
        stationRepository.save(stationMapper.map(dto));
    }

    @Override
    public StationDto findById(Long id) {
        return stationMapper.map(stationRepository.findById(id).orElseThrow(CustomEntityNotFoundException::new));
    }

    @Override
    public void deleteById(Long id) {
        stationRepository.deleteById(id);
    }

    @Override
    public List<StationDto> getAllStationOfCompany(Long companyId, int page, int size) {
        return stationRepository.getAllStationOfCompany(companyId, PageRequest.of(page, size))
                .stream().map(stationMapper::map).collect(Collectors.toList());
    }

    @Override
    public List<StationDto> nearestLocation(LocationDto location, Integer page, Integer size) {
        return stationRepository.getNearestStationsOfDistance(
                Double.parseDouble(location.getLatitude()),
                Double.parseDouble(location.getLongitude()),
                location.getDistance(),
                UnitInCalculationDistanceBetweenTwoPoints.KILOMETER.getValue(),
                PageRequest.of(page, size)).stream().map(stationMapper::map).collect(Collectors.toList());
    }

}
