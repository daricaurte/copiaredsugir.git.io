package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A CompanyType.
 */
@Entity
@Table(name = "company_type")
public class CompanyType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "primary_sector")
    private Boolean primarySector;

    @Column(name = "secondary_sector")
    private Boolean secondarySector;

    @Column(name = "tertiary_sector")
    private Boolean tertiarySector;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "companyTypes", "relations", "contacts", "networks", "qualifications", "priorityOrders", "potentials", "affiliate" },
        allowSetters = true
    )
    private ContactData contactData;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CompanyType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPrimarySector() {
        return this.primarySector;
    }

    public CompanyType primarySector(Boolean primarySector) {
        this.setPrimarySector(primarySector);
        return this;
    }

    public void setPrimarySector(Boolean primarySector) {
        this.primarySector = primarySector;
    }

    public Boolean getSecondarySector() {
        return this.secondarySector;
    }

    public CompanyType secondarySector(Boolean secondarySector) {
        this.setSecondarySector(secondarySector);
        return this;
    }

    public void setSecondarySector(Boolean secondarySector) {
        this.secondarySector = secondarySector;
    }

    public Boolean getTertiarySector() {
        return this.tertiarySector;
    }

    public CompanyType tertiarySector(Boolean tertiarySector) {
        this.setTertiarySector(tertiarySector);
        return this;
    }

    public void setTertiarySector(Boolean tertiarySector) {
        this.tertiarySector = tertiarySector;
    }

    public ContactData getContactData() {
        return this.contactData;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }

    public CompanyType contactData(ContactData contactData) {
        this.setContactData(contactData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyType)) {
            return false;
        }
        return id != null && id.equals(((CompanyType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyType{" +
            "id=" + getId() +
            ", primarySector='" + getPrimarySector() + "'" +
            ", secondarySector='" + getSecondarySector() + "'" +
            ", tertiarySector='" + getTertiarySector() + "'" +
            "}";
    }
}
