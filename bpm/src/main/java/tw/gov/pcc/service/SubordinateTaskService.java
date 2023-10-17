package tw.gov.pcc.service;

import org.springframework.stereotype.Service;
import tw.gov.pcc.repository.SubordinateRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubordinateTaskService {
    private final SubordinateRepository subordinateRepository;

    public SubordinateTaskService(SubordinateRepository subordinateRepository) {
        this.subordinateRepository = subordinateRepository;
    }

    public List<String> findAllSubordinate(String pecard) {
        return subordinateRepository
            .executeQuery(pecard)
            .stream()
            .map(e -> (String)e.get("PECARD"))
            .collect(Collectors.toList());
    }

}
