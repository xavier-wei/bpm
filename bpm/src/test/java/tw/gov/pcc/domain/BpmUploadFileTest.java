package tw.gov.pcc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import tw.gov.pcc.web.rest.TestUtil;

class BpmUploadFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BpmUploadFile.class);
        BpmUploadFile bpmUploadFile1 = new BpmUploadFile();
        bpmUploadFile1.setUuid(UUID.randomUUID());
        BpmUploadFile bpmUploadFile2 = new BpmUploadFile();
        bpmUploadFile2.setUuid(bpmUploadFile1.getUuid());
        assertThat(bpmUploadFile1).isEqualTo(bpmUploadFile2);
        bpmUploadFile2.setUuid(UUID.randomUUID());
        assertThat(bpmUploadFile1).isNotEqualTo(bpmUploadFile2);
        bpmUploadFile1.setUuid(null);
        assertThat(bpmUploadFile1).isNotEqualTo(bpmUploadFile2);
    }
}
