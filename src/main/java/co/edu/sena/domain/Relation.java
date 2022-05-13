package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Relation.
 */
@Entity
@Table(name = "relation")
public class Relation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "family_relation", nullable = false)
    private Boolean familyRelation;

    @NotNull
    @Column(name = "work_relation", nullable = false)
    private Boolean workRelation;

    @NotNull
    @Column(name = "personal_relation", nullable = false)
    private Boolean personalRelation;

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

    public Relation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFamilyRelation() {
        return this.familyRelation;
    }

    public Relation familyRelation(Boolean familyRelation) {
        this.setFamilyRelation(familyRelation);
        return this;
    }

    public void setFamilyRelation(Boolean familyRelation) {
        this.familyRelation = familyRelation;
    }

    public Boolean getWorkRelation() {
        return this.workRelation;
    }

    public Relation workRelation(Boolean workRelation) {
        this.setWorkRelation(workRelation);
        return this;
    }

    public void setWorkRelation(Boolean workRelation) {
        this.workRelation = workRelation;
    }

    public Boolean getPersonalRelation() {
        return this.personalRelation;
    }

    public Relation personalRelation(Boolean personalRelation) {
        this.setPersonalRelation(personalRelation);
        return this;
    }

    public void setPersonalRelation(Boolean personalRelation) {
        this.personalRelation = personalRelation;
    }

    public ContactData getContactData() {
        return this.contactData;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }

    public Relation contactData(ContactData contactData) {
        this.setContactData(contactData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Relation)) {
            return false;
        }
        return id != null && id.equals(((Relation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Relation{" +
            "id=" + getId() +
            ", familyRelation='" + getFamilyRelation() + "'" +
            ", workRelation='" + getWorkRelation() + "'" +
            ", personalRelation='" + getPersonalRelation() + "'" +
            "}";
    }
}
