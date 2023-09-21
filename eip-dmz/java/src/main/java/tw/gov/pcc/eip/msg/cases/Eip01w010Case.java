package tw.gov.pcc.eip.msg.cases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.AssertTrue;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.framework.validation.RequiredString;

/**
 * 訊息上稿
 * 
 * @author vita
 *
 */
@Data
@NoArgsConstructor
public class Eip01w010Case implements Serializable {

    private static final long serialVersionUID = 7285498790665331794L;

    public interface Update {
        // validation group marker interface
    }

    public interface Check {
        // validation group marker interface
    }

    private String p1id; // 建立人員：(帶入首次建入人員)
    private String p1page; // *頁面型態 A:文章 B:連結
    private String p1title; // *主旨/連結網址

    private String mode; // I , Q

    private String fseq; // 項次

    @RequiredString(label = "頁面型態", groups = { Update.class })
    private String pagetype; // *頁面型態 A:文章 B:連結

    private String status; // 狀態
    private String statusText; // 狀態
    // 0-處理中(暫存) 1-已上稿 2-已核可 3-核退 4-已上架 5-已下架 X-註銷(畫面自已上架維護成註銷)

    @RequiredString(label = "屬性", groups = { Update.class })
    private String attributype; // *屬性
    // 1:公告事項 2:最新消息 3:常用系統及網站 4:下載專區 5:輿情專區 6:人事室-行政院組織改造 7:各處室資訊網-單位簡介
    // 8:各處室資訊網-業務資訊

    @RequiredString(label = "訊息類別", groups = { Update.class })
    private String msgtype; // *訊息類別 依屬性ajax查找

    @RequiredString(label = "顯示位置", groups = { Update.class })
    private String locatearea; // *顯示位置 1:登入前 2:登入後 3:各處室資訊網

    @RequiredString(label = "分眾", groups = { Update.class })
    private String availabledep; // *分眾

    @RequiredString(label = "是否提供外部查詢", groups = { Update.class })
    private String issearch; // *是否提供外部查詢 1:是 2:否

    @RequiredString(label = "顯示順序", groups = { Update.class })
    private String showorder; // *顯示順序 1~99，數字愈小，優先序愈高

    private String istop; // 是否置頂 1:是 2:否

    private String isfront; // 前台是否顯示 1:是 2:否

    @RequiredString(label = "主旨/連結網址", groups = { Update.class })
    private String subject; // *主旨/連結網址

    private String oplink; // 是否需要另開視窗 Y:是 N:否

    @RequiredString(label = "內文", groups = { Update.class })
    private String mcontent; // *內文

//    內文附圖： 
    private MultipartFile[] images;
    private Map<String, String> imageFileNameMap;

    private String indir; // 存放目錄

//    附加檔案 最多20個檔案 (註9)
    private MultipartFile[] files;
    private Map<String, String> fileNameMap;

    @RequiredString(label = "上架時間", groups = { Update.class })
    private String releasedt; // *上架時間： (只有日期)

    @RequiredString(label = "下架時間", groups = { Update.class })
    private String offtime; // *下架時間： (只有日期)

    @RequiredString(label = "聯絡單位", groups = { Update.class })
    private String contactunit; // *連絡單位： 請選擇

    @RequiredString(label = "聯絡人", groups = { Update.class })
    private String contactperson;// *聯絡人： (註10)

    @RequiredString(label = "連絡電話", groups = { Update.class })
    private String contacttel;// *連絡電話：

    private String memo; // 備註：

    private String offreason; // 下架原因：(註11)

    private String creatid; // 建立人員：(帶入首次建入人員)

    private String creatdt; // 建立時間：(帶入首次建入系統時間) (註12)

    private String updid; // 更新人員：(執行更新人員)

    private String upddt; // 更新時間：(執行更新時間)

    private boolean checkAll = false; // 全選

    /** 新增,修改 畫面選單用 */
    @Data
    public static class Option {

        private String codeno;

        private String codename;
    }

    private List<Option> pagetypes = new ArrayList<>(); // *頁面型態 A:文章 B:連結

    private List<Option> statuses1 = new ArrayList<>(); // 狀態

    private List<Option> statuses2 = new ArrayList<>(); // 狀態

    private List<Option> attrtypes = new ArrayList<>(); // *屬性

    private List<Option> msgtypes = new ArrayList<>(); // *訊息類別 依屬性ajax查找

    private List<Option> locateareas = new ArrayList<>(); // *顯示位置 1:登入前 2:登入後 3:各處室資訊網 checkbox

    private List<Option> availabledeps = new ArrayList<>(); // *分眾 checkbox

    private List<Option> contactunits = new ArrayList<>(); // *聯絡單位

    /** 查詢結果選單用 */
    @Data
    public static class Detail {

        private boolean check = false; // 勾選框

        private String creatid; // 建立人員：(帶入首次建入人員)

        private String fseq; // 項次

        private String pagetype; // *頁面型態 A:文章 B:連結

        private String subject; // *主旨/連結網址

        private String isfront; // 前台是否顯示 1:是 2:否

        private String releasedt; // *上架時間：

        private String offtime; // *下架時間：

        private String status; // 狀態

        private String statusText; // 狀態中文描述
    }

    @Data
    public static class preView {
        private String attributype; // 屬性名稱

        private String subject; // 主旨

        private String mcontent; // 訊息文字

        private String contactunit; // 發布單位

        private Map<String, String> file; // 附加檔案

        private String upddt; // 更新日期

        private String contactperson; // 聯絡人

        private String contacttel; // 連絡電話
    }

    private List<Detail> queryList = new ArrayList<>();

    private boolean keep;
    private String tmpPath;
    private String seq;

    @AssertTrue(message = "請至少勾選一筆", groups = Check.class)
    public boolean isChecked() {
        if (!"".equals(fseq)) {
            return true;
        }
        if (queryList != null && queryList.stream().filter(Eip01w010Case.Detail::isCheck).findAny().isPresent()) {
            return true;
        }
        return false;
    }

    @AssertTrue(message = "頁面不存在，請先存檔", groups = Check.class)
    public boolean isExists() {
        if ("Q".equals(mode)) {
            return true;
        }
        if (!StringUtils.isEmpty(fseq)) {
            return true;
        }
        return false;
    }

}
