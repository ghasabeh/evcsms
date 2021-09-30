package devolon.fi.evcsms.mapper;

import devolon.fi.evcsms.model.dto.CompanyDto;
import devolon.fi.evcsms.model.entity.CompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Mapper(componentModel = "spring")
public interface CompanyMapper extends BaseMapper<CompanyEntity, CompanyDto> {

    @Mappings({
            @Mapping(target = "parent", qualifiedByName = "mapWithoutParent")
    })
    @Override
    CompanyDto map(CompanyEntity entity);

    @Named(value = "mapWithoutParent")
    @Mapping(target = "parent", ignore = true)
    CompanyDto mapWithoutParent(CompanyEntity entity);
}
