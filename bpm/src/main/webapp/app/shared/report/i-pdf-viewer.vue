<template>
  <div>
    <b-modal
      v-model="modalData.data.visible"
      id="pdf-viewer"
      size="xl"
      :title="mobileDevice ? 'PDF download' : 'PDF-Viewer'"
      header-bg-variant="secondary"
      header-text-variant="light"
      hide-footer
      content-class="pdf-viewer-100"
      dialog-class="pdf-viewer"
      @close="modalData.data.visible = false"
    >
      <b-link v-if="mobileDevice" :href="modalData.data.pdfSrc" target="_blank">下載PDF</b-link>
      <iframe v-else style="height: 100%; min-width: 100%;" :src="modalData.data.pdfSrc + '#view=fitH'" />      
    </b-modal>
  </div>
</template>

<script>
import { BModal, BLink } from 'bootstrap-vue';
import { reactive, ref, onMounted } from '@vue/composition-api';

export default {
  name: 'i-pdf-viewer',
  components: { BModal, BLink },
  setup(props) {
    const modalData = reactive({
      data: {
        pdfSrc: '',
        visible: false
      }
    });
    const mobileDevice = ref(false);

    function isShowDia(parentToChildData, isVisible) {
      if (!parentToChildData) {
        return;
      }
      modalData.data.visible = isVisible;
      modalData.data.pdfSrc = parentToChildData;
    }

    function isMobileDevice() {
      const mobileDevices = ['Android', 'webOS', 'iPhone', 'iPad', 'iPod', 'BlackBerry', 'Windows Phone']
      return mobileDevices.some(e => navigator.userAgent.match(e))
    }

    onMounted(() => {
      mobileDevice.value = isMobileDevice();
      // mobileDevice.value = true;
    });

    return {
      isShowDia,
      modalData,
      mobileDevice
    }
  }
};
</script>
<style>
/* .modal-dialog {
    max-width: 80%;
    height: 90%;
    display: flex;
} */
.pdf-viewer {
  height: 90%;
}
.pdf-viewer-100 {
  height: 100%;
}
</style>
