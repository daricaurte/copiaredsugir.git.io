package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.Rol;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Affiliate.
 */
@Entity
@Table(name = "affiliate")
public class Affiliate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "document_number", length = 50, nullable = false)
    private String documentNumber;

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

    @NotNull
    @Size(max = 11)
    @Column(name = "cell_phone_number", length = 11, nullable = false, unique = true)
    private String cellPhoneNumber;

    @NotNull
    @Size(max = 50)
    @Column(name = "city", length = 50, nullable = false)
    private String city;

    @NotNull
    @Size(max = 50)
    @Column(name = "department", length = 50, nullable = false)
    private String department;

    @NotNull
    @Size(max = 50)
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(max = 4)
    @Column(name = "callsign", length = 4, nullable = false, unique = true)
    private String callsign;

    @NotNull
    @Size(max = 50)
    @Column(name = "country", length = 50, nullable = false)
    private String country;

    @NotNull
    @Size(max = 11)
    @Column(name = "phone_number", length = 11, nullable = false)
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "affiliate")
    @JsonIgnoreProperties(
        value = { "companyTypes", "relations", "contacts", "networks", "qualifications", "priorityOrders", "potentials", "affiliate" },
        allowSetters = true
    )
    private Set<ContactData> contactData = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "affiliates" }, allowSetters = true)
    private DocumentType documentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Affiliate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return this.documentNumber;
    }

    public Affiliate documentNumber(String documentNumber) {
        this.setDocumentNumber(documentNumber);
        return this;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Affiliate firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public Affiliate secondName(String secondName) {
        this.setSecondName(secondName);
        return this;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstLastName() {
        return this.firstLastName;
    }

    public Affiliate firstLastName(String firstLastName) {
        this.setFirstLastName(firstLastName);
        return this;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return this.secondLastName;
    }

    public Affiliate secondLastName(String secondLastName) {
        this.setSecondLastName(secondLastName);
        return this;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getNeighborhood() {
        return this.neighborhood;
    }

    public Affiliate neighborhood(String neighborhood) {
        this.setNeighborhood(neighborhood);
        return this;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCellPhoneNumber() {
        return this.cellPhoneNumber;
    }

    public Affiliate cellPhoneNumber(String cellPhoneNumber) {
        this.setCellPhoneNumber(cellPhoneNumber);
        return this;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getCity() {
        return this.city;
    }

    public Affiliate city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDepartment() {
        return this.department;
    }

    public Affiliate department(String department) {
        this.setDepartment(department);
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return this.email;
    }

    public Affiliate email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCallsign() {
        return this.callsign;
    }

    public Affiliate callsign(String callsign) {
        this.setCallsign(callsign);
        return this;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getCountry() {
        return this.country;
    }

    public Affiliate country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Affiliate phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Rol getRol() {
        return this.rol;
    }

    public Affiliate rol(Rol rol) {
        this.setRol(rol);
        return this;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Affiliate user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<ContactData> getContactData() {
        return this.contactData;
    }

    public void setContactData(Set<ContactData> contactData) {
        if (this.contactData != null) {
            this.contactData.forEach(i -> i.setAffiliate(null));
        }
        if (contactData != null) {
            contactData.forEach(i -> i.setAffiliate(this));
        }
        this.contactData = contactData;
    }

    public Affiliate contactData(Set<ContactData> contactData) {
        this.setContactData(contactData);
        return this;
    }

    public Affiliate addContactData(ContactData contactData) {
        this.contactData.add(contactData);
        contactData.setAffiliate(this);
        return this;
    }

    public Affiliate removeContactData(ContactData contactData) {
        this.contactData.remove(contactData);
        contactData.setAffiliate(null);
        return this;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Affiliate documentType(DocumentType documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Affiliate)) {
            return false;
        }
        return id != null && id.equals(((Affiliate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Affiliate{" +
            "id=" + getId() +
            ", documentNumber='" + getDocumentNumber() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", secondName='" + getSecondName() + "'" +
            ", firstLastName='" + getFirstLastName() + "'" +
            ", secondLastName='" + getSecondLastName() + "'" +
            ", neighborhood='" + getNeighborhood() + "'" +
            ", cellPhoneNumber='" + getCellPhoneNumber() + "'" +
            ", city='" + getCity() + "'" +
            ", department='" + getDepartment() + "'" +
            ", email='" + getEmail() + "'" +
            ", callsign='" + getCallsign() + "'" +
            ", country='" + getCountry() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", rol='" + getRol() + "'" +
            "}";
    }
}
