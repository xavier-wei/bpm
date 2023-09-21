package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.Eip06w040Report;
import tw.gov.pcc.eip.framework.validation.RequiredString;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * 會議室活動報表列印作業
 * @author 2201009
 */
@Data
@NoArgsConstructor
public class Eip06w040Case implements Serializable {

    @RequiredString(label = "會議日期")
    private String meetingdt; //會議日期
    private ByteArrayOutputStream baos; // 報表內容
    private List<Eip06w040Report> resultList;
}
