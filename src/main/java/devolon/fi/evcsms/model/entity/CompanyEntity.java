package devolon.fi.evcsms.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "COMPANY", uniqueConstraints = {
        @UniqueConstraint(columnNames = "NAME", name = "company_uk_name")
})
@SequenceGenerator(name = "DEFAULT_SEQ_GEN", sequenceName = "COMPANY_SEQ", allocationSize = 1)
@Setter
@Getter
public class CompanyEntity extends BaseEntity{
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PATH", nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "company_company_fk"))
    private CompanyEntity parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<CompanyEntity> children;
}
