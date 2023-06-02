package tw.gov.pcc.eip.adm.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.ItemsDao;
import tw.gov.pcc.eip.dao.Role_aclDao;
import tw.gov.pcc.eip.domain.CursorDeptAcl;
import tw.gov.pcc.eip.domain.Items;
import tw.gov.pcc.eip.domain.Role_acl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemsDao itemsDao;

    private final Role_aclDao roleAclDao;

    public ItemService(ItemsDao itemsDao, Role_aclDao roleAclDao) {
        this.itemsDao = itemsDao;
        this.roleAclDao = roleAclDao;
    }

    public String getNewItemIdByLevel(List<CursorDeptAcl> cursor, BigDecimal level,
                                      String pid) {
        String newId;
        String maxId = getMaxIdByLevelAndType(cursor, level, pid);

        String word = maxId.substring(maxId.length() - 1);
        if (((char) (word.hashCode())) >= 'Z') {
            word = maxId.substring(maxId.length() - 2, maxId.length() - 1);
            word = (char) (word.hashCode() + 1) + "A";
            newId = maxId.substring(0, maxId.length() - 2) + word;
        } else {
            word = Character.toString((char) (word.hashCode() + 1));
            newId = maxId.substring(0, maxId.length() - 1) + word;
        }
        return newId;
    }


    private String getMaxIdByLevelAndType(List<CursorDeptAcl> cursor,
                                          BigDecimal level, String pid) {
        String maxId = "";
        for (CursorDeptAcl c : cursor) {
            if (Objects.equals(c.getLevelv(), level.toString())
                    && StringUtils.equals(pid,
                    c.getItemId()
                            .substring(0, pid.length()))) {
                String tmpId = c.getItemId();
                if (tmpId.compareTo(maxId) > 0) {
                    maxId = tmpId;
                }
            }
        }
        if (maxId.equals("")) {
            maxId = pid + "AA";
        }
        return maxId;
    }

    public void saveOrUpdate(Items item) {
        Optional.ofNullable(itemsDao.selectByKey(item.getItem_id()))
                .ifPresentOrElse(
                        (i) -> itemsDao.updateByKey(item),
                        () -> itemsDao.insert(item)
                );


        //todo: 這裡開發用先將授權一併授權
        // select role_acl where user_id = 'n5000'
        roleAclDao.findByItem(item.getItem_id())
                .stream()
                .filter(x -> "EIP_ALL".equals(x.getRole_id()))
                .findAny()
                .ifPresentOrElse((i) -> {
                }, () -> {
                    Role_acl roleAcl = new Role_acl();
                    roleAcl.setItem_id(item.getItem_id());
                    roleAcl.setRole_id("EIP_ALL");
                    roleAcl.setSys_id("EI");
                    roleAcl.setDept_id("iisi");
                    roleAcl.setCreate_user_id("TMP");
                    roleAcl.setCreate_timestamp(LocalDateTime.now());
                    roleAclDao.insert(roleAcl);
                });
    }

    public void deleteItemsAndRoleAcls(List<Items> items) {
        items.forEach(item -> {
            itemsDao.deleteByKey(item);
            roleAclDao.findByItem(item.getItem_id())
                    .forEach(roleAclDao::deleteByKey);
        });
    }
}
