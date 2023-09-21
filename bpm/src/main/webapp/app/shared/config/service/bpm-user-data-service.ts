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
        //keycloak 裡面沒有英文名子,所以從前端組出來, 參考eip系統用mail去組英文名 (UsersDaoImpl裡的getEip02wUsers method)
        res.data.userEName = '';

        if (res.data.email !== null && res.data.email !== undefined) {
          res.data.userEName = res.data.email.substring(0,res.data.email.indexOf('@'))
        }

        console.log(' 登入者資訊 : ', JSON.parse(JSON.stringify(res.data)))

        useStore().value.commit('setUserData', res.data);
      })
      .catch(err => console.log(err));
  }

}
