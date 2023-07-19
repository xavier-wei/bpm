package tw.gov.pcc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tw.gov.pcc.web.rest.TestUtil;

class EipBpmIsmsL414DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EipBpmIsmsL414DTO.class);
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO1 = new EipBpmIsmsL414DTO();
        eipBpmIsmsL414DTO1.setFormId("id1");
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO2 = new EipBpmIsmsL414DTO();
        assertThat(eipBpmIsmsL414DTO1).isNotEqualTo(eipBpmIsmsL414DTO2);
        eipBpmIsmsL414DTO2.setFormId(eipBpmIsmsL414DTO1.getFormId());
        assertThat(eipBpmIsmsL414DTO1).isEqualTo(eipBpmIsmsL414DTO2);
        eipBpmIsmsL414DTO2.setFormId("id2");
        assertThat(eipBpmIsmsL414DTO1).isNotEqualTo(eipBpmIsmsL414DTO2);
        eipBpmIsmsL414DTO1.setFormId(null);
        assertThat(eipBpmIsmsL414DTO1).isNotEqualTo(eipBpmIsmsL414DTO2);
    }
}
