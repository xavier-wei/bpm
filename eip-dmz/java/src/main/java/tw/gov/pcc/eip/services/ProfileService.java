package tw.gov.pcc.eip.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.User_profileDao;
import tw.gov.pcc.eip.domain.User_profile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * profile service
 *
 * @author swho
 */
@Service
@Slf4j
public class ProfileService {

    public static final String ENTRY_LIST_ORDER_KEY = "ENTRY_LIST_ORDER";
    private static final ConcurrentHashMap<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    private final User_profileDao usersProfileDao;

    public ProfileService(User_profileDao usersProfileDao) {
        this.usersProfileDao = usersProfileDao;
    }

    @SuppressWarnings("unchecked")
    public void saveProfile(String userid, String key, String value) {
        ReentrantLock lock = lockMap.computeIfAbsent(userid, id -> new ReentrantLock());
        lock.lock();
        try {
            User_profile current = usersProfileDao.selectByKey(userid);
            ObjectMapper mapper = new ObjectMapper();
            Optional.ofNullable(current)
                    .ifPresentOrElse(x -> {
                        try {
                            Map<String, Object> map = mapper.readValue(x.getProfile(), Map.class);
                            map.put(key, value);
                            x.setProfile(mapper.writeValueAsString(map));
                            usersProfileDao.updateByKey(x);
                        } catch (JsonProcessingException ignored) {
                        }
                    }, () -> {
                        Map<String, Object> map = new LinkedHashMap<>();
                        map.put(key, value);
                        try {
                            User_profile newOne = User_profile.builder()
                                    .user_id(userid)
                                    .profile(mapper.writeValueAsString(map))
                                    .build();
                            usersProfileDao.insert(newOne);
                        } catch (JsonProcessingException ignored) {
                        }
                    });
        } finally {
            lock.unlock();
            if (!lock.hasQueuedThreads()) {
                lockMap.remove(userid, lock);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> readProfileMap(String userid) {
        ObjectMapper mapper = new ObjectMapper();
        return Optional.ofNullable(usersProfileDao.selectByKey(userid))
                .map(x -> {
                    try {
                        return mapper.readValue(x.getProfile(), Map.class);
                    } catch (JsonProcessingException e) {
                        return null;
                    }
                })
                .orElse(new LinkedHashMap<String, Object>());
    }

}
