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
    Integer distant_unit_km = 6371;

    @Query("SELECT S FROM StationEntity S INNER JOIN S.company C where C.id = :companyId or C.path like concat('%,',:companyId,',%') or C.path like concat('%,',:companyId)")
    List<StationEntity> getAllStationOfCompany(@Param("companyId") Long companyId, Pageable pageable);

    @Query("SELECT Station , ( :distant_unit_km * FUNCTION('acos', (FUNCTION('cos', FUNCTION('radians', :latitude) ) * FUNCTION('cos',FUNCTION('radians', Station.latitude)) * FUNCTION('cos', (FUNCTION('radians',Station.longitude) - FUNCTION('radians',:longitude))) + FUNCTION('sin', FUNCTION('radians',:latitude)) * FUNCTION('sin', FUNCTION('radians', Station.latitude)))) ) AS distance FROM StationEntity Station where (6371 * FUNCTION('acos', (FUNCTION('cos', FUNCTION('radians', :latitude)) * FUNCTION('cos',FUNCTION('radians',Station.latitude)) * FUNCTION('cos', (FUNCTION('radians',Station.longitude) - FUNCTION('radians',:longitude))) + FUNCTION('sin', FUNCTION('radians',:latitude)) * FUNCTION('sin', FUNCTION('radians', Station.latitude))))) < :distance_in_kilometers ORDER BY distance")
    List<StationEntity> getNearestStationsOfDistance(@Param("latitude") Double latitude,
                                                     @Param("longitude") Double longitude,
                                                     @Param("distance_in_kilometers") Double distanceInKilometers,
                                                     @Param("distant_unit_km") Double distanceUnitKM,
                                                     Pageable pageable);
}
