package tw.gov.pcc.service;

import org.springframework.stereotype.Service;
import tw.gov.pcc.domain.Cpape05m;
import tw.gov.pcc.domain.Cpape05mForTest;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.domain.UserRole;
import tw.gov.pcc.repository.Cpape05mForTestRepository;
import tw.gov.pcc.repository.Cpape05mRepository;
import tw.gov.pcc.repository.UserRepository;
import tw.gov.pcc.repository.UserRoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Cpape05mRepository cpape05mRepository;

    private final Cpape05mForTestRepository cpape05mForTestRepository;
    private final UserRoleRepository userRoleRepository;


    public UserService(UserRepository userRepository, Cpape05mRepository cpape05mRepository, Cpape05mForTestRepository cpape05mForTestRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.cpape05mRepository = cpape05mRepository;
        this.cpape05mForTestRepository = cpape05mForTestRepository;
        this.userRoleRepository = userRoleRepository;
    }


    public User getUserInfo(String userId) {
        User user = userRepository.findByUserId(userId);
        Optional<Cpape05m> cpape05m = cpape05mRepository.findByPecard(userId);

        Optional<Cpape05mForTest> cpape05mForTest;
        String title;
        if (cpape05m.isEmpty()) {
            cpape05mForTest = cpape05mForTestRepository.findByPecard(userId);
            title = cpape05mForTest.get() == null ? "無職稱" : cpape05mForTest.get().getTitle();
            user.setTitleName(title);
        } else {
            title = (cpape05m.get().getTitle() == null || cpape05m.get().getTitle().isEmpty()) ? "無職稱" : cpape05m.get().getTitle();
            user.setTitleName(title);
        }

        List<UserRole> userRoles = userRoleRepository.findAllByUserId(userId);
        if (userRoles != null && !userRoles.isEmpty()) {
            List<String> roles = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            user.setUserRole(String.join(",", roles));
        }
        return user;
    }

}
