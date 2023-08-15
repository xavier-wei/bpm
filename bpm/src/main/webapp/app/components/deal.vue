<template>
  <div>
    <section class="container mt-2">
      <div class="card">
        <div class="card-body">
          <b-form-row>
            <i-form-group-check
              class="col-sm-12"
              label-cols="2"
              content-cols="8"
              :label="'選擇腳色 ：'"
              :item="form"
              style="margin-left: 7px"
            >
              <b-form-select v-model="form.user" :options="options.selectRole"> </b-form-select>
            </i-form-group-check>
          </b-form-row>
        </div>
      </div>
    </section>
  </div>
</template>

<script lang="ts">
import { reactive, ref, watch } from '@vue/composition-api';
import { useStore } from '@u3u/vue-hooks';
import IFormGroupCheck from '@/shared/form/i-form-group-check.vue';

export default {
  name: 'deal',
  components: {
    'i-form-group-check': IFormGroupCheck,
  },
  setup() {
    const formDefault = {
      user: 'ApplyTester',
    };
    let form = reactive(Object.assign({}, formDefault));

    const userData = ref('');

    const options = reactive({
      selectRole: [
        { value: 'ApplyTester', text: '申請人' },
        { value: 'ChiefTester', text: '科長' },
        { value: 'DirectorTester', text: '主管' },
        { value: 'InfoTester', text: '資推' },
        { value: 'seniorTechSpecialistTester', text: '簡任技正/科長' },
        { value: 'serverRoomOperatorTester', text: '機房操作人員' },
        { value: 'reviewStaffTester', text: '複核人員' },
        { value: 'serverRoomManagerTester', text: '機房管理人員' },
      ],
    });

    watch(
      () => form.user,
      () => {
        console.log('更換:: ',form.user)
        useStore().value.commit('setUserData', { user: form.user });
      },
      { immediate: true }
    );

    return {
      form,
      userData,
      options,
    };
  },
};
</script>

<style scoped>
</style>

