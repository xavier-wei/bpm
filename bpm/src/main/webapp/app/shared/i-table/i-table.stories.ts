// // step 1: 引入 ITable 方便簡化使用
// import { withKnobs, text, object, number } from '@storybook/addon-knobs';
// import ITable from './i-table.vue';
// import { ITableStoriesData } from './i-table.stories.data';

// export default {
//   title: 'Component/ITable',
//   parameters: {
//     componentSubtitle: '以 b-table 為基底，擴充相關操作元件：欄位顯示與隱藏、欄位顯示的排序、分頁、分頁跳頁、顯示分頁資訊',
//     docs: {
//       description: {
//         component:
//           'i-table 與原生 的 b-table 用法一樣，相關文件可參考 [b-table](https://bootstrap-vue.org/docs/components/table#comp-ref-b-table)',
//       },
//     },
//   },
//   component: ITable,
//   decorators: [withKnobs],
// };

// export const Playground = () => ({
//   components: { ITable },
//   props: {
//     title: {
//       // FIXME 不知道為什麼沒有作用啊
//       default: text('title', ITableStoriesData.title),
//     },
//     items: {
//       default: object('items', ITableStoriesData.items),
//     },
//     fields: {
//       default: object('fields', ITableStoriesData.fields),
//     },
//     perPage: {
//       default: number('perPage', 5),
//     },
//   },
//   template: '<i-table :title=title :items=items :per-page=perPage :fields=fields />',
// });
// Playground.args = {
//   title: ITableStoriesData.title,
//   items: ITableStoriesData.items,
//   fields: ITableStoriesData.fields,
//   perPage: ITableStoriesData.perPage,
// };

// export const UndefinedItemsShowsNothing = (args, { argTypes }) => ({
//   components: { ITable },
//   props: Object.keys(argTypes),
//   template: '<i-table :items=items :title=title />',
// });
// UndefinedItemsShowsNothing.args = {
//   title: ITableStoriesData.title,
//   items: undefined,
// };

// export const UndefinedItemsShowsSpinner = (args, { argTypes }) => ({
//   components: { ITable },
//   props: Object.keys(argTypes),
//   template: '<i-table :items=items :title=title :items-undefined-behavior=itemsUndefinedBehavior />',
// });
// UndefinedItemsShowsSpinner.args = {
//   title: ITableStoriesData.title,
//   items: undefined,
//   itemsUndefinedBehavior: 'loading',
// };

// export const EmptyItems = (args, { argTypes }) => ({
//   components: { ITable },
//   props: Object.keys(argTypes),
//   template: '<i-table :items="items" :title="title" :empty-text="emptyText"/>',
// });
// EmptyItems.args = {
//   title: ITableStoriesData.title,
//   emptyText: undefined,
//   items: [],
// };

// export const EmptyItemsWithText = (args, { argTypes }) => ({
//   components: { ITable },
//   props: Object.keys(argTypes),
//   template: '<i-table :items=items :title=title :empty-text=emptyText />',
// });
// EmptyItemsWithText.args = {
//   title: ITableStoriesData.title,
//   emptyText: '啊就沒資料啊，懷疑嗎?! 🤨',
//   items: [],
// };
