<template>
  <div>
    <a title="設定" ref="tableFieldsPickerConfigLink" @click="state.methods.showPickerModal">
      <font-awesome-icon icon="cog"></font-awesome-icon>
    </a>
    <div ref="tableFieldsPickerDraggableContainer" class="card shadow" v-show="state.isShowPicker">
      <div class="card-header cursor-pointer" @mousedown="state.methods.dragging.dragMouseDown">
        <font-awesome-icon icon="th-list"></font-awesome-icon>
        <span class="h5">顯示/隱藏 欄位</span>
      </div>
      <b-form class="card-body p-0">
        <b-form-group label-for="field.key">
          <draggable v-model="state.optionalFields" @start="drag = true" @end="drag = false" @change="state.methods.handleChangedField">
            <b-form-checkbox
              v-for="field in state.optionalFields"
              :key="field.key"
              v-model="field.visible"
              :value="true"
              :disabled="state.methods.computeDisableField(field)"
              @change="state.methods.handleChangedField"
              class="m-2"
            >
              {{ field.label ? field.label : field.key }}
            </b-form-checkbox>
          </draggable>
        </b-form-group>
      </b-form>
      <div class="card-footer">
        <b-button @click="state.methods.handleResetOptionalFields" class="float-right">
          <font-awesome-icon icon="undo"></font-awesome-icon>
          重設欄位
        </b-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, nextTick, onMounted, reactive, ref } from '@vue/composition-api';
import { onClickOutside, useWindowSize } from '@vueuse/core';
import { BButton, BForm, BFormCheckbox, BFormGroup } from 'bootstrap-vue';
import draggable from 'vuedraggable';

export default {
  name: 'table-fields-picker',
  components: { draggable, BButton, BForm, BFormCheckbox, BFormGroup },
  props: ['fields'],
  setup(props, { emit }) {
    const windowSize = useWindowSize();
    const tableFieldsPickerConfigLink = ref(null);
    const tableFieldsPickerDraggableContainer = ref(null);

    const state = reactive({
      optionalFields: [], // 存放可顯示隱藏、以及可拖曳之欄位清單。
      pickedFields: undefined,
      isShowPicker: false,
      positions: {
        clientX: undefined,
        clientY: undefined,
        movementX: 0,
        movementY: 0,
      },
      methods: {
        initializeFields: undefined,
        handleChangedField: undefined,
        handleResetOptionalFields: undefined,
        showPickerModal: undefined,
        computeDisableField: undefined,
        dragging: {
          dragMouseDown: undefined,
          elementDrag: undefined,
          closeDragElement: undefined,
        },
      },
    });

    onClickOutside(tableFieldsPickerDraggableContainer, () => {
      state.isShowPicker = false;
    });

    state.methods.showPickerModal = () => {
      //
      state.isShowPicker = true;
      //
      nextTick().then(() => {
        const container = tableFieldsPickerDraggableContainer.value;
        if (!container.style || !container.style.top) {
          const containerWidth = container.getBoundingClientRect().width;
          const configLink = tableFieldsPickerConfigLink.value;
          const configLinkX = configLink.getBoundingClientRect().left;
          const configLinkHeight = configLink.getBoundingClientRect().height;
          const configLinkTop = configLink.getBoundingClientRect().top;

          // 掛載到 body 之下方便計算 top, left
          document.body.appendChild(tableFieldsPickerDraggableContainer.value);
          tableFieldsPickerDraggableContainer.value.style.zIndex = '9999';
          tableFieldsPickerDraggableContainer.value.style.position = 'absolute';
          tableFieldsPickerDraggableContainer.value.style.minWidth = containerWidth + 'px';
          tableFieldsPickerDraggableContainer.value.style.top = configLinkTop + configLinkHeight + 'px';
          tableFieldsPickerDraggableContainer.value.style.left = configLinkX + 'px';
          tableFieldsPickerDraggableContainer.value.onDragStart = null;
        }
      });
      // v-show 才可以在這使用 style
    };

    state.methods.dragging.dragMouseDown = event => {
      event.preventDefault();
      // get the mouse cursor position at startup:
      state.positions.clientX = event.clientX;
      state.positions.clientY = event.clientY;
      document.onmousemove = state.methods.dragging.elementDrag;
      document.onmouseup = state.methods.dragging.closeDragElement;
    };

    state.methods.dragging.elementDrag = event => {
      event.preventDefault();
      state.positions.movementX = state.positions.clientX - event.clientX;
      state.positions.movementY = state.positions.clientY - event.clientY;
      state.positions.clientX = event.clientX;
      state.positions.clientY = event.clientY;
      let diffTop = tableFieldsPickerDraggableContainer.value.offsetTop - state.positions.movementY;
      let diffLeft = tableFieldsPickerDraggableContainer.value.offsetLeft - state.positions.movementX;
      //
      diffTop = diffTop <= 0 ? 0 : diffTop;
      diffLeft = diffLeft <= 0 ? 0 : diffLeft;
      diffTop = diffTop >= windowSize.height.value - 60 ? windowSize.height.value - 60 : diffTop;
      diffLeft = diffLeft >= windowSize.width.value - 100 ? windowSize.width.value - 100 : diffLeft;
      // set the element's new position:
      tableFieldsPickerDraggableContainer.value.style.top = diffTop + 'px';
      tableFieldsPickerDraggableContainer.value.style.left = diffLeft + 'px';
    };

    state.methods.dragging.closeDragElement = () => {
      document.onmouseup = null;
      document.onmousemove = null;
    };

    // 當欄位發生值變化，則 emit 一個 'picked' 事件，並拋出挑選後的欄位
    state.methods.handleChangedField = () => emit('picked', state.pickedFields);

    // 初始化傳入之欄位，副本一份傳入的 fields 並將每一個欄位賦予預設值，若沒有設定值的話。
    state.methods.initializeFields = fields => {
      state.optionalFields.length = 0;
      //TODO: 當沒有傳入欄位的定義檔時候，應該要有對應做法
      if (!fields || fields.length == 0) {
        return;
      }
      fields.forEach(function (field) {
        state.optionalFields.push({
          key: field.key,
          label: field.label,
          // eslint-disable-next-line no-prototype-builtins
          visible: !field.hasOwnProperty('visible') || (field.hasOwnProperty('visible') && field['visible']),
        });
      });
    };

    // 根據 checkbox 的變化，監聽計算出最後挑選的欄位
    state.pickedFields = computed(() => {
      // console.info('call computed pickedFields');
      let _fields = Object.assign([], props.fields);
      return (
        state.optionalFields
          // 從可挑選的欄位的清單中先過濾要顯示的欄位，
          .filter(optionField => {
            return optionField.visible;
          })
          // 根據挑選後的欄位鍵值，有序的方式將原始欄位資料取出，並彙整成一份新的 array 回傳
          .reduce((accumulator, currentValue) => {
            accumulator.push(_fields.find(field => field.key === currentValue.key));
            return accumulator;
          }, [])
      );
    });

    // fix: 解決欄位全部隱藏的議題。
    // 當最後選定的顯示欄位只有一個時候，則 disable 顯示的欄位
    state.methods.computeDisableField = field => {
      return state.pickedFields.length == 1 && field.visible;
    };

    // 處理欄位重置狀態，回復到初始化狀態
    state.methods.handleResetOptionalFields = () => {
      state.methods.initializeFields(props.fields);
      state.methods.handleChangedField();
    };

    //當畫面掛載後，則觸發一次，以利畫面初始化
    onMounted(state.methods.handleResetOptionalFields);

    return {
      state,
      tableFieldsPickerConfigLink,
      tableFieldsPickerDraggableContainer,
    };
  },
};
</script>

<style scoped>
.cursor-pointer {
  cursor: grab;
}
</style>
