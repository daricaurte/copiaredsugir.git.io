package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.Designation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Qualification.
 */
@Entity
@Table(name = "qualification")
public class Qualification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "qualif_1", nullable = false)
    private Designation qualif1;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "qualif_2", nullable = false)
    private Designation qualif2;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "qualif_3", nullable = false)
    private Designation qualif3;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "qualif_4", nullable = false)
    private Designation qualif4;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "qualif_1_bussiness_viability", nullable = false)
    private Designation qualif1BussinessViability;

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

    public Qualification id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Designation getQualif1() {
        return this.qualif1;
    }

    public Qualification qualif1(Designation qualif1) {
        this.setQualif1(qualif1);
        return this;
    }

    public void setQualif1(Designation qualif1) {
        this.qualif1 = qualif1;
    }

    public Designation getQualif2() {
        return this.qualif2;
    }

    public Qualification qualif2(Designation qualif2) {
        this.setQualif2(qualif2);
        return this;
    }

    public void setQualif2(Designation qualif2) {
        this.qualif2 = qualif2;
    }

    public Designation getQualif3() {
        return this.qualif3;
    }

    public Qualification qualif3(Designation qualif3) {
        this.setQualif3(qualif3);
        return this;
    }

    public void setQualif3(Designation qualif3) {
        this.qualif3 = qualif3;
    }

    public Designation getQualif4() {
        return this.qualif4;
    }

    public Qualification qualif4(Designation qualif4) {
        this.setQualif4(qualif4);
        return this;
    }

    public void setQualif4(Designation qualif4) {
        this.qualif4 = qualif4;
    }

    public Designation getQualif1BussinessViability() {
        return this.qualif1BussinessViability;
    }

    public Qualification qualif1BussinessViability(Designation qualif1BussinessViability) {
        this.setQualif1BussinessViability(qualif1BussinessViability);
        return this;
    }

    public void setQualif1BussinessViability(Designation qualif1BussinessViability) {
        this.qualif1BussinessViability = qualif1BussinessViability;
    }

    public ContactData getContactData() {
        return this.contactData;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }

    public Qualification contactData(ContactData contactData) {
        this.setContactData(contactData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Qualification)) {
            return false;
        }
        return id != null && id.equals(((Qualification) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Qualification{" +
            "id=" + getId() +
            ", qualif1='" + getQualif1() + "'" +
            ", qualif2='" + getQualif2() + "'" +
            ", qualif3='" + getQualif3() + "'" +
            ", qualif4='" + getQualif4() + "'" +
            ", qualif1BussinessViability='" + getQualif1BussinessViability() + "'" +
            "}";
    }
}
