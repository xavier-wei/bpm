package tw.gov.pcc.eip.adm.view.dynatree.parser;

import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.eip.adm.view.dynatree.entity.Tree;
import tw.gov.pcc.eip.domain.CursorAcl;

import java.util.ArrayList;
import java.util.List;

public class ItemParser {

    public static List<Tree> parser(List<CursorAcl> list) {
        List<Tree> trees = new ArrayList<Tree>();
        for (CursorAcl cursor : list) {
            Tree tree = new Tree();
            tree.setId(cursor.getItemId());
            tree.setName(cursor.getItemName());
            tree.setDisplay(cursor.getItemName() + "(" + cursor.getSortOrder() + ")");
            tree.setLevel(Integer.parseInt(String.valueOf(cursor.getLevelv())));
            tree.setFolder(cursor.getIsLeaf()
                    .equals("0"));
            tree.setChecked(StringUtils.equals(cursor.getIsChecked(), "Y"));
            tree.setParentId(cursor.getParentItemId());
            trees.add(tree);
        }
        return trees;
    }
}
