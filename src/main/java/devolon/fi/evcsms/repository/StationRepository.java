package devolon.fi.evcsms.repository;

import devolon.fi.evcsms.model.entity.StationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Repository
public interface StationRepository extends JpaRepository<StationEntity, Long> {
    @Query("SELECT S FROM StationEntity S INNER JOIN S.company C where C.id = :companyId or C.path like concat('%,',:companyId,',%') or C.path like concat('%,',:companyId)")
    List<StationEntity> getAllStationOfCompany(@Param("companyId") Long companyId, Pageable pageable);
}
