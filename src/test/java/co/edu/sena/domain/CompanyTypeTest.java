package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompanyTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyType.class);
        CompanyType companyType1 = new CompanyType();
        companyType1.setId(1L);
        CompanyType companyType2 = new CompanyType();
        companyType2.setId(companyType1.getId());
        assertThat(companyType1).isEqualTo(companyType2);
        companyType2.setId(2L);
        assertThat(companyType1).isNotEqualTo(companyType2);
        companyType1.setId(null);
        assertThat(companyType1).isNotEqualTo(companyType2);
    }
}
