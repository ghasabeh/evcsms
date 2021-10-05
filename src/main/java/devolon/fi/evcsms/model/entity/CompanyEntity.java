package devolon.fi.evcsms.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Company", uniqueConstraints = {
        @UniqueConstraint(columnNames = "NAME", name = "company_uk_name")
})
@Setter
@Getter
public class CompanyEntity extends BaseEntity{
    @Column(name = "NAME", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "company_company_fk"))
    private CompanyEntity parent;
}
