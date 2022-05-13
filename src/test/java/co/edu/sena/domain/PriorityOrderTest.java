package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PriorityOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriorityOrder.class);
        PriorityOrder priorityOrder1 = new PriorityOrder();
        priorityOrder1.setId(1L);
        PriorityOrder priorityOrder2 = new PriorityOrder();
        priorityOrder2.setId(priorityOrder1.getId());
        assertThat(priorityOrder1).isEqualTo(priorityOrder2);
        priorityOrder2.setId(2L);
        assertThat(priorityOrder1).isNotEqualTo(priorityOrder2);
        priorityOrder1.setId(null);
        assertThat(priorityOrder1).isNotEqualTo(priorityOrder2);
    }
}
