package com.reserva.noblesse.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reserva.noblesse.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TenisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tenis.class);
        Tenis tenis1 = new Tenis();
        tenis1.setId(1L);
        Tenis tenis2 = new Tenis();
        tenis2.setId(tenis1.getId());
        assertThat(tenis1).isEqualTo(tenis2);
        tenis2.setId(2L);
        assertThat(tenis1).isNotEqualTo(tenis2);
        tenis1.setId(null);
        assertThat(tenis1).isNotEqualTo(tenis2);
    }
}
