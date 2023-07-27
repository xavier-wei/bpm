import Vue from 'vue';
import VueCompositionAPI, {ref} from '@vue/composition-api';
import {Menu, Meta} from '@/core/menu/menu';
import axios from 'axios';

Vue.use(VueCompositionAPI);

export default class MenuService {

  // retrieveAppMenu() {
  //   const data = ref<Menu[]>(null);
  //   axios.get('content/menu.json', { baseURL: '' }).then(response => {
  //     data.value = response.data;
  //   });
  //   return { data };
  // }

  retrieveAppMenu() {
    const data = ref<Menu[]>(null);
    axios.get(`/eip/menu`).then(response => {
      data.value = this.menusFormater(response.data);
    });
    return { data };
  }


  private menusFormater(menu) {
    let menuSortId = 0;

    return menu.map(element => {
      const child = element.children.map(child => {
        const c = child.children.map(cc => {
          const childchild = this.menuFormater(cc, 3);
          childchild.id = menuSortId;
          menuSortId += 1;
          return childchild;
        })
        const b = this.menuFormater(child, 2);
        b.id = menuSortId;
        menuSortId += 1;
        b.children = c;
        return b;
      });
      const a = this.menuFormater(element, 1);
      a.id = menuSortId;
      menuSortId += 1;
      a.children = child;
      return a;
    });
  }

  private menuFormater(sourceMenu, level) {

    let levelIcon: string;

    if (level === 2) {
      levelIcon = 'tasks';
    }

    const meta: Meta = {
      label: sourceMenu.functionName,
      functionId: (level === 3) ? sourceMenu.functionId : '',
      icon: levelIcon,
      directory: sourceMenu.functionCategory === '0' ? true : false,
    };

    console.log('meta-----------------------------',meta)

    let menu: Menu;

    if (level === 3) {
      menu = {
        id: sourceMenu.sortNo,
        path: sourceMenu.functionPath,
        meta: meta,
      };
    } else {
      menu = {
        id: sourceMenu.sortNo,
        path: sourceMenu.functionPath,
        meta: meta,
        children: new Array<Menu>(),
      };
    }

    return menu;
  }

}
