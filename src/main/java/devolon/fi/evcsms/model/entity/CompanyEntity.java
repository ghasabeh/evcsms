package devolon.fi.evcsms.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class CompanyEntity extends BaseEntity{
    @Column(name = "NAME", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private CompanyEntity parent;
}
