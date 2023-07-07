package tw.gov.pcc.eip.adm.view.dynatree;

import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.eip.adm.view.dynatree.entity.Tree;
import tw.gov.pcc.eip.adm.view.dynatree.entity.Tree.Child;

import java.util.ArrayList;
import java.util.List;

public class DynaTreeBuilder {

    public static List<String> build(List<Tree> trees) {
        List<String> list = new ArrayList<String>();

        list.add("<ul style='display: none'>");

        int level = 1;
        for (Tree tree : trees) {
            StringBuilder buff = new StringBuilder();

            buff.append("<li id='")
                    .append(tree.getId())
                    .append("'");

            if (StringUtils.isNotEmpty(tree.getTitle()))
                buff.append(" title='")
                        .append(tree.getTitle())
                        .append("'");
            if (tree.isChecked()) {
                buff.append(" class='selected expanded'");
            }
            if (tree.getChild() != null) {
                Child child = tree.getChild();
                String data = " data=\"";
                if (StringUtils.isNotEmpty(child.getIcon())) {
                    data += " icon:'" + child.getIcon() + "',";
                }
                if (child.isHideCheckbox()) {
                    data += " hideCheckbox:true,";
                }
                if (child.isUnselectable()) {
                    data += " unselectable:true,";
                }
                data = StringUtils.removeEnd(data, ",");
                buff.append(data)
                        .append("\"");
            }

            buff.append(">")
                    .append(tree.getDisplay());

            if (level > tree.getLevel()) {
                for (int i = tree.getLevel(); i < level; i++) {
                    list.add("</ul>");
                }
            }

            list.add(buff.toString());

            if (tree.isFolder()) {
                list.add("<ul>");
            }

            level = tree.getLevel();
        }

        list.add("\n</ul>");
        return list;
    }
}
