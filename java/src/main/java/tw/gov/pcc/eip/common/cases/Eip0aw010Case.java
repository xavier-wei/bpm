package tw.gov.pcc.eip.common.cases;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class Eip0aw010Case {
    private List<ApiResult> apiResultList;
    private String interval;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiParams {
        private String url;
        private String res;
        private String req;
        private String click_url;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiResult {
        private String cnt;
        private String click_url;
    }

}

