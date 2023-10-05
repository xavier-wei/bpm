<template>
  <div>
    <div class="card" style="display: flex; justify-content: center; align-items: center; ">
      <img
        :src="filePathNameProp.filePathName"
        :style="{ transform: `scale(${scale}) translate(${translateX}px, ${translateY}px)` }"
        @wheel="handleWheel"
        @mousedown="startDrag($event)"
        @mousemove="handleDrag"
        @mouseup="stopDrag"
        @mouseleave="stopDrag"
        alt=""
      />
    </div>
  </div>
</template>

<script lang="ts">
import IFormGroupCheck from "@/shared/form/i-form-group-check.vue";
import IDualDatePicker from "@/shared/i-date-picker/i-dual-date-picker.vue";
import IDatePicker from "@/shared/i-date-picker/i-date-picker.vue";
import {onMounted, reactive, ref, onUnmounted} from "@vue/composition-api";

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
    const scale = ref(1); // 初始化縮放比例
    const drag = ref(false);
    const startX = ref(0);
    const startY = ref(0);
    const translateX = ref(0);
    const translateY = ref(0);

    const handleWheel = (event) => {
      if (event.ctrlKey) {
        event.preventDefault(); //阻止頁面整體縮放
        const delta = event.deltaY > 0 ? -0.1 : 0.1; //根據滑鼠滾輪計算縮放
        scale.value = Math.min(Math.max(0.1, scale.value + delta), 2); //限制圖片縮放比例在在0.1到2之間
      }
    };

    const startDrag = (event) => {
      if (event.button === 0) {
        event.preventDefault(); //阻止瀏覽器默認行為 舉例:不加這個 瀏覽器預設是點下左鍵 要先移動一段距離 才會觸發
        drag.value = true;
        startX.value = event.clientX - translateX.value;
        startY.value = event.clientY - translateY.value;
      }
    };

    const handleDrag = (event) => {
      if (drag.value) {
        translateX.value = event.clientX - startX.value;
        translateY.value = event.clientY - startY.value;
      }
    };

    const stopDrag = () => {
      drag.value = false;
    };

    onMounted(() => {
      window.addEventListener("wheel", handleWheel, { passive: false });
    });

    onUnmounted(() => {
      window.removeEventListener("wheel", handleWheel);
    });


    return {
      scale,
      filePathNameProp,
      startDrag,
      handleDrag,
      stopDrag,
      translateX,
      translateY,
      handleWheel,
    }

  }
}
</script>

<style scoped>
</style>
