package devolon.fi.evcsms.model.entity;

import devolon.fi.evcsms.repository.CompanyRepository;
import devolon.fi.evcsms.utils.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Component
@RequiredArgsConstructor
public class CompanyTreePathMaker {
    private static final String PATH_SEPARATOR = ",";

    private final CompanyRepository repository;

    public void prePersist(CompanyEntity entity) {
        entity.setParent(repository.findById(entity.getParent().getId()).orElseThrow(EntityNotFoundException::new));
        entity.setPath(Objects.nonNull(entity.getParent()) ?
                entity.getParent().getPath() + PATH_SEPARATOR + entity.getParent().getId() : "0");
    }

    public void preUpdate(CompanyEntity entity) {
        // we sure findById is not null
        CompanyEntity oldEntity = repository.findById(entity.getId()).orElseThrow(EntityNotFoundException::new);
        // This means if parent change, we should update all paths
        if ((Objects.isNull(oldEntity.getParent()) && Objects.nonNull(entity.getParent())) || !oldEntity.getParent().equals(entity.getParent())) {
            if (Objects.nonNull(entity.getParent())) {
                //we should call find by id for parent and set it manually
                entity.setParent(repository.findById(entity.getParent().getId()).orElseThrow(EntityNotFoundException::new));
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
