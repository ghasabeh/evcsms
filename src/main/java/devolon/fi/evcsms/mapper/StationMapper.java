package devolon.fi.evcsms.mapper;

import devolon.fi.evcsms.model.dto.StationDto;
import devolon.fi.evcsms.model.entity.StationEntity;
import org.mapstruct.Mapper;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Mapper(componentModel = "spring")
public interface StationMapper extends BaseMapper<StationEntity, StationDto> {
}
