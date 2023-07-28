import Vue from 'vue';
import VueCompositionAPI, {ref} from '@vue/composition-api';
import {Menu} from '@/core/menu/menu';
import axios from 'axios';
import {
  cloneDeep as _cloneDeep,
  keys as _keys,
  includes as _includes,
  filter as _filter,
  findIndex as _findIndex,
  assign as _assign,
  reverse as _reverse,
  uniq as _uniq,
  take as _take,
  takeRight as _takeRight,
  concat as _concat,
  forEach as _forEach,
  isArray as _isArray,
  size as _size,
} from 'lodash';

Vue.use(VueCompositionAPI);

export default class MenuService {
  retrieveAppMenu() {
    const data = ref<Menu[]>(null);
    axios.get(`/eip/menu`).then(response => {
      data.value = _cloneDeep(buildMenuData(response.data, false));
    });
    return { data };
  }
}

function buildMenuData(data, flatten) {
  let arr = [];
  let layerArr = [];
  const count = 0;
  if (data.length === 0) return arr;

  //把後端的所有的dto 轉成前端的obj，並給每個obj加了一個layer屬性，說他是屬於哪一層的
  data.forEach((d, index) => {
    flattenResponseData(arr, d, layerArr, index);
  });

  //反轉layer Arr
  layerArr = _reverse(_uniq(layerArr));

  //依照順序給每個obj ID
  buildArrOrder(arr, count);

  if (flatten) return arr;

  //讓平坦狀的menu obj arr長出樹狀結構
  rebuildNestedMenuData(arr, layerArr);

  //找出是directory的obj,assign給他attr,並給每個obj rootId
  arr = _filter(arr, a => {
    return a.meta.layer === 1;
  });
  arr.forEach(ar => {
    buildAttrForArrData(ar, ar.meta.functionId);
  });
  return arr;
}

//把後端過來的menu tree平坦化
function flattenResponseData(arr, item, layerArr, layer) {
  const clone = _cloneDeep(item),
    keys = _keys(clone);
  const obj = {
    id: '',
    path: clone.functionPath,
    meta: {
      functionId: clone.functionId,
      parentId: clone.masterFunctionId,
      rootId: '',
      label: clone.functionName,
      layer: layer,
      show: true,
    },
    children: [],
  };
  layerArr.push(layer);
  arr.push(obj);
  if (!_includes(keys, 'children')) return;
  if (clone.children.length === 0) return;
  clone.children.forEach(child => {
    flattenResponseData(arr, child, layerArr, layer + 1);
  });
}
//本來的平坦狀menu arr沒有id，在這邊assign給他
function buildArrOrder(arr, count) {
  arr.forEach(a => {
    if (a.meta.layer === 0) return;
    count += 1;
    a.id = count;
  });
}

//設定每個obj的rootId
//如果該obj有children 他就是directory
function buildAttrForArrData(data, rootId) {
  data.meta.rootId = rootId;
  if (data.children.length === 0) return;
  data.meta = _assign(data.meta, { directory: true, icon: 'tasks' });
  data.children.forEach(child => {
    buildAttrForArrData(child, rootId);
  });
}

function rebuildNestedMenuData(arr, layerArr) {
  layerArr.forEach(num => {
    //篩選出對應layer的 menu obj，一層一層去長他的children(從最底層長到最上層)
    const filterArr = _filter(arr, a => {
      return a.meta.layer === num;
    });

    if (filterArr.length === 0) return;
    filterArr.forEach(f => {
      //_findIndex 遍歷arr裡面所有的子元件ar，如果ar裡面的functionId 跟 filter過後的parentId相同，就會回傳arr index
      // 比如說現在filter 是第1層(第0層不會進來)，第一層的obj會有 parentId 跟functionId,
      // _findIndex會找到functionId為bpm0000的(也就是他的parent)，並在parent的children屬性push這個obj
      // {
      //   "id": 2,
      //   "meta": {
      //     "functionId": "bpm0100np",
      //     "parentId": "bpm0000",
      //     "layer": 1,
      //   },
      // },
      const index = _findIndex(arr, ar => {
        return f.meta.parentId === ar.meta.functionId;
      });
      if (index !== -1) arr[index].children.push(f);
    });
  });
}
