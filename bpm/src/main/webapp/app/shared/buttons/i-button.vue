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
