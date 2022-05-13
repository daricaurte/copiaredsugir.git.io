package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PotentialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Potential.class);
        Potential potential1 = new Potential();
        potential1.setId(1L);
        Potential potential2 = new Potential();
        potential2.setId(potential1.getId());
        assertThat(potential1).isEqualTo(potential2);
        potential2.setId(2L);
        assertThat(potential1).isNotEqualTo(potential2);
        potential1.setId(null);
        assertThat(potential1).isNotEqualTo(potential2);
    }
}
