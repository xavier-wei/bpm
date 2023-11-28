import axios from "axios";
import {Store} from "vuex";
import {useStore} from "@u3u/vue-hooks";

export default class BpmTitleOptionsService {
  constructor(private store: Store<any>) {
    this.getBpmTitleOptions();
  }

  private async getBpmTitleOptions() {

    try {
      const response = await axios.get('/eip/findTitleIdList');

      // 使用 reduce 去重複的 codename
      response.data = Object.values(response.data.reduce((acc, curr) => {
        if (!acc[curr.codename]) {
          acc[curr.codename] = {...curr};
        }
        return acc;
      }, {} as Record<string, {
        codekind: string;
        codename: string;
        codeno: string;
        prcdat: string;
        remark: null | string;
        scodekind: null | string;
        scodeno: null | string;
        staff: string
      }>));

      // 更新store
      await this.store.dispatch('eipCodeData', response.data);
      useStore().value.commit('setBpmTitleOptions', response.data.map(item => {
        return { value: item.codename, text: item.codename };
      }));
    } catch (error) {
      console.error("獲取資料失敗：", error);
    }

  }

}
