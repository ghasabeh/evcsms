package devolon.fi.evcsms.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
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
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "ID", nullable = false)
    private CompanyEntity company;

}
