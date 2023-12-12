package tw.gov.pcc.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "EIPMAILDATA")
public class EipMailData {
    @Id
    @Size(max = 20)
    @Nationalized
    @Column(name = "mail_id", nullable = false, length = 20)
    private String mailId;

    @Size(max = 20)
    @Nationalized
    @Column(name = "batch_no", length = 20)
    private String batchNo;

    @Size(max = 10)
    @Nationalized
    @Column(name = "mail_kind", length = 10)
    private String mailKind;

    @Size(max = 512)
    @Nationalized
    @Column(name = "email", length = 512)
    private String email;

    @Size(max = 4000)
    @Nationalized
    @Column(name = "subject", length = 4000)
    private String subject;

    @Nationalized
    @Lob
    @Column(name = "message")
    private String message;

    @Size(max = 1024)
    @Nationalized
    @Column(name = "file_path", length = 1024)
    private String filePath;

    @Size(max = 1)
    @Nationalized
    @Column(name = "is_mailed", length = 1)
    private String isMailed;

    @Column(name = "process_timestamp")
    private Instant processTimestamp;

    @Size(max = 1024)
    @Nationalized
    @Column(name = "return_message", length = 1024)
    private String returnMessage;

    @Size(max = 100)
    @Nationalized
    @Column(name = "attach_file_name", length = 100)
    private String attachFileName;

}

