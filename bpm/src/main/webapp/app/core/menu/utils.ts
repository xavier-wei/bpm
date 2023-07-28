import { Menu } from '@/core/menu/menu';
import ExpandedMenu from '@/core/menu/expanded-menu';

export function flattenAndTransformMenus(menus: Menu[]): ExpandedMenu[] {
  const result: ExpandedMenu[] = [];
  expand(menus, 0, result);
  return result;
}

function expand(menus: Menu[], parent: number, result: ExpandedMenu[]): void {
  menus.forEach(menu => {
    const expandedMenu: ExpandedMenu = {
      id: menu.id,
      parentId: parent,
      path: menu.path,
      label: menu.meta.label,
      functionId: menu.meta.functionId,
      icon: menu.meta.icon,
    };
    result.push(expandedMenu);

    if (menu.children) {
      expand(menu.children, menu.id, result);
    }
  });
}
