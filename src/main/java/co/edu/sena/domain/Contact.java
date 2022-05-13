package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "contact_3rd_sector_hl", nullable = false)
    private Boolean contact3rdSectorHl;

    @NotNull
    @Column(name = "contact_academy_hl", nullable = false)
    private Boolean contactAcademyHl;

    @NotNull
    @Column(name = "contact_teeth_hl", nullable = false)
    private Boolean contactTeethHl;

    @NotNull
    @Column(name = "contact_employee_hl", nullable = false)
    private Boolean contactEmployeeHl;

    @NotNull
    @Column(name = "contact_ent_finan_hl", nullable = false)
    private Boolean contactEntFinanHl;

    @NotNull
    @Column(name = "contact_status_hl", nullable = false)
    private Boolean contactStatusHl;

    @NotNull
    @Column(name = "international_contact_hl", nullable = false)
    private Boolean internationalContactHl;

    @NotNull
    @Column(name = "contact_media_comunc_hl", nullable = false)
    private Boolean contactMediaComuncHl;

    @NotNull
    @Column(name = "contact_org_community_hl", nullable = false)
    private Boolean contactOrgCommunityHl;

    @NotNull
    @Column(name = "contact_regulatory_organisms_hl", nullable = false)
    private Boolean contactRegulatoryOrganismsHl;

    @NotNull
    @Column(name = "contact_proveedores_hl", nullable = false)
    private Boolean contactProveedoresHl;

    @NotNull
    @Column(name = "contact_social_networks", nullable = false)
    private Boolean contactSocialNetworks;

    @NotNull
    @Column(name = "contact_shareholders_hl", nullable = false)
    private Boolean contactShareholdersHl;

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

    public Contact id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getContact3rdSectorHl() {
        return this.contact3rdSectorHl;
    }

    public Contact contact3rdSectorHl(Boolean contact3rdSectorHl) {
        this.setContact3rdSectorHl(contact3rdSectorHl);
        return this;
    }

    public void setContact3rdSectorHl(Boolean contact3rdSectorHl) {
        this.contact3rdSectorHl = contact3rdSectorHl;
    }

    public Boolean getContactAcademyHl() {
        return this.contactAcademyHl;
    }

    public Contact contactAcademyHl(Boolean contactAcademyHl) {
        this.setContactAcademyHl(contactAcademyHl);
        return this;
    }

    public void setContactAcademyHl(Boolean contactAcademyHl) {
        this.contactAcademyHl = contactAcademyHl;
    }

    public Boolean getContactTeethHl() {
        return this.contactTeethHl;
    }

    public Contact contactTeethHl(Boolean contactTeethHl) {
        this.setContactTeethHl(contactTeethHl);
        return this;
    }

    public void setContactTeethHl(Boolean contactTeethHl) {
        this.contactTeethHl = contactTeethHl;
    }

    public Boolean getContactEmployeeHl() {
        return this.contactEmployeeHl;
    }

    public Contact contactEmployeeHl(Boolean contactEmployeeHl) {
        this.setContactEmployeeHl(contactEmployeeHl);
        return this;
    }

    public void setContactEmployeeHl(Boolean contactEmployeeHl) {
        this.contactEmployeeHl = contactEmployeeHl;
    }

    public Boolean getContactEntFinanHl() {
        return this.contactEntFinanHl;
    }

    public Contact contactEntFinanHl(Boolean contactEntFinanHl) {
        this.setContactEntFinanHl(contactEntFinanHl);
        return this;
    }

    public void setContactEntFinanHl(Boolean contactEntFinanHl) {
        this.contactEntFinanHl = contactEntFinanHl;
    }

    public Boolean getContactStatusHl() {
        return this.contactStatusHl;
    }

    public Contact contactStatusHl(Boolean contactStatusHl) {
        this.setContactStatusHl(contactStatusHl);
        return this;
    }

    public void setContactStatusHl(Boolean contactStatusHl) {
        this.contactStatusHl = contactStatusHl;
    }

    public Boolean getInternationalContactHl() {
        return this.internationalContactHl;
    }

    public Contact internationalContactHl(Boolean internationalContactHl) {
        this.setInternationalContactHl(internationalContactHl);
        return this;
    }

    public void setInternationalContactHl(Boolean internationalContactHl) {
        this.internationalContactHl = internationalContactHl;
    }

    public Boolean getContactMediaComuncHl() {
        return this.contactMediaComuncHl;
    }

    public Contact contactMediaComuncHl(Boolean contactMediaComuncHl) {
        this.setContactMediaComuncHl(contactMediaComuncHl);
        return this;
    }

    public void setContactMediaComuncHl(Boolean contactMediaComuncHl) {
        this.contactMediaComuncHl = contactMediaComuncHl;
    }

    public Boolean getContactOrgCommunityHl() {
        return this.contactOrgCommunityHl;
    }

    public Contact contactOrgCommunityHl(Boolean contactOrgCommunityHl) {
        this.setContactOrgCommunityHl(contactOrgCommunityHl);
        return this;
    }

    public void setContactOrgCommunityHl(Boolean contactOrgCommunityHl) {
        this.contactOrgCommunityHl = contactOrgCommunityHl;
    }

    public Boolean getContactRegulatoryOrganismsHl() {
        return this.contactRegulatoryOrganismsHl;
    }

    public Contact contactRegulatoryOrganismsHl(Boolean contactRegulatoryOrganismsHl) {
        this.setContactRegulatoryOrganismsHl(contactRegulatoryOrganismsHl);
        return this;
    }

    public void setContactRegulatoryOrganismsHl(Boolean contactRegulatoryOrganismsHl) {
        this.contactRegulatoryOrganismsHl = contactRegulatoryOrganismsHl;
    }

    public Boolean getContactProveedoresHl() {
        return this.contactProveedoresHl;
    }

    public Contact contactProveedoresHl(Boolean contactProveedoresHl) {
        this.setContactProveedoresHl(contactProveedoresHl);
        return this;
    }

    public void setContactProveedoresHl(Boolean contactProveedoresHl) {
        this.contactProveedoresHl = contactProveedoresHl;
    }

    public Boolean getContactSocialNetworks() {
        return this.contactSocialNetworks;
    }

    public Contact contactSocialNetworks(Boolean contactSocialNetworks) {
        this.setContactSocialNetworks(contactSocialNetworks);
        return this;
    }

    public void setContactSocialNetworks(Boolean contactSocialNetworks) {
        this.contactSocialNetworks = contactSocialNetworks;
    }

    public Boolean getContactShareholdersHl() {
        return this.contactShareholdersHl;
    }

    public Contact contactShareholdersHl(Boolean contactShareholdersHl) {
        this.setContactShareholdersHl(contactShareholdersHl);
        return this;
    }

    public void setContactShareholdersHl(Boolean contactShareholdersHl) {
        this.contactShareholdersHl = contactShareholdersHl;
    }

    public ContactData getContactData() {
        return this.contactData;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }

    public Contact contactData(ContactData contactData) {
        this.setContactData(contactData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contact)) {
            return false;
        }
        return id != null && id.equals(((Contact) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", contact3rdSectorHl='" + getContact3rdSectorHl() + "'" +
            ", contactAcademyHl='" + getContactAcademyHl() + "'" +
            ", contactTeethHl='" + getContactTeethHl() + "'" +
            ", contactEmployeeHl='" + getContactEmployeeHl() + "'" +
            ", contactEntFinanHl='" + getContactEntFinanHl() + "'" +
            ", contactStatusHl='" + getContactStatusHl() + "'" +
            ", internationalContactHl='" + getInternationalContactHl() + "'" +
            ", contactMediaComuncHl='" + getContactMediaComuncHl() + "'" +
            ", contactOrgCommunityHl='" + getContactOrgCommunityHl() + "'" +
            ", contactRegulatoryOrganismsHl='" + getContactRegulatoryOrganismsHl() + "'" +
            ", contactProveedoresHl='" + getContactProveedoresHl() + "'" +
            ", contactSocialNetworks='" + getContactSocialNetworks() + "'" +
            ", contactShareholdersHl='" + getContactShareholdersHl() + "'" +
            "}";
    }
}
