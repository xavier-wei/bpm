package tw.gov.pcc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tw.gov.pcc.domain.Cpape05m;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.domain.UserRole;
import tw.gov.pcc.repository.Cpape05mRepository;
import tw.gov.pcc.repository.UserRepository;
import tw.gov.pcc.repository.UserRoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final Cpape05mRepository cpape05mRepository;
    public final UserRoleRepository userRoleRepository;


    public UserService(UserRepository userRepository, Cpape05mRepository cpape05mRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.cpape05mRepository = cpape05mRepository;
        this.userRoleRepository = userRoleRepository;
    }


    public User getUserInfo(String userId) {
        User user = userRepository.findByUserId(userId);
        Cpape05m cpape05m = cpape05mRepository.findByPecard(userId);
        if(cpape05m!= null) {
            user.setTitleName(cpape05m.getTitle());
        }
        List<UserRole> userRoles = userRoleRepository.findAllByUserId(userId);
        if (userRoles != null && !userRoles.isEmpty()) {
            List<String> roles= userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            user.setUserRole(String.join(",",roles));
        }
        return user;
    }

}
