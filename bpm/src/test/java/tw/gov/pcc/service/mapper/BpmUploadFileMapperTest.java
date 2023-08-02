package tw.gov.pcc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BpmUploadFileMapperTest {

    private BpmUploadFileMapper bpmUploadFileMapper;

    @BeforeEach
    public void setUp() {
        bpmUploadFileMapper = new BpmUploadFileMapperImpl();
    }
}
