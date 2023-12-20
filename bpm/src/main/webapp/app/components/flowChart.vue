<template>
  <div >

    <b-button class="transparent-btn" @click="zoomIn()">
      <b-icon class="icon" icon="zoom-in"></b-icon>
    </b-button>

    <b-button class="transparent-btn" @click="zoomOut()">
      <b-icon class="icon" icon="zoom-out"></b-icon>
    </b-button>

    <b-button class="transparent-btn" @click="recover()">
      <b-icon class="icon" icon="arrow-clockwise"></b-icon>
    </b-button>

    <div class="card " style="display: flex; justify-content: center; align-items: center; overflow: hidden; ">
      <img
        :src="filePathNameProp.filePathName"
        :style="{ transform: `scale(${scale}) translate(${translateX}px, ${translateY}px)` }"
        @wheel="handleWheel"
        @mousedown.stop="startDrag($event)"
        @mousemove.stop="handleDrag"
        @mouseup.stop="stopDrag"
        @mouseleave.stop="stopDrag"
        @touchstart.stop="startTouch"
        @touchmove.stop="handleTouch"
        @touchend.stop="stopTouch"
        @click.stop="handleClick"
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
import IButton from '@/shared/buttons/i-button.vue';

export default {
  name: "flowChart",//流程圖共用模組
  components: {
    'i-dual-date-picker': IDualDatePicker,
    'i-date-picker': IDatePicker,
    'i-form-group-check': IFormGroupCheck,
    'i-button': IButton,
  },
  props: {
    filePathName: {
      type: Object,
      required: false,
    },
  },
  setup(props) {

    //接收父層傳來的base64，直接傳給畫面的 <img>
    const filePathNameProp = reactive(props.filePathName);

    // 初始化縮放比例
    const scale = ref(1);

    const drag = ref(false);
    const startX = ref(0);
    const startY = ref(0);
    const translateX = ref(0);
    const translateY = ref(0);

    const handleWheel = (event) => {
      const isCtrlPressed = (event.ctrlKey && !event.metaKey) || (!event.ctrlKey && event.metaKey);
      if (isCtrlPressed) { // 检查是否按下了Ctrl键
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

    //點擊次數
    let clickCount = 0;
    // 設定最大點擊間隔時間
    const MAX_CLICK_INTERVAL = 500;

    const handleClick = () => {
      clickCount++;
      if (clickCount === 1) {
        setTimeout(() => {
          if (clickCount === 1) {
            // 單擊事件 不做事
          } else {
            // 雙擊事件
            translateX.value = 0;
            translateY.value = 0;
            scale.value = 1;
          }
          clickCount = 0;
        }, MAX_CLICK_INTERVAL);
      }
    };


    const stopDrag = () => {
      drag.value = false;
    };

    const zoomIn = () => {
      scale.value = Math.min(scale.value + 0.1, 2);
    };

    const zoomOut = () => {
      scale.value = Math.max(scale.value - 0.1, 0.1);
    };
    const recover = () => {
      translateX.value = 0;
      translateY.value = 0;
      scale.value = 1;
    };

    //手機平板觸發的事件
    const startTouch = (event) => {
      if (event.touches.length === 1) {
        event.preventDefault();
        drag.value = true;
        const touch = event.touches[0];
        startX.value = touch.clientX - translateX.value;
        startY.value = touch.clientY - translateY.value;
      }
    };

    //手機平板觸發的事件
    const handleTouch = (event) => {
      if (drag.value && event.touches.length === 1) {
        const touch = event.touches[0];
        translateX.value = touch.clientX - startX.value;
        translateY.value = touch.clientY - startY.value;
      }
    };

    //手機平板觸發的事件
    const stopTouch = () => {
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
      handleClick,
      zoomIn,
      zoomOut,
      recover,
      startTouch,
      handleTouch,
      stopTouch,
    }

  }
}
</script>

<style scoped>

/* 半透明按鈕樣式 */
.transparent-btn {
  background-color: rgba(255, 255, 255, 0.5); /* 半透明白色背景 */
  border: none; /* 移除邊框 */
  cursor: pointer; /* 鼠標樣式 */
}

/* 圖示樣式（可根據需要調整大小和颜色） */
.icon {
  font-size: 1.5rem; /* 圖示大小 */
  color: #000; /* 圖示颜色 */
}

</style>
