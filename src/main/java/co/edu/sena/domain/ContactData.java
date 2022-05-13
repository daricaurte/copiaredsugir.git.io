package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ContactData.
 */
@Entity
@Table(name = "contact_data")
public class ContactData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "document_number_contact", length = 50, nullable = false)
    private String documentNumberContact;

    @NotNull
    @Size(max = 11)
    @Column(name = "cell_phone_number", length = 11, nullable = false, unique = true)
    private String cellPhoneNumber;

    @NotNull
    @Size(max = 50)
    @Column(name = "country", length = 50, nullable = false)
    private String country;

    @NotNull
    @Size(max = 50)
    @Column(name = "city", length = 50, nullable = false)
    private String city;

    @NotNull
    @Size(max = 50)
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(max = 50)
    @Column(name = "department", length = 50, nullable = false)
    private String department;

    @NotNull
    @Size(max = 50)
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Size(max = 50)
    @Column(name = "second_name", length = 50)
    private String secondName;

    @NotNull
    @Size(max = 50)
    @Column(name = "first_last_name", length = 50, nullable = false)
    private String firstLastName;

    @Size(max = 50)
    @Column(name = "second_last_name", length = 50)
    private String secondLastName;

    @NotNull
    @Size(max = 50)
    @Column(name = "neighborhood", length = 50, nullable = false)
    private String neighborhood;

    @OneToMany(mappedBy = "contactData")
    @JsonIgnoreProperties(value = { "contactData" }, allowSetters = true)
    private Set<CompanyType> companyTypes = new HashSet<>();

    @OneToMany(mappedBy = "contactData")
    @JsonIgnoreProperties(value = { "contactData" }, allowSetters = true)
    private Set<Relation> relations = new HashSet<>();

    @OneToMany(mappedBy = "contactData")
    @JsonIgnoreProperties(value = { "contactData" }, allowSetters = true)
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(mappedBy = "contactData")
    @JsonIgnoreProperties(value = { "contactData" }, allowSetters = true)
    private Set<Networks> networks = new HashSet<>();

    @OneToMany(mappedBy = "contactData")
    @JsonIgnoreProperties(value = { "contactData" }, allowSetters = true)
    private Set<Qualification> qualifications = new HashSet<>();

    @OneToMany(mappedBy = "contactData")
    @JsonIgnoreProperties(value = { "contactData" }, allowSetters = true)
    private Set<PriorityOrder> priorityOrders = new HashSet<>();

    @OneToMany(mappedBy = "contactData")
    @JsonIgnoreProperties(value = { "contactData" }, allowSetters = true)
    private Set<Potential> potentials = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "contactData", "documentType" }, allowSetters = true)
    private Affiliate affiliate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContactData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumberContact() {
        return this.documentNumberContact;
    }

    public ContactData documentNumberContact(String documentNumberContact) {
        this.setDocumentNumberContact(documentNumberContact);
        return this;
    }

    public void setDocumentNumberContact(String documentNumberContact) {
        this.documentNumberContact = documentNumberContact;
    }

    public String getCellPhoneNumber() {
        return this.cellPhoneNumber;
    }

    public ContactData cellPhoneNumber(String cellPhoneNumber) {
        this.setCellPhoneNumber(cellPhoneNumber);
        return this;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getCountry() {
        return this.country;
    }

    public ContactData country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return this.city;
    }

    public ContactData city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return this.email;
    }

    public ContactData email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return this.department;
    }

    public ContactData department(String department) {
        this.setDepartment(department);
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public ContactData firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public ContactData secondName(String secondName) {
        this.setSecondName(secondName);
        return this;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstLastName() {
        return this.firstLastName;
    }

    public ContactData firstLastName(String firstLastName) {
        this.setFirstLastName(firstLastName);
        return this;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return this.secondLastName;
    }

    public ContactData secondLastName(String secondLastName) {
        this.setSecondLastName(secondLastName);
        return this;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getNeighborhood() {
        return this.neighborhood;
    }

    public ContactData neighborhood(String neighborhood) {
        this.setNeighborhood(neighborhood);
        return this;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Set<CompanyType> getCompanyTypes() {
        return this.companyTypes;
    }

    public void setCompanyTypes(Set<CompanyType> companyTypes) {
        if (this.companyTypes != null) {
            this.companyTypes.forEach(i -> i.setContactData(null));
        }
        if (companyTypes != null) {
            companyTypes.forEach(i -> i.setContactData(this));
        }
        this.companyTypes = companyTypes;
    }

    public ContactData companyTypes(Set<CompanyType> companyTypes) {
        this.setCompanyTypes(companyTypes);
        return this;
    }

    public ContactData addCompanyType(CompanyType companyType) {
        this.companyTypes.add(companyType);
        companyType.setContactData(this);
        return this;
    }

    public ContactData removeCompanyType(CompanyType companyType) {
        this.companyTypes.remove(companyType);
        companyType.setContactData(null);
        return this;
    }

    public Set<Relation> getRelations() {
        return this.relations;
    }

    public void setRelations(Set<Relation> relations) {
        if (this.relations != null) {
            this.relations.forEach(i -> i.setContactData(null));
        }
        if (relations != null) {
            relations.forEach(i -> i.setContactData(this));
        }
        this.relations = relations;
    }

    public ContactData relations(Set<Relation> relations) {
        this.setRelations(relations);
        return this;
    }

    public ContactData addRelation(Relation relation) {
        this.relations.add(relation);
        relation.setContactData(this);
        return this;
    }

    public ContactData removeRelation(Relation relation) {
        this.relations.remove(relation);
        relation.setContactData(null);
        return this;
    }

    public Set<Contact> getContacts() {
        return this.contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        if (this.contacts != null) {
            this.contacts.forEach(i -> i.setContactData(null));
        }
        if (contacts != null) {
            contacts.forEach(i -> i.setContactData(this));
        }
        this.contacts = contacts;
    }

    public ContactData contacts(Set<Contact> contacts) {
        this.setContacts(contacts);
        return this;
    }

    public ContactData addContact(Contact contact) {
        this.contacts.add(contact);
        contact.setContactData(this);
        return this;
    }

    public ContactData removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.setContactData(null);
        return this;
    }

    public Set<Networks> getNetworks() {
        return this.networks;
    }

    public void setNetworks(Set<Networks> networks) {
        if (this.networks != null) {
            this.networks.forEach(i -> i.setContactData(null));
        }
        if (networks != null) {
            networks.forEach(i -> i.setContactData(this));
        }
        this.networks = networks;
    }

    public ContactData networks(Set<Networks> networks) {
        this.setNetworks(networks);
        return this;
    }

    public ContactData addNetworks(Networks networks) {
        this.networks.add(networks);
        networks.setContactData(this);
        return this;
    }

    public ContactData removeNetworks(Networks networks) {
        this.networks.remove(networks);
        networks.setContactData(null);
        return this;
    }

    public Set<Qualification> getQualifications() {
        return this.qualifications;
    }

    public void setQualifications(Set<Qualification> qualifications) {
        if (this.qualifications != null) {
            this.qualifications.forEach(i -> i.setContactData(null));
        }
        if (qualifications != null) {
            qualifications.forEach(i -> i.setContactData(this));
        }
        this.qualifications = qualifications;
    }

    public ContactData qualifications(Set<Qualification> qualifications) {
        this.setQualifications(qualifications);
        return this;
    }

    public ContactData addQualification(Qualification qualification) {
        this.qualifications.add(qualification);
        qualification.setContactData(this);
        return this;
    }

    public ContactData removeQualification(Qualification qualification) {
        this.qualifications.remove(qualification);
        qualification.setContactData(null);
        return this;
    }

    public Set<PriorityOrder> getPriorityOrders() {
        return this.priorityOrders;
    }

    public void setPriorityOrders(Set<PriorityOrder> priorityOrders) {
        if (this.priorityOrders != null) {
            this.priorityOrders.forEach(i -> i.setContactData(null));
        }
        if (priorityOrders != null) {
            priorityOrders.forEach(i -> i.setContactData(this));
        }
        this.priorityOrders = priorityOrders;
    }

    public ContactData priorityOrders(Set<PriorityOrder> priorityOrders) {
        this.setPriorityOrders(priorityOrders);
        return this;
    }

    public ContactData addPriorityOrder(PriorityOrder priorityOrder) {
        this.priorityOrders.add(priorityOrder);
        priorityOrder.setContactData(this);
        return this;
    }

    public ContactData removePriorityOrder(PriorityOrder priorityOrder) {
        this.priorityOrders.remove(priorityOrder);
        priorityOrder.setContactData(null);
        return this;
    }

    public Set<Potential> getPotentials() {
        return this.potentials;
    }

    public void setPotentials(Set<Potential> potentials) {
        if (this.potentials != null) {
            this.potentials.forEach(i -> i.setContactData(null));
        }
        if (potentials != null) {
            potentials.forEach(i -> i.setContactData(this));
        }
        this.potentials = potentials;
    }

    public ContactData potentials(Set<Potential> potentials) {
        this.setPotentials(potentials);
        return this;
    }

    public ContactData addPotential(Potential potential) {
        this.potentials.add(potential);
        potential.setContactData(this);
        return this;
    }

    public ContactData removePotential(Potential potential) {
        this.potentials.remove(potential);
        potential.setContactData(null);
        return this;
    }

    public Affiliate getAffiliate() {
        return this.affiliate;
    }

    public void setAffiliate(Affiliate affiliate) {
        this.affiliate = affiliate;
    }

    public ContactData affiliate(Affiliate affiliate) {
        this.setAffiliate(affiliate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactData)) {
            return false;
        }
        return id != null && id.equals(((ContactData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactData{" +
            "id=" + getId() +
            ", documentNumberContact='" + getDocumentNumberContact() + "'" +
            ", cellPhoneNumber='" + getCellPhoneNumber() + "'" +
            ", country='" + getCountry() + "'" +
            ", city='" + getCity() + "'" +
            ", email='" + getEmail() + "'" +
            ", department='" + getDepartment() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", secondName='" + getSecondName() + "'" +
            ", firstLastName='" + getFirstLastName() + "'" +
            ", secondLastName='" + getSecondLastName() + "'" +
            ", neighborhood='" + getNeighborhood() + "'" +
            "}";
    }
}
