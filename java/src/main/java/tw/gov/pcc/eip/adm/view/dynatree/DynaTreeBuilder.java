package tw.gov.pcc.eip.adm.view.dynatree;

import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.eip.adm.view.dynatree.entity.Tree;
import tw.gov.pcc.eip.adm.view.dynatree.entity.Tree.Child;

import java.util.ArrayList;
import java.util.List;

public class DynaTreeBuilder {

    public static final String FOLDER_STYLE = "folder";
    public static final String ACTIVE_STYLE = "active";
    public static final String ACTIVE_FOCUSED_STYLE = "active focused";
    public static final String EXPANDED_STYLE = "expanded";
    public static final String UNAVAILABLE_STYLE = "unavailable";

    public static List<String> build(List<Tree> trees) {
        List<String> list = new ArrayList<String>();

        list.add("<ul style='display: none'>");

        int level = 1;
        for (Tree tree : trees) {
            StringBuffer buff = new StringBuffer();

            buff.append("<li id='")
                    .append(tree.getId())
                    .append("'");

            if (StringUtils.isNotEmpty(tree.getTitle()))
                buff.append(" title='")
                        .append(tree.getTitle())
                        .append("'");

            buff.append(" class='")
                    .append(tree.getCssClass());
            if (tree.isChecked()) {
                buff.append(" selected");
            }
            buff.append("'");

            if (tree.getChild() != null) {
                Child child = tree.getChild();
                String data = " data=\"";
                if (StringUtils.isNotEmpty(child.getIcon())) {
                    data += " icon:'" + child.getIcon() + "',";
                }
                if (StringUtils.isNotEmpty(child.getCssClass())) {
                    data += " addClass:'" + child.getCssClass() + "',";
                }
                if (child.isHideCheckbox()) {
                    data += " hideCheckbox:true,";
                }
                if (child.isUnselectable()) {
                    data += " unselectable:true,";
                }
                if (data.length() > 1) {
                    data = data.substring(0, data.length() - 1);
                }
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
