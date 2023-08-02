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
import IFormGroupCheck from "@/shared/form/i-form-group-check.vue";
import IDualDatePicker from "@/shared/i-date-picker/i-dual-date-picker.vue";
import IDatePicker from "@/shared/i-date-picker/i-date-picker.vue";

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
    return {
      filePathNameProp,
    }

  }
}
</script>

<style scoped>
</style>
