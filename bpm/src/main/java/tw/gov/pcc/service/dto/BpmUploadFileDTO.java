package tw.gov.pcc.service.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tw.gov.pcc.domain.BpmUploadFile} entity.
 */
public class BpmUploadFileDTO implements Serializable {

    private UUID uuid;

    @Size(max = 50)
    private String formId;

    @Size(max = 200)
    private String fileName;

    @Size(max = 20)
    private String fileSize;

    @Size(max = 20)
    private String authorName;

    @Size(max = 1000)
    private String fileDescription;

    @Size(max = 1000)
    private String filePath;

    @Size(max = 20)
    private String updateUser;

    private Instant updateTime;

    @Size(max = 20)
    private String createUser;

    private Instant createTime;
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BpmUploadFileDTO)) {
            return false;
        }

        BpmUploadFileDTO bpmUploadFileDTO = (BpmUploadFileDTO) o;
        if (this.uuid == null) {
            return false;
        }
        return Objects.equals(this.uuid, bpmUploadFileDTO.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uuid);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BpmUploadFileDTO{" +
            "uuid='" + getUuid() + "'" +
            ", formId='" + getFormId() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fileSize='" + getFileSize() + "'" +
            ", authorName='" + getAuthorName() + "'" +
            ", fileDescription='" + getFileDescription() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
