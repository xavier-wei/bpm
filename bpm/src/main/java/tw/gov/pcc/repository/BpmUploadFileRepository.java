package tw.gov.pcc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.BpmUploadFile;

/**
 * Spring Data SQL repository for the BpmUploadFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BpmUploadFileRepository extends JpaRepository<BpmUploadFile, Long> {
    List<BpmUploadFile> findBpmUploadFileByFormId(String formId);

}
