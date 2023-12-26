package tw.gov.pcc.eip.tableau.service;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.StoredProcedureDao;
import tw.gov.pcc.eip.domain.Eipcode;

import java.io.File;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UpdateTableauFilesService {
    private static final String CODE_KIND = "TABLEAU%";
    private static final String IP = "ip";
    private static final int PORT = 22;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String FOLDER = "folder";
    private static final String JOB_NAME = "UPDATE_TABLEAU_FILES";
    private  String remoteDir = null;
    private static final String LOCAL_DIR = "/mnt/stsdat/eip/TABLEAU/OUTPUT/FIG";
    private final EipcodeDao eipcodeDao;
    private final StoredProcedureDao storedProcedureDao;

    public UpdateTableauFilesService(EipcodeDao eipcodeDao, StoredProcedureDao storedProcedureDao) {
        this.eipcodeDao = eipcodeDao;
        this.storedProcedureDao = storedProcedureDao;
    }

    public void updateTableauFiles() {
        writeLog("start","開始執行更新Tableau檔案","");
        // 從資料庫中取出Eipcode的相關訊息，並存到Map中
        Map<String, String> sftpServerInfo = eipcodeDao.findCodeKindLike(CODE_KIND).stream().filter(e->"TABLEAUFIG_SFTP".equals(e.getCodekind())).collect(Collectors.toMap(Eipcode::getCodeno, Eipcode::getCodename));
        // 從Map中取出host、port、username的值
        String host = sftpServerInfo.get(IP);
        remoteDir =sftpServerInfo.get(FOLDER);
        String password = sftpServerInfo.get(PASSWORD);
        String username = sftpServerInfo.get(USERNAME);
        JSch jsch = new JSch(); // 創建一個JSch的實例
        Session session;
        try {
            // 使用JSch與遠端主機建立Session
            session = jsch.getSession(username, host, PORT);
            session.setPassword(password); // 設定連線密碼
            Properties prop = new Properties();
            // SSH服務器會在連接的時候檢查主機的公鑰，如果服務器的公鑰已存在於客戶端，那麼連線將繼續；如果不存在，則進行StrictHostKeyChecking
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);     // 將上面的參數配置到session中
            session.connect();   // 連線到遠端主機
            Channel channel = session.openChannel("sftp");  // 開啟sftp頻道
            channel.connect();   // 連接到sftp server
            log.info("成功連接到sftp server");
            ChannelSftp sftpChannel = getChannelSftp((ChannelSftp) channel);
            sftpChannel.exit();   // 退出sftp頻道
            session.disconnect(); // 中斷session
            log.info("已成功中斷session");
            writeLog("end","更新Tableau檔案完成","");

        } catch (Exception e) {
            log.error("發現異常: {}", e.getMessage());
            writeLog("exception","發現異常","執行失敗");
        }
    }

    /**
     * 取得已連接到特定遠端目錄並已完成特定工作的 SFTP server。
     * 工作包括從遠端目錄清單中，找出所有非資料夾的檔案，並將其下載到本地目錄。
     * @param channel 預期已被開啟並已連接到 SFTP server 的 SFTP 頻道
     * @return 傳入的 SFTP 頻道，已進行遠端目錄改變與檔案下載
     * @throws SftpException 如果任何 SFTP 錯誤發生，例如遠端目錄不存在或檔案下載失敗等。
     */
    @SuppressWarnings("unchecked || rawtypes")
    private ChannelSftp getChannelSftp(ChannelSftp channel) throws SftpException {
        channel.cd(remoteDir);   // 連線至遠端資料夾

        // 列出遠端所有資料夾及文件
        List<ChannelSftp.LsEntry> fileList = new ArrayList<>(channel.ls(remoteDir));

        // 檢查每一檔案，如果不是資料夾，而是文件而且檔名不是"." 或 ".." 則從遠端伺服器下載到本地資料夾
        for (ChannelSftp.LsEntry entry : fileList) {
            if (!entry.getAttrs().isDir() && !entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                channel.get(entry.getFilename(), LOCAL_DIR + File.separator + entry.getFilename());
                log.info("已將文件: {} 下載至本地資料夾: {}", entry.getFilename(), LOCAL_DIR);
            }
        }
        return channel;
    }

    private void writeLog(String step,String stepInfo,String memo) {
        SqlParameter[] sqlParameters = {
                new SqlParameter("@p_job_id", Types.NVARCHAR),
                new SqlParameter("@p_job_name", Types.NVARCHAR),
                new SqlParameter("@p_step", Types.NVARCHAR),
                new SqlParameter("@p_stepinfo", Types.NVARCHAR),
                new SqlParameter("@p_memo", Types.NVARCHAR),
                new SqlOutParameter("@p_jobno", Types.NVARCHAR),
        };

        Map<String, Object> params = new HashMap<>();
        params.put("@p_job_id", JOB_NAME);
        params.put("@p_job_name", "更新Tableau檔案");
        params.put("@p_step", step);
        params.put("@p_stepinfo", stepInfo);
        params.put("@p_memo", memo);

        SqlParameterSource param = new MapSqlParameterSource(params);

        storedProcedureDao.callProcedure("USP_ADD_EIPJOBLOG", param,sqlParameters);
    }
}
