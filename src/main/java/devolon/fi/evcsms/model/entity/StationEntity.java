package devolon.fi.evcsms.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "STATION", uniqueConstraints = {
        @UniqueConstraint(columnNames = "NAME", name = "station_uk_name"),
        @UniqueConstraint(columnNames = {"LATITUDE", "LONGITUDE"}, name = "station_uk_location")
})
@SequenceGenerator(name = "DEFAULT_SEQ_GEN", sequenceName = "STATION_SEQ", allocationSize = 1)
@Getter
@Setter
public class StationEntity extends BaseEntity {
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;
    @Column(name = "Longitude", nullable = false)
    private Double longitude;
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "ID", nullable = false,
            foreignKey = @ForeignKey(name = "station_company_fk"))
    private CompanyEntity company;

}
