<template>
  <div>
    <nav aria-label="breadcrumb">
      <ol class="breadcrumb p-1">
        <li
          class="breadcrumb-item d-inline-block align-middle"
          :class="breadcrumb.class"
          v-for="(breadcrumb, index) in breadcrumbs"
          :key="index"
        >
          <font-awesome-icon v-if="breadcrumb.icon" :icon="breadcrumb.icon"></font-awesome-icon>
          <router-link v-if="breadcrumb.path" :to="breadcrumb.path" class="text-secondary">
            {{ breadcrumb.label }}
            <code v-if="breadcrumb.functionId" class="text-secondary">({{ breadcrumb.functionId }})</code>
          </router-link>
          <span v-else class="text-secondary">{{ breadcrumb.label }}</span>
        </li>
      </ol>
    </nav>
  </div>
</template>

<script lang="ts">
import {inject, reactive, ref, watch} from '@vue/composition-api';
import MenuService from './menu-service';
import { flattenAndTransformMenus } from '@/core/menu/utils';
import {useRouter} from '@u3u/vue-hooks';
import {
  BFormRow,
  BCol,
  BButton,
} from 'bootstrap-vue';
import ExpandedMenu from './expanded-menu';
import {Menu} from "@/core/menu/menu";
import axios from "axios";

export default {
  name: "breadcrumb",
  components: {
    'b-form-row': BFormRow,
    'b-button': BButton,
    'b-col': BCol,
  },
  setup() {

    const breadcrumbs = reactive<ExpandedMenu[]>([]);
    const expandedMenus: ExpandedMenu[] = [];
    const menuService: MenuService = inject<MenuService>('menuService');
    const menu = menuService.retrieveAppMenu();

    watch(menu.data, value => {
      expandedMenus.push(...flattenAndTransformMenus(value));
    });

    const { route } = useRouter();
    watch(route, to => {
      const id: number = to.meta.functionId ? expandedMenus.find(expandedMenu => expandedMenu.functionId === to.meta.functionId)?.id : null;
      breadcrumbs.splice(0, breadcrumbs.length);
      breadcrumbs.push({
        id: 0,
        parentId: null,
        label: '行政支援系統',
        path: '/',
        icon: 'home',
      });
      if (id) {
        addBreadcrumb(id, breadcrumbs);
        breadcrumbs[breadcrumbs.length - 1].class = 'active';
      }
    });

    function addBreadcrumb(id: number, breadcrumbsRef: ExpandedMenu[]) {
      const breadcrumb: ExpandedMenu = expandedMenus.find(expandedMenu => expandedMenu.id === id);
      if (breadcrumb) {
        if (breadcrumb.parentId && breadcrumb.parentId !== 0) {
          addBreadcrumb(breadcrumb.parentId, breadcrumbsRef);
        }
        breadcrumbsRef.push(breadcrumb);
      }
    }

    return {
      breadcrumbs,
    }
  }
}
</script>

<style scoped>
.breadcrumb {
  background-color: #f5f5f5;
}
</style>
