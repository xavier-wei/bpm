package tw.gov.pcc.service;

import org.springframework.stereotype.Service;
import tw.gov.pcc.cache.BpmCache;
import tw.gov.pcc.domain.Cpape05m;
import tw.gov.pcc.domain.Cpape05mOthers;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.domain.UserRole;
import tw.gov.pcc.repository.Cpape05mOthersRepository;
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

    private final Cpape05mOthersRepository cpape05MOthersRepository;
    private final UserRoleRepository userRoleRepository;


    public UserService(UserRepository userRepository, Cpape05mRepository cpape05mRepository, Cpape05mOthersRepository cpape05MOthersRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.cpape05mRepository = cpape05mRepository;
        this.cpape05MOthersRepository = cpape05MOthersRepository;
        this.userRoleRepository = userRoleRepository;
    }


    public User getUserInfo(String userId) {
        User user = userRepository.findByUserId(userId);
        // 正式員工登入用
        Optional<Cpape05m> cpape05m = cpape05mRepository.findByPecard(userId);
        // IVV或資拓測試帳號用
        Optional<Cpape05mOthers> cpape05mOthers;
        String title;
        if (cpape05m.isEmpty()) {
            cpape05mOthers = cpape05MOthersRepository.findByPecard(userId);
            title = cpape05mOthers.isPresent() ?  cpape05mOthers.get().getTitle(): "無職稱";
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
        user.setSupervisor(BpmCache.getSupervisorJudgeSet().contains(user.getTitleName()));

        return user;
    }

}
