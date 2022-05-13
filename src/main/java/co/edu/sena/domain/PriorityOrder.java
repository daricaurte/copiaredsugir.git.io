package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.Designation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PriorityOrder.
 */
@Entity
@Table(name = "priority_order")
public class PriorityOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "qualif_logarit", nullable = false)
    private Designation qualifLogarit;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "qualif_affiliate", nullable = false)
    private Designation qualifAffiliate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "qualif_definitive", nullable = false)
    private Designation qualifDefinitive;

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

    public PriorityOrder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Designation getQualifLogarit() {
        return this.qualifLogarit;
    }

    public PriorityOrder qualifLogarit(Designation qualifLogarit) {
        this.setQualifLogarit(qualifLogarit);
        return this;
    }

    public void setQualifLogarit(Designation qualifLogarit) {
        this.qualifLogarit = qualifLogarit;
    }

    public Designation getQualifAffiliate() {
        return this.qualifAffiliate;
    }

    public PriorityOrder qualifAffiliate(Designation qualifAffiliate) {
        this.setQualifAffiliate(qualifAffiliate);
        return this;
    }

    public void setQualifAffiliate(Designation qualifAffiliate) {
        this.qualifAffiliate = qualifAffiliate;
    }

    public Designation getQualifDefinitive() {
        return this.qualifDefinitive;
    }

    public PriorityOrder qualifDefinitive(Designation qualifDefinitive) {
        this.setQualifDefinitive(qualifDefinitive);
        return this;
    }

    public void setQualifDefinitive(Designation qualifDefinitive) {
        this.qualifDefinitive = qualifDefinitive;
    }

    public ContactData getContactData() {
        return this.contactData;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }

    public PriorityOrder contactData(ContactData contactData) {
        this.setContactData(contactData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PriorityOrder)) {
            return false;
        }
        return id != null && id.equals(((PriorityOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PriorityOrder{" +
            "id=" + getId() +
            ", qualifLogarit='" + getQualifLogarit() + "'" +
            ", qualifAffiliate='" + getQualifAffiliate() + "'" +
            ", qualifDefinitive='" + getQualifDefinitive() + "'" +
            "}";
    }
}
