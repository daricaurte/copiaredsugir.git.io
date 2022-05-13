package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Potential.
 */
@Entity
@Table(name = "potential")
public class Potential implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "exchange_partner", nullable = false)
    private Boolean exchangePartner;

    @NotNull
    @Column(name = "consultant", nullable = false)
    private Boolean consultant;

    @NotNull
    @Column(name = "client", nullable = false)
    private Boolean client;

    @NotNull
    @Column(name = "collaborator", nullable = false)
    private Boolean collaborator;

    @NotNull
    @Column(name = "provider", nullable = false)
    private Boolean provider;

    @NotNull
    @Column(name = "referrer", nullable = false)
    private Boolean referrer;

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

    public Potential id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getExchangePartner() {
        return this.exchangePartner;
    }

    public Potential exchangePartner(Boolean exchangePartner) {
        this.setExchangePartner(exchangePartner);
        return this;
    }

    public void setExchangePartner(Boolean exchangePartner) {
        this.exchangePartner = exchangePartner;
    }

    public Boolean getConsultant() {
        return this.consultant;
    }

    public Potential consultant(Boolean consultant) {
        this.setConsultant(consultant);
        return this;
    }

    public void setConsultant(Boolean consultant) {
        this.consultant = consultant;
    }

    public Boolean getClient() {
        return this.client;
    }

    public Potential client(Boolean client) {
        this.setClient(client);
        return this;
    }

    public void setClient(Boolean client) {
        this.client = client;
    }

    public Boolean getCollaborator() {
        return this.collaborator;
    }

    public Potential collaborator(Boolean collaborator) {
        this.setCollaborator(collaborator);
        return this;
    }

    public void setCollaborator(Boolean collaborator) {
        this.collaborator = collaborator;
    }

    public Boolean getProvider() {
        return this.provider;
    }

    public Potential provider(Boolean provider) {
        this.setProvider(provider);
        return this;
    }

    public void setProvider(Boolean provider) {
        this.provider = provider;
    }

    public Boolean getReferrer() {
        return this.referrer;
    }

    public Potential referrer(Boolean referrer) {
        this.setReferrer(referrer);
        return this;
    }

    public void setReferrer(Boolean referrer) {
        this.referrer = referrer;
    }

    public ContactData getContactData() {
        return this.contactData;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }

    public Potential contactData(ContactData contactData) {
        this.setContactData(contactData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Potential)) {
            return false;
        }
        return id != null && id.equals(((Potential) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Potential{" +
            "id=" + getId() +
            ", exchangePartner='" + getExchangePartner() + "'" +
            ", consultant='" + getConsultant() + "'" +
            ", client='" + getClient() + "'" +
            ", collaborator='" + getCollaborator() + "'" +
            ", provider='" + getProvider() + "'" +
            ", referrer='" + getReferrer() + "'" +
            "}";
    }
}
