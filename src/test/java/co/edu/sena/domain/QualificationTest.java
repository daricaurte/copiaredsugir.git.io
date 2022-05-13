package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QualificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Qualification.class);
        Qualification qualification1 = new Qualification();
        qualification1.setId(1L);
        Qualification qualification2 = new Qualification();
        qualification2.setId(qualification1.getId());
        assertThat(qualification1).isEqualTo(qualification2);
        qualification2.setId(2L);
        assertThat(qualification1).isNotEqualTo(qualification2);
        qualification1.setId(null);
        assertThat(qualification1).isNotEqualTo(qualification2);
    }
}
