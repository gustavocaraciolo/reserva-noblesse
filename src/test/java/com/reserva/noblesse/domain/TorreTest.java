package com.reserva.noblesse.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reserva.noblesse.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TorreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Torre.class);
        Torre torre1 = new Torre();
        torre1.setId(1L);
        Torre torre2 = new Torre();
        torre2.setId(torre1.getId());
        assertThat(torre1).isEqualTo(torre2);
        torre2.setId(2L);
        assertThat(torre1).isNotEqualTo(torre2);
        torre1.setId(null);
        assertThat(torre1).isNotEqualTo(torre2);
    }
}
