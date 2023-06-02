package tw.gov.pcc.eip.adm.view.dynatree.parser;

import tw.gov.pcc.eip.adm.view.dynatree.entity.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DepartmentParser {
	
	public static List<Tree> parser(List<Map> list) {
		List<Tree> trees = new ArrayList<Tree>();
//		for(CursorDepartment cd : list) {
//			Tree tree = new Tree();
//			tree.setId(cd.getId());
//			tree.setName(cd.getName());
//			tree.setDisplay(tree.getName() + "(" + tree.getId() + ")");
//			tree.setLevel(cd.getLevel());
//			
//			if(cd.getLeaf() == 0)
//				tree.setFolder(true);
//			else
//				tree.setFolder(false);
//			
//			tree.setParentId(cd.getParentId());
//			
//			trees.add(tree);
//		}
		return trees;
	}
}
