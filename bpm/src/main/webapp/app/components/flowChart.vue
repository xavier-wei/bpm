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
import {reactive} from '@vue/composition-api';

export default {
  name: "flowChart",
  props: {
    filePathName: {
      type: Object,
      required: false,
    },
  },
  setup(props){
    const filePathNameProp = reactive(props.filePathName);
    return{
      filePathNameProp,
    }

  }
}
</script>

<style scoped>
</style>
