package devolon.fi.evcsms.model.entity;

import devolon.fi.evcsms.repository.CompanyRepository;
import devolon.fi.evcsms.utils.exception.CustomEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Component
@RequiredArgsConstructor
public class CompanyTreePathMaker {
    private static final String PATH_SEPARATOR = ",";

    private final CompanyRepository repository;

    @Transactional
    public void prePersist(CompanyEntity entity) {
        if (Objects.nonNull(entity.getParent())) {
            if (Objects.isNull(entity.getParent().getId())) {
                throw new CustomEntityNotFoundException();
            }
            entity.setParent(repository.findById(entity.getParent().getId()).orElseThrow(CustomEntityNotFoundException::new));
        }
        entity.setPath(Objects.nonNull(entity.getParent()) ?
                    entity.getParent().getPath() + PATH_SEPARATOR + entity.getParent().getId() : "0");
    }

    @Transactional
    public void preUpdate(CompanyEntity entity) {
        // we sure findById is not null
        CompanyEntity oldEntity = repository.findById(entity.getId()).orElseThrow(CustomEntityNotFoundException::new);
        // This means if parent change, we should update all paths
        if (Objects.isNull(oldEntity.getParent()) || (Objects.nonNull(entity.getParent()) && !oldEntity.getParent().equals(entity.getParent()))) {
            if (Objects.nonNull(entity.getParent())) {
                //we should call find by id for parent and set it manually
                entity.setParent(repository.findById(entity.getParent().getId()).orElseThrow(CustomEntityNotFoundException::new));
            }
            //because newEntity is not managed then children not loaded
            //so we should load children from oldEntity in order to set their path
            entity.setChildren(oldEntity.getChildren());
            entity.setPath(Objects.nonNull(entity.getParent()) ?
                    entity.getParent().getPath() + PATH_SEPARATOR + entity.getParent().getId() : "0");
            updateChildrenPath(entity, entity.getPath());
        }
    }

    private void updateChildrenPath(CompanyEntity entity, String newPath) {
        for (CompanyEntity child : entity.getChildren()) {
            child.setPath(newPath + PATH_SEPARATOR +
                    (Objects.nonNull(child.getParent()) ? child.getParent().getId() : "0"));
            updateChildrenPath(child, child.getPath());
        }
    }

}
