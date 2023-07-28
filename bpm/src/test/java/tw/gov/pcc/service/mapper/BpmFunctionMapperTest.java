package tw.gov.pcc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BpmFunctionMapperTest {

    private BpmFunctionMapper bpmFunctionMapper;

    @BeforeEach
    public void setUp() {
        bpmFunctionMapper = new BpmFunctionMapperImpl();
    }
}
