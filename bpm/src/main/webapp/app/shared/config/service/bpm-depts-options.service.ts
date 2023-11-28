import axios from "axios";
import {Store} from "vuex";
import {useStore} from "@u3u/vue-hooks";
export default class BpmDeptsOptionsService {
  constructor(private store: Store<any>) {
    this.getBpmDeptsOptions();
  }

  private async getBpmDeptsOptions() {
    try {
      const response = await axios.get('/eip/bpmDeptsOptions');
      const data = response.data;

      // 更新store
      await this.store.dispatch('eipDeptsData', data);
      useStore().value.commit('setBpmDeptsOptions', data.map(item => {
        return { value: item.dept_id, text: item.dept_name };
      }));
    } catch (error) {
      console.error("獲取資料失敗：", error);
    }
  }

}
