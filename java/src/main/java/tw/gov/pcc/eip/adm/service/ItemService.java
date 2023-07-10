package tw.gov.pcc.eip.adm.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.ItemsDao;
import tw.gov.pcc.eip.dao.Role_aclDao;
import tw.gov.pcc.eip.domain.CursorAcl;
import tw.gov.pcc.eip.domain.Items;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemsDao itemsDao;

    private final Role_aclDao roleAclDao;

    public ItemService(ItemsDao itemsDao, Role_aclDao role_aclDao) {
        this.itemsDao = itemsDao;
        this.roleAclDao = role_aclDao;
    }

    public String getNewItemIdByLevel(List<CursorAcl> cursor, BigDecimal level,
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


    private String getMaxIdByLevelAndType(List<CursorAcl> cursor,
                                          BigDecimal level, String pid) {
        String maxId = "";
        for (CursorAcl c : cursor) {
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
    }

    public void deleteItemsAndRoleAcls(List<Items> items) {
        items.forEach(item -> {
            itemsDao.deleteByKey(item);
            roleAclDao.findByItem(item.getItem_id())
                    .forEach(roleAclDao::deleteByKey);
        });
    }
}
