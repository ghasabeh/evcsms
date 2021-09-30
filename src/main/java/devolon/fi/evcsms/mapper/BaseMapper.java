package devolon.fi.evcsms.mapper;

import devolon.fi.evcsms.model.dto.BaseDto;
import devolon.fi.evcsms.model.entity.BaseEntity;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
public interface BaseMapper<E extends BaseEntity, D extends BaseDto> {
    E map(D dto);

    D map(E entity);
}
