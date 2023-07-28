package tw.gov.pcc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tw.gov.pcc.web.rest.TestUtil;

class BpmFunctionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BpmFunction.class);
        BpmFunction bpmFunction1 = new BpmFunction();
        bpmFunction1.setId(1L);
        BpmFunction bpmFunction2 = new BpmFunction();
        bpmFunction2.setId(bpmFunction1.getId());
        assertThat(bpmFunction1).isEqualTo(bpmFunction2);
        bpmFunction2.setId(2L);
        assertThat(bpmFunction1).isNotEqualTo(bpmFunction2);
        bpmFunction1.setId(null);
        assertThat(bpmFunction1).isNotEqualTo(bpmFunction2);
    }
}
