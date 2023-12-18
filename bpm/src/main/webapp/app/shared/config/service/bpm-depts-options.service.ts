import axios from "axios";
import {Store} from "vuex";
import {useStore} from "@u3u/vue-hooks";

export default class BpmDeptsOptionsService {
  constructor(private store: Store<any>) {
    this.getBpmDepts();
  }

  private async getBpmDepts() {
    await axios
      .get('/eip/bpmDeptsOptions')
      .then(res => {
        useStore().value.commit('setBpmDeptsOptions', res.data.map(item => {
          return { value: item.dept_id, text: item.dept_name };
        }));
      })
      .catch(err => console.log(err));
  }
}
