import axios from "axios";
import {Store} from "vuex";
import {useStore} from "@u3u/vue-hooks";

export default class BpmUnitOptionsService {
    constructor(private store: Store<any>) {
        this.getBpmUnitOptions();
    }

    private getBpmUnitOptions() {
        this.store.dispatch('eipCodeDataPromise', new Promise(resolve => {
            axios
                .get('/eip/bpmUnitOptions')
                .then(res => {
                    resolve(res.data);
                    this.store.dispatch('eipCodeData', res.data);
                    useStore().value.commit('setBpmUnitOptions', res.data.map(item => {
                        return {value: item.codeno, text: item.codename};
                    }));
                })
                .catch(err => console.log(err));
        }));
    }

}
