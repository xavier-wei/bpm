<template>

</template>

<script>
import {ref, watch} from '@vue/composition-api';
import {useGetters, useStore} from "@u3u/vue-hooks";
import axios from "axios";
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";

export default {
  name: "userSys",
  setup() {
    const userAllData = ref(useGetters(['getUserAllData']).getUserAllData).value
    const notificationService = useNotification();

    async function toQuery() {

      await axios.get('/eip/getUsers')
        .then(({data}) => {
          useStore().value.commit('setUserAllData', data);
        })
        .catch(notificationErrorHandler(notificationService))
    };

    watch(userAllData, () => {
        if (userAllData.length === 0) {
          toQuery();
        }
      },
      {immediate: true}
    )


    return {
      userAllData,
    }

  }
}
</script>

<style scoped>

</style>
