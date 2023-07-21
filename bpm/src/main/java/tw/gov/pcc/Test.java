package tw.gov.pcc;

import tw.gov.pcc.service.dto.EipBpmIsmsL414DTO;
import tw.gov.pcc.service.mapper.EipBpmIsmsL414Mapper;
import tw.gov.pcc.service.mapper.EipBpmIsmsL414MapperImpl;

public class Test {
    public static void main(String[] args) {
        EipBpmIsmsL414DTO dto = new EipBpmIsmsL414DTO();
        EipBpmIsmsL414Mapper eipBpmIsmsL414Mapper = new EipBpmIsmsL414MapperImpl();
        eipBpmIsmsL414Mapper.toEntity(dto);

    }
}
