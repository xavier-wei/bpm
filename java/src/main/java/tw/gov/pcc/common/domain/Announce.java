package tw.gov.pcc.common.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Announce {
    private String sno;
    private String date;
    private String kind;
    private String context;
    private String url;
    private String dept;
}
