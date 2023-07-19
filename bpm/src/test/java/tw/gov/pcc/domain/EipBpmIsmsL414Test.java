package tw.gov.pcc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tw.gov.pcc.web.rest.TestUtil;

class EipBpmIsmsL414Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EipBpmIsmsL414.class);
        EipBpmIsmsL414 eipBpmIsmsL4141 = new EipBpmIsmsL414();
        eipBpmIsmsL4141.setFormId("id1");
        EipBpmIsmsL414 eipBpmIsmsL4142 = new EipBpmIsmsL414();
        eipBpmIsmsL4142.setFormId(eipBpmIsmsL4141.getFormId());
        assertThat(eipBpmIsmsL4141).isEqualTo(eipBpmIsmsL4142);
        eipBpmIsmsL4142.setFormId("id2");
        assertThat(eipBpmIsmsL4141).isNotEqualTo(eipBpmIsmsL4142);
        eipBpmIsmsL4141.setFormId(null);
        assertThat(eipBpmIsmsL4141).isNotEqualTo(eipBpmIsmsL4142);
    }
}
