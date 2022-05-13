package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactData.class);
        ContactData contactData1 = new ContactData();
        contactData1.setId(1L);
        ContactData contactData2 = new ContactData();
        contactData2.setId(contactData1.getId());
        assertThat(contactData1).isEqualTo(contactData2);
        contactData2.setId(2L);
        assertThat(contactData1).isNotEqualTo(contactData2);
        contactData1.setId(null);
        assertThat(contactData1).isNotEqualTo(contactData2);
    }
}
