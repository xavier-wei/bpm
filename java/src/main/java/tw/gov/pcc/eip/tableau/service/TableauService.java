package tw.gov.pcc.eip.tableau.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.*;
import tw.gov.pcc.eip.domain.*;
import tw.gov.pcc.eip.tableau.cases.TableauDataCase;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * index頁面，tableau儀錶板顯示
 *
 * @author Susan
 */

@Service
@Slf4j
public class TableauService {

    @Autowired
    private TableauUserInfoDao tableauUserInfoDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private DeptsDao deptsDao;

    @Autowired
    private TableauDepartmentInfoDao tableauDepartmentInfoDao;

    @Autowired
    private TableauDashboardInfoDao tableauDashboardInfoDao;


    public List<TableauDataCase> findTableauDataByUser(String userId) throws IOException {
        List<TableauUserInfo> subscribedData = tableauUserInfoDao.selectByUserId(userId);
        List<TableauDataCase> resultList = new ArrayList<>();
        List<TableauDashboardInfo> dashboardList;

        //有訂閱
        if (CollectionUtils.isNotEmpty(subscribedData)) {
            log.info("該使用者<有>訂閱儀錶板，使用者 userId :{}", userId);
            List<String> figIdList = subscribedData.stream()
                    .map(TableauUserInfo::getDashboard_fig_id)
                    .collect(Collectors.toList());
            dashboardList = tableauDashboardInfoDao.selectByDashboardFigId(figIdList);
            //無訂閱
        } else {
            Users users = usersDao.selectByKey(userId); // ex. n5000
            String deptId = users.getDept_id();
            log.info("該使用者<無>訂閱儀錶板，使用者 userId :{}，所屬機關為:{}", userId, deptId); //ex. n5000，iisi
            Depts depts;
            do {
                depts = deptsDao.findByPk(deptId);
                deptId = depts.getDept_id_p();
            } while (!deptId.equals("9999")); //要找到"處級"機關
            deptId = depts.getDept_id();
            log.info("該使用者無訂閱儀錶板，使用者所屬 <處級> 機關為:{}", deptId);

            //找該處級機關應顯示的dashboards
            List<TableauDepartmentInfo> departmentInfos = tableauDepartmentInfoDao.selectByDepartmentId(depts.getDept_id());
            List<String> figIdList = departmentInfos.stream()
                    .map(TableauDepartmentInfo::getDashboard_fig_id)
                    .collect(Collectors.toList());
            dashboardList = tableauDashboardInfoDao.selectByDashboardFigId(figIdList);
        }
        log.info("使用者應顯示的儀表板為:{}", dashboardList.toString());
        String tableauFolderPathPrefix = "";
        String env = System.getenv("spring.profiles.active");
        if ("dev".equals(env)) {
            log.info("環境為==dev==");
            tableauFolderPathPrefix = "D:/";
        } else {
            log.info("環境為==prod==");
            tableauFolderPathPrefix = "/mnt/stsdat/eip/";
        }
        log.info("tableauFolderPathPrefix:{}", tableauFolderPathPrefix);
        for (TableauDashboardInfo dashboard : dashboardList) {
            TableauDataCase tableauDataCase = new TableauDataCase();
            tableauDataCase.setDashboardFigId(dashboard.getDashboard_fig_id());
            tableauDataCase.setImageUrl(dashboard.getDashboard_fig_folder() + "/" + dashboard.getDashboard_fig_file_nm());
            String path = dashboard.getDashboard_fig_folder().replaceAll("/", "\\" + File.separator) + File.separator + dashboard.getDashboard_fig_file_nm();
//            if ("dev".equals(env)) {
//                tableauDataCase.setImageBase64String(getImageBase64String(tableauFolderPathPrefix.replaceAll("/", "\\" + File.separator) + path.replace("\\mnt\\stsdat\\eip\\","")));
//            }else{
//                tableauDataCase.setImageBase64String(getImageBase64String(path));
//            }
            tableauDataCase.setImageBase64String(getImageBase64String(tableauFolderPathPrefix.replaceAll("/", "\\" + File.separator) + path));
            tableauDataCase.setTableauUrl(dashboard.getDashboard_url());
            //找排序
            List<TableauUserInfo> userInfoList = tableauUserInfoDao.selectByFigId(dashboard.getDashboard_fig_id());
            TableauUserInfo firstUserInfo = userInfoList.stream()
                    .findFirst()
                    .orElse(null);
            if (firstUserInfo != null) {
                tableauDataCase.setSort_order(firstUserInfo.getSort_order());
            }
            resultList.add(tableauDataCase);
        }
        return resultList;
    }


    public List<TableauDataCase> findTableauData() throws IOException {
        List<TableauDataCase> resultList = new ArrayList<>();
        List<TableauDashboardInfo> dashboardList = tableauDashboardInfoDao.selectAll();
        log.info("使用者應顯示的儀表板為:{}", dashboardList.toString());
        for (TableauDashboardInfo dashboard : dashboardList) {
            TableauDataCase tableauDataCase = new TableauDataCase();
            tableauDataCase.setDashboardFigId(dashboard.getDashboard_fig_id());
            tableauDataCase.setTableauUrl(dashboard.getDashboard_url());
            resultList.add(tableauDataCase);
        }
        return resultList;
    }


    public Map<String, String> getTrustedTicket() {
        Map<String, String> resultMap = new HashMap<>();
        OutputStreamWriter out = null;
        BufferedReader in = null;
        try {
            // TODO: 2023/8/2  wgserver要改成定義在properties檔?
            String wgserver = "http://223.200.84.115";
            StringBuilder reqUrl = new StringBuilder();
            reqUrl.append(URLEncoder.encode("username", "UTF-8"));
            reqUrl.append("=");
            // TODO: 2023/8/2  username: admin要改成定義在properties檔?
            reqUrl.append(URLEncoder.encode("admin", "UTF-8"));
            URL url = new URL(wgserver + "/trusted");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(reqUrl.toString());
            out.flush();
            StringBuilder rsp = new StringBuilder();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                rsp.append(line);
            }
            resultMap.put("ticket", rsp.toString());
//            resultMap.put("targeturl", tableauComponent.getWebTarget());
            return resultMap;
        } catch (Exception e) {
            log.error(e.getMessage());
            resultMap.put("ticket", "Exception occurred");
//            resultMap.put("targeturl", tableauComponent.getWebTarget());
            return resultMap;
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

    }


    /**
     * 將 圖片路徑 轉 base64String
     */
    public String getImageBase64String(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        return Base64.encodeBase64String(Files.readAllBytes(path));
    }


}
