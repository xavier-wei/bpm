import axios from "axios";
import {Store} from "vuex";
import {useStore} from "@u3u/vue-hooks";
export default class BpmUserDataService {
    constructor(private store: Store<any>) {
        this.getUser();
    }
    private getUser() {
        //  bpmErrandOptions
            axios
                .get('/loginBpmDev')
                .then(res => {
                    console.log('登入者資訊++',res.data)
                    useStore().value.commit('setUserData', res.data);
                })
                .catch(err => console.log(err));
    }

}
