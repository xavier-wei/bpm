<template>
  <b-button variant="info" v-bind="$attrs" v-on="$listeners">
    {{ buttonText }}
    <svg
      v-if="type === 'send-check'"
      xmlns="http://www.w3.org/2000/svg"
      width="16"
      height="16"
      fill="currentColor"
      class="bi b-icon bi-send-check"
      viewBox="0 0 16 16"
    >
      <path
        d="M15.964.686a.5.5 0 0 0-.65-.65L.767 5.855a.75.75 0 0 0-.124 1.329l4.995 3.178 1.531 2.406a.5.5 0 0 0 .844-.536L6.637 10.07l7.494-7.494-1.895 4.738a.5.5 0 1 0 .928.372l2.8-7Zm-2.54 1.183L5.93 9.363 1.591 6.602l11.833-4.733Z"
      />
      <path
        d="M16 12.5a3.5 3.5 0 1 1-7 0 3.5 3.5 0 0 1 7 0Zm-1.993-1.679a.5.5 0 0 0-.686.172l-1.17 1.95-.547-.547a.5.5 0 0 0-.708.708l.774.773a.75.75 0 0 0 1.174-.144l1.335-2.226a.5.5 0 0 0-.172-.686Z"
      />
    </svg>
    <svg
      v-else-if="type === 'x-lg'"
      xmlns="http://www.w3.org/2000/svg"
      width="16"
      height="16"
      fill="currentColor"
      class="bi b-icon bi-x-lg"
      viewBox="0 0 16 16"
    >
      <path fill-rule="evenodd"
            d="M13.854 2.146a.5.5 0 0 1 0 .708l-11 11a.5.5 0 0 1-.708-.708l11-11a.5.5 0 0 1 .708 0Z"/>
      <path fill-rule="evenodd"
            d="M2.146 2.146a.5.5 0 0 0 0 .708l11 11a.5.5 0 0 0 .708-.708l-11-11a.5.5 0 0 0-.708 0Z"/>
    </svg>
    <svg
      v-else-if="type === 'save'"
      xmlns="http://www.w3.org/2000/svg"
      width="16"
      height="16"
      fill="currentColor"
      class="bi b-icon bi-save"
      viewBox="0 0 16 16"
    >
      <path
        d="M2 1a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H9.5a1 1 0 0 0-1 1v7.293l2.646-2.647a.5.5 0 0 1 .708.708l-3.5 3.5a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L7.5 9.293V2a2 2 0 0 1 2-2H14a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h2.5a.5.5 0 0 1 0 1H2z"
      />
    </svg>
    <svg
      v-else-if="type === 'arrow-counterclockwise'"
      xmlns="http://www.w3.org/2000/svg"
      width="16"
      height="16"
      fill="currentColor"
      class="bi b-icon bi-arrow-counterclockwise"
      viewBox="0 0 16 16"
    >
      <path fill-rule="evenodd" d="M8 3a5 5 0 1 1-4.546 2.914.5.5 0 0 0-.908-.417A6 6 0 1 0 8 2v1z"/>
      <path d="M8 4.466V.534a.25.25 0 0 0-.41-.192L5.23 2.308a.25.25 0 0 0 0 .384l2.36 1.966A.25.25 0 0 0 8 4.466z"/>
    </svg>
    <b-icon v-else :icon="type"></b-icon>

  </b-button>
</template>

<script lang="ts">
import {BButton, BIcon, BootstrapVueIcons} from 'bootstrap-vue';
import {computed} from '@vue/composition-api';
import jsonData from "@/shared/buttons/button-name.json";

export default {
  name: 'i-button',
  components: {BButton, BIcon},
  use: {BootstrapVueIcons},
  props: {
    type: {
      type: String,
      required: true,
    },
  },
  setup(props) {
    //bpm不用i18n，也沒有使用資料庫管理按鈕名稱，所以暫且用 button-name.json 這個json做管理
    const buttonText = computed(() => {
      const matchingText = jsonData.find(item => item.code === props.type);
      return matchingText ? matchingText.text : '';
    });
    return {
      buttonText
    }

  }
};
</script>
