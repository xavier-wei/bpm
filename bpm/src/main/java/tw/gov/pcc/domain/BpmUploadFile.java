package tw.gov.pcc.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A BpmUploadFile.
 */
@Entity
@Table(name = "bpm_upload_file")
public class BpmUploadFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * type: bigint
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bpmUploadFileSql")
    @SequenceGenerator(name = "bpmUploadFileSql", sequenceName = "BPM_UPLOAD_FILE_SQL", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "form_id", length = 50, nullable = false)
    private String formId;

    @NotNull
    @Size(max = 200)
    @Column(name = "file_name", length = 200, nullable = false)
    private String fileName;

    @NotNull
    @Size(max = 20)
    @Column(name = "file_size", length = 20, nullable = false)
    private String fileSize;

    @NotNull
    @Size(max = 20)
    @Column(name = "author_name", length = 20, nullable = false)
    private String authorName;

    @Size(max = 1000)
    @Column(name = "file_description", length = 1000)
    private String fileDescription;

    @NotNull
    @Size(max = 1000)
    @Column(name = "file_path", length = 1000, nullable = false)
    private String filePath;

    @Size(max = 20)
    @Column(name = "update_user", length = 20)
    private String updateUser;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @NotNull
    @Size(max = 20)
    @Column(name = "create_user", length = 20, nullable = false)
    private String createUser;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private Timestamp createTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public BpmUploadFile id(Long id) {
        this.setId(id);
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormId() {
        return this.formId;
    }

    public BpmUploadFile formId(String formId) {
        this.setFormId(formId);
        return this;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public BpmUploadFile fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return this.fileSize;
    }

    public BpmUploadFile fileSize(String fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public BpmUploadFile authorName(String authorName) {
        this.setAuthorName(authorName);
        return this;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getFileDescription() {
        return this.fileDescription;
    }

    public BpmUploadFile fileDescription(String fileDescription) {
        this.setFileDescription(fileDescription);
        return this;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public BpmUploadFile filePath(String filePath) {
        this.setFilePath(filePath);
        return this;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public BpmUploadFile updateUser(String updateUser) {
        this.setUpdateUser(updateUser);
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public BpmUploadFile updateTime(Timestamp updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public BpmUploadFile createUser(String createUser) {
        this.setCreateUser(createUser);
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public BpmUploadFile createTime(Timestamp createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BpmUploadFile)) {
            return false;
        }
        return id != null && id.equals(((BpmUploadFile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "BpmUploadFile{" +
                "id=" + id +
                ", formId='" + formId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", authorName='" + authorName + '\'' +
                ", fileDescription='" + fileDescription + '\'' +
                ", filePath='" + filePath + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
