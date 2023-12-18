import axios from "axios";
import {Store} from "vuex";
import {useStore} from "@u3u/vue-hooks";

export default class BpmUserDataService {
  constructor(private store: Store<any>) {
    this.getUser();
  }
  private async getUser() {
    await axios
      .get('/loginBpmDev')
      .then(res => {
        //如果給res.data內沒有titleName就給UserModel預設值
        if (!('titleName' in res.data)) {
          res.data.titleName = null;
        }
        //如果給res.data內沒有userRole就給UserModel預設值
        if (!('userRole' in res.data)) {
          res.data.userRole = null;
        }
        //keycloak 裡面沒有英文名子,所以從前端組出來, 參考eip系統用mail去組英文名 (UsersDaoImpl裡的getEip02wUsers method)
        if (res.data.email !== null && res.data.email !== undefined) {
          res.data.userEName = res.data.email.substring(0, res.data.email.indexOf('@'));
        } else {
          res.data.userEName = '';
        }
        console.log(' 登入者資訊 : ', JSON.parse(JSON.stringify(res.data)));
        useStore().value.commit('setUserData', res.data);
      })
      .catch(err => console.log(err));
  }
}
