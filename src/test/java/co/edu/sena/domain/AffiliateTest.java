package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AffiliateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Affiliate.class);
        Affiliate affiliate1 = new Affiliate();
        affiliate1.setId(1L);
        Affiliate affiliate2 = new Affiliate();
        affiliate2.setId(affiliate1.getId());
        assertThat(affiliate1).isEqualTo(affiliate2);
        affiliate2.setId(2L);
        assertThat(affiliate1).isNotEqualTo(affiliate2);
        affiliate1.setId(null);
        assertThat(affiliate1).isNotEqualTo(affiliate2);
    }
}
