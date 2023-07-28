package tw.gov.pcc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tw.gov.pcc.web.rest.TestUtil;

class BpmFunctionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BpmFunctionDTO.class);
        BpmFunctionDTO bpmFunctionDTO1 = new BpmFunctionDTO();
        bpmFunctionDTO1.setId(1L);
        BpmFunctionDTO bpmFunctionDTO2 = new BpmFunctionDTO();
        assertThat(bpmFunctionDTO1).isNotEqualTo(bpmFunctionDTO2);
        bpmFunctionDTO2.setId(bpmFunctionDTO1.getId());
        assertThat(bpmFunctionDTO1).isEqualTo(bpmFunctionDTO2);
        bpmFunctionDTO2.setId(2L);
        assertThat(bpmFunctionDTO1).isNotEqualTo(bpmFunctionDTO2);
        bpmFunctionDTO1.setId(null);
        assertThat(bpmFunctionDTO1).isNotEqualTo(bpmFunctionDTO2);
    }
}
