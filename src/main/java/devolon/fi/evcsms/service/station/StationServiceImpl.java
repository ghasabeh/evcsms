package devolon.fi.evcsms.service.station;

import devolon.fi.evcsms.mapper.StationMapper;
import devolon.fi.evcsms.model.dto.StationDto;
import devolon.fi.evcsms.model.entity.CompanyEntity;
import devolon.fi.evcsms.model.entity.StationEntity;
import devolon.fi.evcsms.repository.StationRepository;
import devolon.fi.evcsms.utils.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService{
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
            throw new EntityNotFoundException("Company Not Found");
        }
    }

    @Override
    public void update(StationDto dto) {
        stationRepository.save(stationMapper.map(dto));
    }

    @Override
    public StationDto findById(Long id) {
        return stationMapper.map(stationRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void deleteById(Long id) {
        stationRepository.deleteById(id);
    }
}
