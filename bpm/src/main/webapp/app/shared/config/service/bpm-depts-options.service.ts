import axios from "axios";
import {Store} from "vuex";
import {useStore} from "@u3u/vue-hooks";
export default class BpmDeptsOptionsService {
  constructor(private store: Store<any>) {
    this.getBpmDeptsOptions();
  }

  private getBpmDeptsOptions() {
    //  bpmErrandOptions
    this.store.dispatch('eipDeptsDataPromise', new Promise(resolve => {
      axios
        .get('/eip/bpmDeptsOptions')
        .then(res => {
          resolve(res.data);
          this.store.dispatch('eipDeptsData', res.data);
          useStore().value.commit('setBpmDeptsOptions', res.data.map(item => {
            return {value: item.dept_id, text: item.dept_name};
          }));
        })
        .catch(err => console.log(err));
    }));
  }

}
