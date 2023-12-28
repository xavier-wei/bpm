<template>
  <b-card no-body :class="{ 'border-0': border0 }" v-if="$attrs.items || state.itemsUndefinedBehaviorComputed">
    <slot name="table-top" />
    <b-card-header :class="{ 'd-none': hideCardHeader }" v-if="($attrs.items && $attrs.items.length > 0) || !showFilter">
      <h5 class="my-0" v-if="!showFilter">
        <font-awesome-icon icon="list"></font-awesome-icon>
        {{ title }}
        <table-fields-picker
          v-show="$attrs.items && $attrs.items.length > 0 && !mobileDevice"
          v-bind="$attrs"
          @picked="handlePicked"
          class="float-right"
          ref="fieldsPickerComponent"
        />
        <font-awesome-icon
          v-if="exportExcelProp"
          class="ml-2 cursor-pointer"
          icon="file-excel"
          size="lg"
          @click.stop="onExportExcelClick"
        ></font-awesome-icon>
      </h5>
      <div style="margin-left: -20px" v-else>
        <b-form-group
          class="col-4 pl-0 mb-0 float-left"
          label="篩選："
          label-for="filter-input"
          label-cols="3"
          label-align="right"
          :label-size="size"
        >
          <b-input-group :size="size">
            <b-form-input
              id="filter-input"
              v-model="state.filter"
              type="search"
              placeholder="輸入篩選關鍵字"
              aria-label="search"
            ></b-form-input>
            <b-input-group-append>
              <b-button :disabled="!state.filter" @click="state.filter = ''">清除</b-button>
            </b-input-group-append>
          </b-input-group>
        </b-form-group>
        <b-form-checkbox-group
          class="pt-1 pl-2 d-inline-block"
          v-model="state.filterOn"
          :size="size"
          :options="state.filterFields"
          value-field="key"
          text-field="label"
        />
        <h5 class="pt-1 my-0 d-inline-block float-right">
          <table-fields-picker
            v-if="$attrs.items && $attrs.items.length > 0"
            v-show="!mobileDevice"
            v-bind="$attrs"
            @picked="handlePicked"
            ref="fieldsPickerComponent"
          />
        </h5>
        <font-awesome-icon
          v-if="exportExcelProp"
          class="ml-2 cursor-pointer"
          icon="file-excel"
          size="lg"
          @click.stop="onExportExcelClick"
        ></font-awesome-icon>
      </div>
    </b-card-header>
    <b-card-body class="m-0 p-0">
      <!-- I-Table 本體 -->
      <b-table
        hover
        show-empty
        responsive
        class="m-0"
        v-bind="$attrs"
        v-on="$listeners"
        :thead-class="{ 'd-none': !($attrs.items && $attrs.items.length > 0) }"
        :bordered="bordered"
        :fields="state.pickedFields"
        :per-page="state.pagination.perPage"
        :current-page="isServerSidePaging ? 1 : state.pagination.currentPage"
        :sort-by="pagination.sortBy[0]"
        :sort-desc="pagination.sortDirection.toUpperCase() === 'DESC'"
        :empty-text="state.emptyText"
        :empty-filtered-text="state.emptyFilterText"
        :no-sort-reset="isServerSidePaging"
        :no-local-sorting="isServerSidePaging"
        :filter="state.filter"
        :filter-included-fields="state.filterOn"
        :filter-ignored-fields="state.ignoredFields"
        :isShowDetail="state.isShowDetail"
        @filtered="state.filtered"
        @sort-changed="handleSortChanged"
        @row-clicked="rowClickForMobileModal($event)"
      >
        <template v-for="(_, slot) of $scopedSlots" v-slot:[slot]="scope">
          <slot :name="slot" v-bind="scope" />
        </template>

        <template #empty>
          <div class="text-center m-3" v-if="state.itemsUndefinedBehaviorComputed">
            <b-spinner></b-spinner>
            <p>查詢中，請稍候...</p>
          </div>
        </template>

        <!-- 序號 -->
        <template #cell(tableNo)="row">
          {{ state.pagination.start + row.index }}
        </template>
      </b-table>
    </b-card-body>
    <b-card-footer :class="{ 'd-none': hideCardFooter }" v-if="$attrs.items && $attrs.items.length > 0 && state.showFooter">
      <!-- 分頁 -->
      <b-row align-h="around" align-v="center">
        <b-col v-if="!simpleCardFooter" md="5" xl="3" order="2" order-md="1" order-xl="1" class="pt-1 pt-md-0 per-page-select">
          <b-input-group prepend="每頁" append="筆" :size="size" label-for="perpage">
            <b-form-select
              label-for="選擇每頁比數"
              aria-label="perpage"
              id="perpage"
              v-model="state.pagination.perPage"
              :options="state.pagination.perPageOptions"
              @input="handlePageChanged()"
            ></b-form-select>
          </b-input-group>
        </b-col>
        <b-col :md="simpleCardFooter ? '5' : '7'" :xl="simpleCardFooter ? '5' : '3'" order="1" order-md="2" order-xl="2">
          <div>
            <b-pagination
              class="my-0"
              align="fill"
              :size="size"
              :value="state.pagination.currentPage"
              :total-rows="state.pagination.totalRows"
              :per-page="state.pagination.perPage"
              :limit="3"
              @change="handlePageChanged($event)"
            />
          </div>
        </b-col>
        <b-col
          :md="simpleCardFooter ? '4' : '8'"
          :xl="simpleCardFooter ? '4' : '3'"
          order="4"
          order-md="4"
          order-xl="3"
          class="pt-1 pt-xl-0 text-center"
        >
          第 {{ state.pagination.start }} 到 {{ state.pagination.end }} , 共
          {{ state.pagination.totalRows ? state.pagination.totalRows : '沒給totalItems' }} 筆 , 頁次 {{ state.pagination.currentPage }}/{{
            state.pagination.totalPages
          }}
        </b-col>
        <b-col v-if="!simpleCardFooter" md="4" xl="3" order="3" order-md="3" order-xl="4" class="pt-1 pt-xl-0">
          <b-input-group prepend="前往頁數" :size="size" label-for="gotoPage">
            <b-form-input
              aria-label="gotoPage"
              id="gotoPage"
              v-model="state.pagination.gotoPage"
              @keydown.enter.prevent="handleGotoPage"
              label-for="輸入要前往的頁數"
            />
            <b-input-group-append>
              <b-button variant="outline-success" @click="handleGotoPage" label-for="前往">GO</b-button>
            </b-input-group-append>
          </b-input-group>
        </b-col>
      </b-row>
    </b-card-footer>
    <b-modal
      v-model="modalVisible"
      id="i-table-model"
      size="xl"
      title="詳細資料"
      header-bg-variant="secondary"
      header-text-variant="light"
      hide-footer
      @close="toggleModal(false)"
    >
      <b-table-simple class="i-table">
        <b-tr v-for="modalItem in modalData" :key="modalItem.key">
          <b-th>{{ modalItem.label }}</b-th>
          <b-td>{{ modalItem.value }}</b-td>
        </b-tr>
      </b-table-simple>
    </b-modal>
  </b-card>
</template>

<script lang="ts">
import { formatDate, formatToString } from '@/shared/date/minguo-calendar-utils';
import { commaFormatter, formOptionsFormatter } from '@/shared/formatter/common';
import TableFieldsPicker from '@/shared/table-fields-picker/table-fields-picker.vue';
import { computed, onMounted, reactive, ref, watch } from '@vue/composition-api';
import {
  BButton,
  BCard,
  BCardBody,
  BCardFooter,
  BCardHeader,
  BCol,
  BFormCheckboxGroup,
  BFormGroup,
  BFormInput,
  BFormSelect,
  BInputGroup,
  BInputGroupAppend,
  BPagination,
  BRow,
  BSpinner,
  BTable,
  BTableSimple,
  BTd,
  BTh,
  BTr,
} from 'bootstrap-vue';
import ExcelJs from 'exceljs';
import {
  assign as _assign,
  cloneDeep as _cloneDeep,
  forEach as _forEach,
  includes as _includes,
  isArray as _isArray,
  isEqual as _isEqual,
  keys as _keys,
  trim as _trim,
  isString as _isString,
  isObject as _isObject,
  isNumber as _isNumber,
  size as _size,
} from 'lodash';
import { Pagination } from '../model/pagination.model';
import { useGetters } from '@u3u/vue-hooks';

export default {
  name: 'i-table',
  inheritAttrs: false,
  components: {
    TableFieldsPicker,
    BButton,
    BCard,
    BCardBody,
    BCardFooter,
    BCardHeader,
    BCol,
    BFormCheckboxGroup,
    BFormGroup,
    BFormInput,
    BFormSelect,
    BInputGroup,
    BInputGroupAppend,
    BPagination,
    BRow,
    BSpinner,
    BTable,
    BTableSimple,
    BTd,
    BTh,
    BTr,
  },
  props: {
    // card-header裡的標題，只有在不顯示篩選器(showFilter為false)時才有用
    title: {
      type: String,
      required: false,
      default: '查詢結果',
    },
    // 當items為undefined時候，表格呈現的行為。hide：預設值，表格不呈現。loading：則表格會呈現查詢中的圖示。
    itemsUndefinedBehavior: {
      type: String,
      required: false,
      default: 'hide',
    },
    // 是否後端分頁
    isServerSidePaging: {
      type: Boolean,
      required: false,
      default: true,
    },
    // 資料總筆數，後端分頁(isServerSidePaging為true)時必須傳遞的參數
    totalItems: {
      type: Number,
      required: false,
    },
    // 是否去除包住i-table的card外框
    border0: {
      type: Boolean,
      required: false,
      default: false,
    },
    // 是否顯示i-table中b-table(card-body)本身的框線
    bordered: {
      type: Boolean,
      required: false,
      default: true,
    },
    // 是否將card-header內容換成篩選器
    showFilter: {
      type: Boolean,
      default: false,
      required: false,
    },
    // 篩選器要去除的欄位，傳遞方式為陣列，value為fields裡的key值，例如:filter-ignored-fields="['no', 'subject']"
    filterIgnoredFields: {
      type: Array,
      required: false,
      default: () => [],
    },
    // card-header及card-footer裡的元件尺寸
    size: {
      type: String,
      required: false,
      default: 'md',
    },
    // 隱藏序號field
    hideNo: {
      type: Boolean,
      required: false,
      default: false,
    },
    // 隱藏card-header
    hideCardHeader: {
      type: Boolean,
      required: false,
      default: false,
    },
    // 隱藏card-footer
    hideCardFooter: {
      type: Boolean,
      required: false,
      default: false,
    },
    // 只顯示分頁選項跟分頁資訊
    simpleCardFooter: {
      type: Boolean,
      required: false,
      default: false,
    },
    // 是否顯示手機板Detail
    isShowDetail: {
      type: Boolean,
      required: false,
      default: true,
    },
    exportExcel: {
      type: Boolean,
      required: false,
      default: false,
    },
    exportExcelFileName: {
      type: String,
      required: false,
      default: '',
    },
    exportExcelFileExtension: {
      type: String,
      required: false,
      default: '',
    },
    customPerPageOptions: {
      type: Array,
      required: false,
      default: () => [],
    },
    sortBy: {
      type: Array,
      required: false,
      default: () => [],
    },
    sortDirection: {
      type: String,
      required: false,
      default: 'ASC',
    },
  },
  setup(props, context) {
    const mobileDevice = computed(() => useGetters(['mobileDevice']).mobileDevice.value);
    const padDevice = computed(() => useGetters(['padDevice']).padDevice.value);
    const deskTopDevice = computed(() => useGetters(['deskTopDevice']).deskTopDevice.value);

    const modalVisible = ref(false);
    const modalData = ref([]);
    const fieldsPickerComponent = ref(null);

    // 在table最左側添加流水序號
    // 第三個判斷是否已經有tableNo，是為了避免同頁面多個元件傳入相同fields時重複添加元素
    if (!props.hideNo && context.attrs.fields instanceof Array && !context.attrs.fields.find(field => field.key === 'tableNo')) {
      context.attrs.fields.unshift({
        key: 'tableNo',
        label: '序號',
        sortable: false,
        thStyle: 'width:3%',
        thClass: 'text-center align-middle',
        tdClass: 'text-center align-middle',
        mobileShow: true,
        padShow: true,
      });
    }

    // assign新的避免使用TableFieldsPicker時，又被傳入的props.filterIgnoredFields覆蓋
    let filterIgnoredFieldsProp = Object.assign([], props.filterIgnoredFields);
    // 如有序號field則篩選器須去除
    if (context.attrs.fields instanceof Array && context.attrs.fields.find(field => field.key === 'tableNo')) {
      filterIgnoredFieldsProp.push('tableNo');
    }

    const pagination = new Pagination(0, 20, <string[]>props.sortBy, <string>props.sortDirection);

    const state = reactive({
      itemsUndefinedBehaviorComputed: computed(() => {
        return !context.attrs.items && props.itemsUndefinedBehavior === 'loading';
      }),
      emptyText: '查無資料，請修改條件後再重新查詢一次。',
      emptyFilterText: '篩選後無對應資料，請選擇其他篩選條件。',
      // TableFieldsPicker有打勾的選項
      pickedFields: [],
      // 篩選checkbox有的選項
      filterFields: [],
      // 篩選忽略的欄位
      ignoredFields: computed(() =>
        // 從傳進來的items中找出所有key並去除filterFields沒有的欄位，避免篩選時，篩到未設篩選的欄位
        // 用items過濾而非fields，是因為篩選器會篩選資料所有屬性而非只有fields顯示的
        context.attrs.items instanceof Array && context.attrs.items.length > 0
          ? Object.keys(context.attrs.items[0]).filter(key => !state.filterFields.map(field => field.key).includes(key))
          : []
      ),
      pagination: {
        perPageOptions: [10, 20, 30, 50, 100],
        perPage: pagination.perPage,
        currentPage: 1,
        start: computed(() => (state.pagination.currentPage - 1) * state.pagination.perPage + 1),
        end: computed(() => {
          const end = state.pagination.currentPage * state.pagination.perPage;
          return end > state.pagination.totalRows ? (state.pagination.totalRows === 0 ? 1 : state.pagination.totalRows) : end;
        }),
        totalPages: computed(() => Math.ceil(state.pagination.totalRows / state.pagination.perPage)),
        // 篩選時總筆數依據篩選結果，未篩選時則依據前端或後端分頁，前端依items長度，後端依傳進來的totalItems
        totalRows: computed(() =>
          state.filter === null
            ? props.isServerSidePaging
              ? props.totalItems
              : context.attrs.items instanceof Array
                ? context.attrs.items.length
                : 0
            : state.pagination.filterRows
        ),
        // 篩選結果筆數
        filterRows: null,
        gotoPage: undefined,
      },
      showFooter: true,
      // 篩選條件
      filter: null,
      // 篩選條件作用於哪些欄位
      filterOn: [],
      // 輸入篩選條件時，將totalRows替換為filterRows，如無篩選結果則隱藏footer
      filtered: (filteredItems: string | any[]) => {
        state.pagination.filterRows = filteredItems.length;
        state.pagination.currentPage = 1; // filtered時如現在頁數大於過濾完的頁數currentPage會自己跳到1，但避免混淆，統一過濾完都跳到第1頁
        state.showFooter = state.pagination.filterRows !== 0 ? true : false;
      },
    });

    // 使用TableFieldsPicker時，改變顯示的欄位及變更篩選checkbox的選項
    const handlePicked = (pickedFields: any) => {
      state.pickedFields = pickedFields;
      state.filterFields = pickedFields.filter((field: { key: string }) => !filterIgnoredFieldsProp.includes(field.key));
    };

    const handleGotoPage = () => {
      const gotoPage = state.pagination.gotoPage;
      if (!isNaN(gotoPage) && gotoPage > 0 && gotoPage <= state.pagination.totalPages) {
        state.pagination.currentPage = gotoPage;
      }
      state.pagination.gotoPage = undefined;
      emitPagination();
    };

    const handleSortChanged = ctx => {
      if (ctx.sortBy) {
        pagination.sortBy = [ctx.sortBy];
        pagination.sortDirection = ctx.sortDesc ? 'DESC' : 'ASC';
        emitPagination();
      }
    };

    const handlePageChanged = (currentPage?: number) => {
      if (currentPage) state.pagination.currentPage = currentPage;
      if (!_isEqual(pagination.perPage, parseInt(state.pagination.perPage))) {
        pagination.perPage = state.pagination.perPage;
        state.pagination.currentPage = 1;
      }
      emitPagination();
    };

    const handleQuery = (sortBy?: string[], sortDirection?: string) => {
      state.pagination.currentPage = 1;
      pagination.sortBy = sortBy || <string[]>props.sortBy;
      pagination.sortDirection = sortDirection || <string>props.sortDirection;
      emitPagination();
    };

    const emitPagination = (isSearchQueryEqual?) => {
      if (props.isServerSidePaging) {
        if (isSearchQueryEqual !== undefined && !isSearchQueryEqual) state.pagination.currentPage = 1;
        pagination.currentPage = state.pagination.currentPage - 1;
        const param = Object.assign({}, pagination);
        context.emit('changePagination', param);
      }
    };

    const exportExcelProp = ref(props.exportExcel);
    const buildExcelColumns = fields => {
      let arr = [];
      if (!_isArray(fields)) return arr;
      _forEach(fields, f => {
        if (f.key === 'tableNo') return;
        let obj = { name: f.label, key: f.key };
        if (_includes(_keys(f), 'excelTransfer')) obj = _assign(obj, { excelTransfer: f.excelTransfer });
        arr.push(obj);
      });
      return arr;
    };
    const buildExcelRows = (columns, items) => {
      let arr = [];
      if (_size(columns) === 0) return arr;
      _forEach(items, i => {
        let itemArr = [];
        _forEach(columns, c => {
          let value = _cloneDeep(i[c.key]);
          if (_includes(_keys(c), 'transfer')) value = buildTransferData(c, value);
          if (_trim(value) === '') value = _cloneDeep(i[c.key]);
          itemArr.push(value);
        });
        arr.push(itemArr);
      });
      return arr;
    };
    const buildTransferData = (item, value) => {
      const method = item.transfer.method;
      if (method === 'formatDate') value = formatDate(new Date(value), '/');
      else if (method === 'formOptionsFormatter') value = formOptionsFormatter(item.transfer.options, value);
      else if (method === 'formatToString') value = formatToString(value, '/', '-');
      else if (method === 'commaFormatter') value = commaFormatter(value, item.transfer.num);
      return value;
    };
    const onExportExcelClick = () => {
      if (!exportExcelProp.value) return;
      const excel = new ExcelJs.Workbook();
      const sheet = excel.addWorksheet('工作表1');
      const columns = buildExcelColumns(context.attrs.fields);
      const rows = buildExcelRows(columns, context.attrs.items);
      sheet.addTable({ name: 'test', ref: 'A1', columns: columns, rows: rows });
      excel.xlsx.writeBuffer().then(content => {
        const link = document.createElement('a');
        const blobData = new Blob([content], {
          type: 'application/vnd.ms-excel;charset=utf-8;',
        });
        link.download = props.exportExcelFileName + '.' + props.exportExcelFileExtension;
        // link.download = props.exportExcelFileName;
        link.href = URL.createObjectURL(blobData);
        link.click();
      });
    };

    const rowClickForMobileModal = item => {
      if (!props.isShowDetail) {
        return;
      }
      if (deskTopDevice.value) return;
      if (
        padDevice.value &&
        !state.pickedFields
          .filter((field: { key: string }) => field.key !== 'tableNo')
          .find((field: object) => Object.keys(field).includes('padShow'))
      )
        return;
      modalData.value = setModalData(item, context.attrs.fields);
      toggleModal(true);
    };

    const setModalData = (item, fields) => {
      return fields
        .filter((field: { key: string }) => field.key !== 'tableNo')
        .filter((field: { mobileModalHide: boolean }) => !field.mobileModalHide)
        .map((field: { key: string; label: string; formatter: Function }) => ({
          key: field.key,
          label: `${field.label}：`,
          value: field.formatter ? field.formatter(item[field.key], field.key, item).replace(/<br>/g, '\n') : item[field.key],
        }));
    };

    const toggleModal = toggle => {
      modalVisible.value = toggle;
    };

    const handleFieldsChanged = () => {
      state.pickedFields = fieldsPickerComponent.value?.state.pickedFields;
      if (mobileDevice.value) {
        if (
          state.pickedFields
            .filter((field: { key: string }) => field.key !== 'tableNo')
            .find((field: object) => Object.keys(field).includes('mobileShow'))
        )
          state.pickedFields = state.pickedFields.filter((field: { mobileShow: boolean }) => field.mobileShow);
      } else if (padDevice.value) {
        if (
          state.pickedFields
            .filter((field: { key: string }) => field.key !== 'tableNo')
            .find((field: object) => Object.keys(field).includes('padShow'))
        )
          state.pickedFields = state.pickedFields.filter((field: { padShow: boolean }) => field.padShow);
      }
    };

    onMounted(() => {
      // override value: per-page
      // eslint-disable-next-line no-prototype-builtins
      if (context.attrs.hasOwnProperty('per-page') && context.attrs['per-page'] && !isNaN(<any>context.attrs['per-page'])) {
        state.pagination.perPage = parseInt(<string>context.attrs['per-page']);
        pagination.perPage = parseInt(<string>context.attrs['per-page']);
      }
      // override value: empty-text
      // eslint-disable-next-line no-prototype-builtins
      if (context.attrs.hasOwnProperty('empty-text') && context.attrs['empty-text']) {
        state.emptyText = context.attrs['empty-text'];
      }
      // override value: empty-filtered-text
      // eslint-disable-next-line no-prototype-builtins
      if (context.attrs.hasOwnProperty('empty-filtered-text') && context.attrs['empty-filtered-text']) {
        state.emptyFilterText = String(context.attrs['empty-filtered-text']);
      }
      if (props.hasOwnProperty('customPerPageOptions') && _size(props.customPerPageOptions) !== 0) {
        state.pagination.perPageOptions = _cloneDeep(props.customPerPageOptions);
      }
      handleFieldsChanged();
    });

    watch(
      context.attrs,
      () => {
        const keys = _keys(context.attrs);
        if (!_includes(keys, 'isSearchQueryEqual')) return;
        if (!context.attrs.isSearchQueryEqual) emitPagination(context.attrs.isSearchQueryEqual);
      },
      { immediate: true, deep: true }
    );

    watch(padDevice, handleFieldsChanged);

    return {
      state,
      exportExcelProp,
      pagination,
      modalVisible,
      modalData,
      handlePicked,
      handleGotoPage,
      handleSortChanged,
      handlePageChanged,
      handleQuery,
      emitPagination,
      onExportExcelClick,
      rowClickForMobileModal,
      toggleModal,
      fieldsPickerComponent,
      mobileDevice,
    };
  },
};
</script>

<style lang="scss" scoped>
@media screen and (max-width: 768px) {
  .modal-row {
    .modal-label,
    .modal-value {
      font-size: 1.25rem;
    }
    .modal-label {
      font-weight: bold;
    }
  }
}
@media screen and (min-width: 1200px) and (max-width: 1400px) {
  .per-page-select {
    padding-right: 0px;
  }
}
</style>
