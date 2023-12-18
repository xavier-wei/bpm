import axios from "axios";
import {Store} from "vuex";

export default class BpmTitleOptionsService {
  constructor(private store: Store<any>) {
    this.getBpmTitle();
  }

  private getBpmTitle() {

    this.store.dispatch('handleBpmTitleOptionsPromise', new Promise(resolve => {
      axios
        .get('/eip/findTitleIdList')
        .then(res => {
          resolve(res.data);

          // 使用 reduce 去重複的 codename
          res.data = Object.values(res.data.reduce((acc, curr) => {
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

          this.store.dispatch('handleBpmTitleOptions', res.data.map(item => {
            return { value: item.codename, text: item.codename };
          }));

        })
        .catch(err => console.log(err));
    }));

  }

}
