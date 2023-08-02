package tw.gov.pcc.eip.tableau.cases;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Susan
 */
@Data
@NoArgsConstructor
public class TableauDataCase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 使用者id
     */
    private String userId;


    /**
     * tableau dashboard ID
     */
    private String dashboardFigId;


    /**
     * 圖片存放位置
     */
    private String imageUrl;  // "././images/tableau/BID_01_01_20230717.png",


    /**
     * 圖片 base64 String
     */
    private String imageBase64String;


    /**
     * tableau dashboard url
     */
    private String tableauUrl;  // "http://223.200.84.115/#/views/__2023071701/_?:iid=4"

    public interface Query {
    }
}
