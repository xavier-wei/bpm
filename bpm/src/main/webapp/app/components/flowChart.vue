<template>
  <div>
    <section class="container">
      <div style="display: flex; justify-content: center; align-items: center; ">
        <img :src=filePathNameProp.filePathName alt=""/>
      </div>
    </section>

  </div>
</template>

<script lang="ts">
import IFormGroupCheck from "@/shared/form/i-form-group-check.vue";
import IDualDatePicker from "@/shared/i-date-picker/i-dual-date-picker.vue";
import IDatePicker from "@/shared/i-date-picker/i-date-picker.vue";
import {onMounted, reactive, ref, computed, watch} from "@vue/composition-api";

export default {
  name: "flowChart",
  components: {
    'i-dual-date-picker': IDualDatePicker,
    'i-date-picker': IDatePicker,
    'i-form-group-check': IFormGroupCheck,
  },
  props: {
    filePathName: {
      type: Object,
      required: false,
    },
  },
  setup(props) {
    const filePathNameProp = reactive(props.filePathName);
    const currentScale = ref(0.8);

    // 在組件掛載後，加上滑鼠滾輪監聽
    onMounted(() => {
      window.addEventListener('wheel', handleWheel);
    });

    // 處理滑鼠滾輪
    const handleWheel = (event) => {
      if (event.ctrlKey) { // 检查是否按下了Ctrl键
        const newScale = currentScale.value + (event.deltaY > 0 ? -0.1 : 0.1); // 根据滚轮方向调整缩放比例
        currentScale.value = Math.max(0.1, Math.min(2, newScale)); // 限制缩放比例在0.1到2之间
      }
    };

    return {
      filePathNameProp
    }

  }
}
</script>

<style scoped>
</style>
