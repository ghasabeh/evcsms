package devolon.fi.evcsms.service;

import devolon.fi.evcsms.model.dto.BaseDto;
import devolon.fi.evcsms.model.entity.BaseEntity;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
public interface BaseCrudService<E extends BaseEntity, D extends BaseDto> {
    Long create(D dto);

    void update(D dto);

    D findById(Long id);

    void deleteById(Long id);
}
