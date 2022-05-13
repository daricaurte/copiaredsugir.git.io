package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Networks.
 */
@Entity
@Table(name = "networks")
public class Networks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "networks_3rd_sector_hl", nullable = false)
    private Boolean networks3rdSectorHl;

    @NotNull
    @Column(name = "academic_networks_hl", nullable = false)
    private Boolean academicNetworksHl;

    @NotNull
    @Column(name = "customer_networks_hl", nullable = false)
    private Boolean customerNetworksHl;

    @NotNull
    @Column(name = "employee_networks_hl", nullable = false)
    private Boolean employeeNetworksHl;

    @NotNull
    @Column(name = "networks_ent_finan_hl", nullable = false)
    private Boolean networksEntFinanHl;

    @NotNull
    @Column(name = "state_networks_hl", nullable = false)
    private Boolean stateNetworksHl;

    @NotNull
    @Column(name = "international_networks_hl", nullable = false)
    private Boolean internationalNetworksHl;

    @NotNull
    @Column(name = "media_networks_comunc_hl", nullable = false)
    private Boolean mediaNetworksComuncHl;

    @NotNull
    @Column(name = "community_org_networks_hl", nullable = false)
    private Boolean communityOrgNetworksHl;

    @NotNull
    @Column(name = "regulatory_organisms_networks", nullable = false)
    private Boolean regulatoryOrganismsNetworks;

    @NotNull
    @Column(name = "networks_providers_hl", nullable = false)
    private Boolean networksProvidersHl;

    @NotNull
    @Column(name = "social_networks", nullable = false)
    private Boolean socialNetworks;

    @NotNull
    @Column(name = "shareholder_networks_hl", nullable = false)
    private Boolean shareholderNetworksHl;

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

    public Networks id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getNetworks3rdSectorHl() {
        return this.networks3rdSectorHl;
    }

    public Networks networks3rdSectorHl(Boolean networks3rdSectorHl) {
        this.setNetworks3rdSectorHl(networks3rdSectorHl);
        return this;
    }

    public void setNetworks3rdSectorHl(Boolean networks3rdSectorHl) {
        this.networks3rdSectorHl = networks3rdSectorHl;
    }

    public Boolean getAcademicNetworksHl() {
        return this.academicNetworksHl;
    }

    public Networks academicNetworksHl(Boolean academicNetworksHl) {
        this.setAcademicNetworksHl(academicNetworksHl);
        return this;
    }

    public void setAcademicNetworksHl(Boolean academicNetworksHl) {
        this.academicNetworksHl = academicNetworksHl;
    }

    public Boolean getCustomerNetworksHl() {
        return this.customerNetworksHl;
    }

    public Networks customerNetworksHl(Boolean customerNetworksHl) {
        this.setCustomerNetworksHl(customerNetworksHl);
        return this;
    }

    public void setCustomerNetworksHl(Boolean customerNetworksHl) {
        this.customerNetworksHl = customerNetworksHl;
    }

    public Boolean getEmployeeNetworksHl() {
        return this.employeeNetworksHl;
    }

    public Networks employeeNetworksHl(Boolean employeeNetworksHl) {
        this.setEmployeeNetworksHl(employeeNetworksHl);
        return this;
    }

    public void setEmployeeNetworksHl(Boolean employeeNetworksHl) {
        this.employeeNetworksHl = employeeNetworksHl;
    }

    public Boolean getNetworksEntFinanHl() {
        return this.networksEntFinanHl;
    }

    public Networks networksEntFinanHl(Boolean networksEntFinanHl) {
        this.setNetworksEntFinanHl(networksEntFinanHl);
        return this;
    }

    public void setNetworksEntFinanHl(Boolean networksEntFinanHl) {
        this.networksEntFinanHl = networksEntFinanHl;
    }

    public Boolean getStateNetworksHl() {
        return this.stateNetworksHl;
    }

    public Networks stateNetworksHl(Boolean stateNetworksHl) {
        this.setStateNetworksHl(stateNetworksHl);
        return this;
    }

    public void setStateNetworksHl(Boolean stateNetworksHl) {
        this.stateNetworksHl = stateNetworksHl;
    }

    public Boolean getInternationalNetworksHl() {
        return this.internationalNetworksHl;
    }

    public Networks internationalNetworksHl(Boolean internationalNetworksHl) {
        this.setInternationalNetworksHl(internationalNetworksHl);
        return this;
    }

    public void setInternationalNetworksHl(Boolean internationalNetworksHl) {
        this.internationalNetworksHl = internationalNetworksHl;
    }

    public Boolean getMediaNetworksComuncHl() {
        return this.mediaNetworksComuncHl;
    }

    public Networks mediaNetworksComuncHl(Boolean mediaNetworksComuncHl) {
        this.setMediaNetworksComuncHl(mediaNetworksComuncHl);
        return this;
    }

    public void setMediaNetworksComuncHl(Boolean mediaNetworksComuncHl) {
        this.mediaNetworksComuncHl = mediaNetworksComuncHl;
    }

    public Boolean getCommunityOrgNetworksHl() {
        return this.communityOrgNetworksHl;
    }

    public Networks communityOrgNetworksHl(Boolean communityOrgNetworksHl) {
        this.setCommunityOrgNetworksHl(communityOrgNetworksHl);
        return this;
    }

    public void setCommunityOrgNetworksHl(Boolean communityOrgNetworksHl) {
        this.communityOrgNetworksHl = communityOrgNetworksHl;
    }

    public Boolean getRegulatoryOrganismsNetworks() {
        return this.regulatoryOrganismsNetworks;
    }

    public Networks regulatoryOrganismsNetworks(Boolean regulatoryOrganismsNetworks) {
        this.setRegulatoryOrganismsNetworks(regulatoryOrganismsNetworks);
        return this;
    }

    public void setRegulatoryOrganismsNetworks(Boolean regulatoryOrganismsNetworks) {
        this.regulatoryOrganismsNetworks = regulatoryOrganismsNetworks;
    }

    public Boolean getNetworksProvidersHl() {
        return this.networksProvidersHl;
    }

    public Networks networksProvidersHl(Boolean networksProvidersHl) {
        this.setNetworksProvidersHl(networksProvidersHl);
        return this;
    }

    public void setNetworksProvidersHl(Boolean networksProvidersHl) {
        this.networksProvidersHl = networksProvidersHl;
    }

    public Boolean getSocialNetworks() {
        return this.socialNetworks;
    }

    public Networks socialNetworks(Boolean socialNetworks) {
        this.setSocialNetworks(socialNetworks);
        return this;
    }

    public void setSocialNetworks(Boolean socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public Boolean getShareholderNetworksHl() {
        return this.shareholderNetworksHl;
    }

    public Networks shareholderNetworksHl(Boolean shareholderNetworksHl) {
        this.setShareholderNetworksHl(shareholderNetworksHl);
        return this;
    }

    public void setShareholderNetworksHl(Boolean shareholderNetworksHl) {
        this.shareholderNetworksHl = shareholderNetworksHl;
    }

    public ContactData getContactData() {
        return this.contactData;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }

    public Networks contactData(ContactData contactData) {
        this.setContactData(contactData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Networks)) {
            return false;
        }
        return id != null && id.equals(((Networks) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Networks{" +
            "id=" + getId() +
            ", networks3rdSectorHl='" + getNetworks3rdSectorHl() + "'" +
            ", academicNetworksHl='" + getAcademicNetworksHl() + "'" +
            ", customerNetworksHl='" + getCustomerNetworksHl() + "'" +
            ", employeeNetworksHl='" + getEmployeeNetworksHl() + "'" +
            ", networksEntFinanHl='" + getNetworksEntFinanHl() + "'" +
            ", stateNetworksHl='" + getStateNetworksHl() + "'" +
            ", internationalNetworksHl='" + getInternationalNetworksHl() + "'" +
            ", mediaNetworksComuncHl='" + getMediaNetworksComuncHl() + "'" +
            ", communityOrgNetworksHl='" + getCommunityOrgNetworksHl() + "'" +
            ", regulatoryOrganismsNetworks='" + getRegulatoryOrganismsNetworks() + "'" +
            ", networksProvidersHl='" + getNetworksProvidersHl() + "'" +
            ", socialNetworks='" + getSocialNetworks() + "'" +
            ", shareholderNetworksHl='" + getShareholderNetworksHl() + "'" +
            "}";
    }
}
