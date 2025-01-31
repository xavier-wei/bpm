package tw.gov.pcc.eip.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.User_rolesDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.User_roles;
import tw.gov.pcc.eip.domain.Users;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 轉換 ldap 資料
 *
 * @author swho
 */
@Service
@AllArgsConstructor
@Slf4j
public class Eip0aw020Service {

    private final EipcodeDao eipcodeDao;
    private final UsersDao usersDao;
    private final User_rolesDao userRolesDao;

    public void insertUsersFromLdap() {
        List<Users> list = transLdapToUsers();
        insertUsers(list);
    }

    private void insertUsers(List<Users> list) {
        AtomicInteger passCnt = new AtomicInteger();
        AtomicInteger insertCnt = new AtomicInteger();
        AtomicInteger errCnt = new AtomicInteger();
        list.forEach(x -> {
            try {
                x.setAcnt_is_valid("Y");
                x.setCreate_timestamp(LocalDateTime.now());
                x.setCreate_user_id("SYS");
                x.setDept_id(Depts.DEFAULT);
                Optional.ofNullable(usersDao.selectByKey(x.getUser_id())).ifPresentOrElse(r -> {
                    log.debug("使用者{}已存在", ObjectUtility.normalizeObject(r.getUser_id()));
                    passCnt.getAndIncrement();
                }, () -> {
                    usersDao.insert(x);
                    log.debug("寫入使用者{}資料", ObjectUtility.normalizeObject(x.getUser_id()));
                    //20231004 UAT要求預設新增Users role
                    insertUsersRole(x);
                    insertCnt.getAndIncrement();
                });
            } catch (Exception e) {
                log.error("寫入USERS錯誤 {} ", ExceptionUtility.getStackTrace(e));
                errCnt.getAndIncrement();
            }
        });
        log.info("LDAP匯入帳號結束，新增{}筆，已存在{}筆，失敗{}筆。", insertCnt.get(), passCnt.get(), errCnt.get());
    }

    private void insertUsersRole(Users users) {
        User_roles addUserRoles = new User_roles();
        addUserRoles.setUser_id(users.getUser_id());
        addUserRoles.setSys_id(User_rolesDao.SYSTEM_ADMIN_SYS_ID);
        addUserRoles.setDept_id(StringUtils.defaultIfBlank(users.getDept_id(), Depts.DEFAULT));
        addUserRoles.setRole_id(User_rolesDao.DEFAULT_ROLE);
        addUserRoles.setCreate_user_id(users.getCreate_user_id());
        addUserRoles.setCreate_timestamp(users.getCreate_timestamp());
        Optional.ofNullable(userRolesDao.selectByKey(addUserRoles.getUser_id(), addUserRoles.getSys_id(), addUserRoles.getDept_id(), addUserRoles.getRole_id()))
                .ifPresentOrElse(userRoles -> log.debug("使用者{}已存在預設角色{}", addUserRoles.getUser_id(), addUserRoles.getRole_id()),
                        () -> {
                            log.debug("使用者{}新增預設角色{}", addUserRoles.getUser_id(), addUserRoles.getRole_id());
                            userRolesDao.insert(addUserRoles);
                        });
    }

    public List<Users> transLdapToUsers() {
        LdapParams ldapParams = initLdapParams();
        Hashtable<String, String> hashEnv = new Hashtable<>();
        hashEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
        hashEnv.put(Context.SECURITY_PRINCIPAL, ldapParams.getBindAccount());
        hashEnv.put(Context.SECURITY_CREDENTIALS, ldapParams.getBindPassword());
        hashEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        hashEnv.put("com.sun.jndi.ldap.connect.timeout", "3000");
        hashEnv.put(Context.PROVIDER_URL, ldapParams.getUrl());
        hashEnv.put(Context.REFERRAL, "ignore");

        List<Users> usersList = new ArrayList<>();
        DirContext ctx = null;
        try {
            ctx = new InitialDirContext(hashEnv);
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            searchControls.setReturningAttributes(Arrays.stream(LdapAttributes.values()).map(Enum::name).toArray(String[]::new));
            NamingEnumeration<SearchResult> result = ctx.search(ObjectUtility.normalizeObject(ldapParams.getDnBase()), ObjectUtility.normalizeObject(ldapParams.getSearchFilter()), ObjectUtility.normalizeObject(searchControls));

            while (result.hasMore()) {
                SearchResult searchResult = result.next();
                Attributes attributes = searchResult.getAttributes();
                Users users = new Users();
                users.setLdap_id(retriveLdapReturnString(attributes, LdapAttributes.userPrincipalName));
                users.setUser_id(StringUtils.substringBefore(users.getLdap_id(), "@"));
                users.setUser_name(retriveLdapReturnString(attributes, LdapAttributes.name));
//                users.setTel1(retriveLdapReturnString(attributes, LdapAttributes.telephoneNumber));
//                users.setEmail(retriveLdapReturnString(attributes, LdapAttributes.mail));
                log.info("讀取LDAP資料：{}", users);
                usersList.add(users);
            }

        } catch (Exception e) {
            log.error("LDAP失敗! {}", ExceptionUtility.getStackTrace(e));
        } finally {
            Optional.ofNullable(ctx).ifPresent(x -> {
                try {
                    x.close();
                } catch (NamingException e) {
                    log.error("LDAP失敗! {}", ExceptionUtility.getStackTrace(e));
                }
            });
        }
        List<Users> nonEmptyIdUsersList = usersList.stream().filter(x -> StringUtils.isNotBlank(x.getUser_id())).collect(Collectors.toList());
        log.info("讀取LDAP共 {} 筆資料，過濾空白ID後共 {} 筆資料。", usersList.size(), nonEmptyIdUsersList.size());
        return nonEmptyIdUsersList;
    }

    private String retriveLdapReturnString(Attributes attributes, LdapAttributes ldapAttributes) {
        return Optional.ofNullable(attributes.get(ldapAttributes.name())).map(x -> {
            try {
                return (String) x.get();
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }).orElse(StringUtils.EMPTY);
    }

    private LdapParams initLdapParams() {
        LdapParams ldapParams = new LdapParams();
        Map<String, String> envMap = eipcodeDao.findByCodeKind("SYS_LDAP").stream().collect(Collectors.toMap(Eipcode::getCodeno, Eipcode::getCodename));
        new BeanWrapperImpl(ldapParams).setPropertyValues(envMap);
        return ldapParams;
    }

    /**
     * Ldap 對應參數
     */
    enum LdapAttributes {
        name, // 姓名
        userPrincipalName, // 完整AD帳號
        telephoneNumber, // 電話
        mail // mail
    }

    @Data
    private static class LdapParams {
        private String bindAccount;
        private String bindPassword;
        private String dnBase;
        private String searchFilter;
        private String url;
    }
}
