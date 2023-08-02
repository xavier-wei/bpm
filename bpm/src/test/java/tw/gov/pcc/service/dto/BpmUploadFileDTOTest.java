package tw.gov.pcc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import tw.gov.pcc.web.rest.TestUtil;

class BpmUploadFileDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BpmUploadFileDTO.class);
        BpmUploadFileDTO bpmUploadFileDTO1 = new BpmUploadFileDTO();
        bpmUploadFileDTO1.setUuid(UUID.randomUUID());
        BpmUploadFileDTO bpmUploadFileDTO2 = new BpmUploadFileDTO();
        assertThat(bpmUploadFileDTO1).isNotEqualTo(bpmUploadFileDTO2);
        bpmUploadFileDTO2.setUuid(bpmUploadFileDTO1.getUuid());
        assertThat(bpmUploadFileDTO1).isEqualTo(bpmUploadFileDTO2);
        bpmUploadFileDTO2.setUuid(UUID.randomUUID());
        assertThat(bpmUploadFileDTO1).isNotEqualTo(bpmUploadFileDTO2);
        bpmUploadFileDTO1.setUuid(null);
        assertThat(bpmUploadFileDTO1).isNotEqualTo(bpmUploadFileDTO2);
    }
}
