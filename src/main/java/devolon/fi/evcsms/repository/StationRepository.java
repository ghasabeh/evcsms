package devolon.fi.evcsms.repository;

import devolon.fi.evcsms.model.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Repository
public interface StationRepository extends JpaRepository<StationEntity, Long> {
}
