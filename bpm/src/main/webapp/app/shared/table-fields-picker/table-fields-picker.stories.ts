// step 1: 引入 TableFieldsPicker 方便簡化使用
import TableFieldsPicker from './table-fields-picker.vue';

export default {
  title: 'Component/TableFieldsPicker',
  component: TableFieldsPicker,
  argTypes: {
    fields: {
      name: 'label',
      defaultValue: 'name',
      description: '要用來顯示的屬性名稱',
    }, // TODO
    onPicked: { action: 'picked' },
  },
};

const Template = (args, { argTypes }) => ({
  components: { TableFieldsPicker },
  props: Object.keys(argTypes),
  template: '<table-fields-picker :fields="fields" @picked="onPicked" />',
});

export const Main = Template.bind({});
Main.args = {
  // 原始欄位，base on b-table 的 field 的屬性，擴充 visible 屬性
  // visible 的值為 true or false, 控制 欄位是否顯示或隱藏
  // 不設定，則套用預設值。預設值為 true (顯示)
  // step 2(optional): 設定欄位初始顯示與否
  fields: [
    { key: 'eventId', label: '事件編號', sortable: true, visible: false },
    { key: 'eventType', label: '事件類型', sortable: false },
    { key: 'eventDesc', label: '事件描述', sortable: true, visible: true },
    { key: 'eventRate', label: '事件比重(0~100)', sortable: true, visible: false },
    { key: 'eventRiskScore', label: '風險事件評分(權重*配分)', sortable: true },
    { key: 'countRiskScoreFrequency', label: '計算週期' },
    { key: 'creator', label: '建立者', sortable: true },
    { key: 'modifier', label: '修改者' },
  ],
};
