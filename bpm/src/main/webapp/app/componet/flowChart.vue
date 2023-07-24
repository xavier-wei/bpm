<template>
  <div>
    <section class="container">
      <div class="card">
          <img :src=filePathNameProp.filePathName alt="" />
      </div>
    </section>
  </div>
</template>

<script lang="ts">
import {BContainer, BRow} from "bootstrap-vue";
import {reactive, watch} from '@vue/composition-api';

export default {
  name: "flowChart",
  components: {
    'b-container': BContainer,
    'b-row': BRow,
  },
  props: {
    filePathName: {
      type: Object,
      required: false,
    },
  },
  setup(props){
    const filePathNameProp = reactive(props.filePathName);

    watch(
      filePathNameProp,
      newValue => {
        console.log('filePathNameProp++++',filePathNameProp.filePathName)
      },
      { immediate: true }
    );



    return{
      filePathNameProp,
    }

  }
}
</script>

<style scoped>
</style>
