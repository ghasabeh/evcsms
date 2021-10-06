package devolon.fi.evcsms.mapper;

import devolon.fi.evcsms.model.dto.StationDto;
import devolon.fi.evcsms.model.entity.StationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Mapper(componentModel = "spring", uses = CompanyMapper.class)
public interface StationMapper extends BaseMapper<StationEntity, StationDto> {
    @Mappings({
            @Mapping(target = "company", expression = "java(companyMapper.mapWithoutParent(entity.getCompany()))")
    })
    @Override
    StationDto map(StationEntity entity);
}
