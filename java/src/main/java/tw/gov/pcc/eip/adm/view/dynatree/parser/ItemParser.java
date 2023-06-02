package tw.gov.pcc.eip.adm.view.dynatree.parser;

import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.eip.adm.view.dynatree.entity.Tree;
import tw.gov.pcc.eip.domain.CursorDeptAcl;

import java.util.ArrayList;
import java.util.List;

public class ItemParser {

    public static List<Tree> parser(List<CursorDeptAcl> list) {
        List<Tree> trees = new ArrayList<Tree>();
        for (CursorDeptAcl cursor : list) {
            Tree tree = new Tree();
            tree.setId(cursor.getItemId());
            tree.setName(cursor.getItemName());
            tree.setDisplay(cursor.getItemName() + "(" + cursor.getSortOrder() + ")");
            tree.setLevel(Integer.parseInt(String.valueOf(cursor.getLevelv())));

            tree.setFolder(cursor.getIsLeaf()
                    .equals("0"));

            tree.setChecked(StringUtils.isNotEmpty(cursor.getDeptId()));

            tree.setParentId(cursor.getParentItemId());

            trees.add(tree);
        }
        return trees;
    }
}
