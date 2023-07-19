package tw.gov.pcc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EipBpmIsmsL414MapperTest {

    private EipBpmIsmsL414Mapper eipBpmIsmsL414Mapper;

    @BeforeEach
    public void setUp() {
        eipBpmIsmsL414Mapper = new EipBpmIsmsL414MapperImpl();
    }
}
