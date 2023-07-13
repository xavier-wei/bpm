<template>
  <b-card no-body :class="{ 'border-0': border0 }" v-if="$attrs.items !== undefined || state.itemsUndefinedBehaviorComputed">
    <slot name="table-top" />

    <b-card-header :class="{ 'd-none': true, 'd-md-block': !hideCardHeader }">
      <h5 class="my-0">
        <font-awesome-icon icon="list"></font-awesome-icon>
        {{ title }}
        <table-fields-picker
          v-show="$attrs.items && $attrs.items.length > 0 && showFilterIcon"
          v-bind="$attrs"
          @picked="handlePicked"
          class="float-right"
        />
      </h5>
    </b-card-header>

    <b-card-body class="m-0 p-0">
      <div id="searchTips">
        <p id="searchCondition"></p>
        <p class="pt-3">
          [基本查詢]以表格顯示全部結果、[分區查詢]可依地圖選擇資料、[樹狀顯示]依類別區分資料、[趨勢圖表]顯示價格變化的趨勢
        </p>
        <div class="text-success" v-show="!mobileDevice">請以下列四個按鈕切換基本查詢 、分區查詢 、樹狀顯示及趨勢圖表之頁面</div>
        <div id="badgeGroup">
          <button id="basicSearchButton" @keyup.left="tabKeyupLeft('trendSearchButton')" @keyup.right="tabKeyupRight('areaSearchButton')" class="mt-1 ml-2 btn btn-info">
            <font-awesome-icon :icon="['fas', 'bars']" fixed-width /> 基本查詢
          </button>
          <button id="areaSearchButton" @keyup.left="tabKeyupLeft('basicSearchButton')" @keyup.right="tabKeyupRight('treeSearchButton')" class="mt-1 ml-2 btn btn-info">
            <font-awesome-icon :icon="['fas', 'map-marker-alt']" fixed-width /> 分區查詢
          </button>
          <button id="treeSearchButton" @keyup.left="tabKeyupLeft('areaSearchButton')" @keyup.right="tabKeyupRight('trendSearchButton')" class="mt-1 ml-2 btn btn-info">
            <font-awesome-icon :icon="['fas','sitemap']" fixed-width /> 樹狀顯示
          </button>
          <button id="trendSearchButton" @keyup.left="tabKeyupLeft('treeSearchButton')" @keyup.right="tabKeyupRight('basicSearchButton')" class="mt-1 ml-2 btn btn-info">
            <font-awesome-icon :icon="['fas', 'clock']" fixed-width /> 趨勢圖表
          </button>
        </div>

        <p class="">提醒：</p>
        <p class="">1. 本會工程價格資料庫係依據政府採購法第11條建立，資料庫內容包括預算價、決標價兩種價格，分別提供機關作為編列設計預算、核定底價之參考。</p>
        <p class="">2. 機關參考使用時應確實依政府採購法第46條規定，考量工程規模、性質、規範要求、施工地點及工期等因素，配合工程專業判斷，予以調整，不可僅參考工程價格資料庫之價格即逕以編列設計預算或訂定底價。</p>
        <p class="">3. 本資料庫不提供依物價指數調整後的價格資訊，使用者可依需求自行</p>
        <p class="">4. 樣本數較少時，使用者應注意其平均價格與標準差兩者數據之差異，不宜逕行參考引用。</p>
      </div>

      <!-- 基本查詢 -->
      <div id="basicResult" class="resultDiv">
        <b-table
          hover
          show-empty
          responsive
          class="m-0"
          v-bind="$attrs"
          v-on="$listeners"
          :bordered="bordered"
          :fields="state.pickedFields"
          :per-page="state.pagination.perPage"
          :current-page="isServerSidePaging ? 1 : state.pagination.currentPage"
          :empty-text="state.emptyText"
          :empty-filtered-text="state.emptyFilterText"
          :no-sort-reset="isServerSidePaging"
          :no-local-sorting="isServerSidePaging"
          :filter="state.filter"
          :filter-included-fields="state.filterOn"
          :filter-ignored-fields="state.ignoredFields"
          @filtered="state.filtered"
          @sort-changed="handleSortChanged"
          :style="{fontSize: '16px'}"
        >
          <template v-for="(_, slot) of $scopedSlots" v-slot:[slot]="scope">
            <slot :name="slot" v-bind="scope" />
          </template>
          <template v-slot:head(err_price)="data">
            <div>標準差 <img id="popoverId0" src="../../components/mrp/image/exclaim_icon.png" width="20" height="20" style="cursor:pointer;"></img></div>
            <b-popover target="popoverId0" triggers="hover">
              標準差說明為變異數開算術平方根，反映組內個體間的離散程度。如近似於常態分布的機率分布，則約有近7成的價格分布在平均價格±標準差之內的範圍。
            </b-popover>
          </template>            
          <template #empty>
            <div class="text-center m-3" v-if="state.itemsUndefinedBehaviorComputed">
              <b-spinner></b-spinner>
              <p>查詢中，請稍候...</p>
            </div>
          </template>
        </b-table>
        <div id="basicFooter" class="card-footer" v-if="$attrs.items && $attrs.items.length > 0 && state.showFooter">
          <b-row align-h="around" align-v="center">
            <b-col lg="3" class="pl-0 pr-0">
              <b-input-group prepend="每頁" append="筆" class="mr-md-3" :size="size">
                <b-form-select
                  v-model="state.pagination.perPage"
                  :options="state.pagination.perPageOptions"
                  @input="handlePageChanged"
                ></b-form-select>
              </b-input-group>
            </b-col>
            <b-col lg="4" class="pt-1 pt-lg-0">
              <div>
                <b-pagination
                  class="my-0"
                  align="fill"
                  :size="size"
                  v-model="state.pagination.currentPage"
                  :total-rows="state.pagination.totalRows"
                  :per-page="state.pagination.perPage"
                  @input="handlePageChanged"
                />
              </div>
            </b-col>
            <b-col md="8" xl="2" order="4" order-md="4" order-xl="3" class="pt-1 pt-xl-0 text-center">
              第 {{ state.pagination.start }} 到 {{ state.pagination.end }} , 共
              {{ state.pagination.totalRows ? state.pagination.totalRows : '沒給totalItems' }} 筆 , 頁次 {{ state.pagination.currentPage }}/{{
                state.pagination.totalPages
              }}
            </b-col>
            <b-col md="4" xl="3" order="3" order-md="3" order-xl="4" class="pt-1 pt-xl-0">
              <b-input-group prepend="前往頁數" :size="size">
                <b-form-input v-model="state.pagination.gotoPage" @keydown.enter.prevent="handleGotoPage" />
                <b-input-group-append>
                  <b-button variant="outline-success" @click="handleGotoPage">GO</b-button>
                </b-input-group-append>
              </b-input-group>
            </b-col>
          </b-row>
        </div>
        <div id="basicFooterButtonGroup">
          <button id="basicCollectionDownloadButton" class="mt-1 ml-1 btn btn-info">下載</button>
          <button id="basicCollectionButton" type="button" v-if="footerQueryButton" class="mt-1 ml-1 btn btn-info">
            查詢集合
            <span id="basicCollectionNumberIcon" class="badge badge-light">0</span>
          </button>
        </div>
      </div>

      <!-- 分區查詢 -->
      <div id="areaResult" class="resultDiv table-responsive hidden">
        <div class="text-right">
          <b-button class="mr-4 mb-2" @click="toggleBtn.isMapOpen = !toggleBtn.isMapOpen" :style="{ postion: 'absolute', zIndex: 1 }">
            <font-awesome-icon v-if="!toggleBtn.isMapOpen" :icon="['fas', 'expand-arrows-alt']" />
            <font-awesome-icon v-if="toggleBtn.isMapOpen" :icon="['fas', 'compress-arrows-alt']" />
          </b-button>
        </div>
        <div id="mapContainer" v-if="toggleBtn.isMapOpen">
          <map name="FPMap1">
            <area
              title="臺北市"
              shape="polygon"
              coords="314, 28, 338, 66"
              href="javascript:void(0);"
            >
              <div 
                id="area_1"               
                data-name="臺北市"
                title="臺北市"
                class="area_1 pin pin bluePin">
              </div>
            </area>
            <area
              title="桃園市"
              shape="polygon"
              coords="241, 69, 259, 49, 285, 42, 300, 60, 289, 75, 294, 96, 301, 97, 311, 116, 301, 136, 288, 121, 284, 105, 272, 96, 265, 85"
              href="javascript:void(0);"
              >
              <div 
                id="area_2"               
                data-name="桃園市"
                title="桃園市"
                class="area_2 pin pin bluePin">
              </div>
            </area>
            <area
              title="新竹市"
              shape="polygon"
              coords="298, 140, 284, 124, 281, 111, 268, 98, 259, 87, 243, 74, 230, 74, 229, 82, 246, 91, 251, 105, 239, 114, 255, 134, 257, 146, 272, 147, 287, 162, 295, 150"
              href="javascript:void(0);"
              >
              <div 
                id="area_3"               
                data-name="新竹市"
                title="新竹市"
                class="area_3 pin pin bluePin">
              </div>
            </area>
            <area
              title="苗栗縣"
              shape="polygon"
              coords="221, 111, 239, 115, 254, 135, 255, 145, 263, 154, 271, 152, 285, 162, 251, 184, 232, 178, 226, 187,210, 180, 197, 176, 184, 157, 196, 132, 207, 124, 216, 120"
              href="javascript:void(0);"
              >
              <div 
                id="area_4"               
                data-name="苗栗縣"
                title="苗栗縣"
                class="area_4 pin pin bluePin">
              </div>
            </area>
            <area
              title="臺中市"
              shape="polygon"
              coords="183, 163, 198, 184, 225, 194, 235, 193, 238, 187, 256, 193, 292, 169, 312, 179, 295, 199, 270, 201 , 246, 216, 236, 216, 227, 222, 214, 232, 193, 234, 182, 218, 163, 200, 169, 188, 175, 174"
              href="javascript:void(0);"
              >
              <div 
                id="area_5"               
                data-name="臺中市"
                title="臺中市"
                class="area_5 pin pin bluePin">
              </div>
            </area>
            <area
              title="彰化縣"
              shape="polygon"
              coords="161, 204, 173, 218, 187, 232, 187, 244, 181, 248, 181, 259, 187, 272, 130, 263, 125, 256, 150, 224"
              href="javascript:void(0);"
              >
              <div 
                id="area_6"               
                data-name="彰化縣"
                title="彰化縣"
                class="area_6 pin pin bluePin">
              </div>
            </area>
            <area
              title="南投縣"
              shape="polygon"
              coords="188, 241, 213, 239, 233, 224, 246, 221, 271, 205, 291, 203, 272, 302, 264, 303, 248, 328, 237, 330, 221, 323, 220, 310, 203, 300, 186, 300, 187, 276, 193, 272, 185, 259, 183, 251"
              href="javascript:void(0);"
              >
              <div 
                id="area_7"               
                data-name="南投縣"
                title="南投縣"
                class="area_7 pin pin bluePin">
              </div>
            </area>
            <area
              title="雲林縣"
              shape="polygon"
              coords="117, 272, 133, 268, 187, 277, 188, 302, 205, 305, 203, 312, 182, 309, 160, 298, 143, 305, 132, 316, 119, 316, 109, 321, 105, 298"
              href="javascript:void(0);"
              >
              <div 
                id="area_8"               
                data-name="雲林縣"
                title="雲林縣"
                class="area_8 pin pin bluePin">
              </div>
            </area>
            <area
              title="嘉義市"
              shape="circle"
              coords="161, 330, 8"
              href="javascript:void(0);"
              >
              <div 
                id="area_9"               
                data-name="嘉義市"
                title="嘉義市"
                class="area_9 pin pin bluePin">
              </div>
            </area>
            <area
              title="臺南市"
              shape="polygon"
              coords="106, 359, 119, 361, 154, 345, 173, 355, 176, 373, 187, 379, 171, 403, 155, 415, 140, 430, 122, 425, 125, 400, 119, 392, 104, 395, 103, 379"
              href="javascript:void(0);"
              >
              <div 
                id="area_10"               
                data-name="臺南市"
                title="臺南市"
                class="area_10 pin pin bluePin">
              </div>
            </area>
            <area
              title="高雄市"
              shape="polygon"
              coords="116, 430, 134, 438, 152, 425, 171, 411, 190, 383, 196, 373, 195, 366, 203, 363, 235, 342, 243, 335, 255,334, 251, 351, 240, 359, 236, 378, 228, 387, 227, 409, 219, 416, 218, 438, 211, 441, 192, 432, 176, 441,156, 442, 150, 502, 144, 499, 148, 488, 137, 476, 137, 457, 121, 458, 116, 438"
              href="javascript:void(0);"
              >
              <div 
                id="area_11"               
                data-name="高雄市"
                title="高雄市"
                class="area_11 pin pin bluePin">
              </div>
            </area>
            <area
              title="屏東縣"
              shape="polygon"
              coords="161, 446, 174, 445, 193, 437, 210, 446, 221, 442, 226, 455, 202, 484, 203,522, 209, 527, 211, 541, 224, 549, 225, 582, 217, 593, 217, 604, 211, 596, 200, 600, 195, 583, 198, 575, 195, 551, 187, 532, 175, 516, 162, 510, 164, 507, 154, 505, 156, 465"
              href="javascript:void(0);"
              >
              <div 
                id="area_12"               
                data-name="屏東縣"
                title="屏東縣"
                class="area_12 pin pin bluePin">
              </div>
            </area>
            <area
              title="宜蘭縣"
              shape="polygon"
              coords="290, 161, 312, 125, 369, 83, 368, 105, 375, 132, 370, 152, 360, 162, 357, 180, 339, 171, 330, 179, 307, 167, 302, 170"
              href="javascript:void(0);"
              >
              <div 
                id="area_13"               
                data-name="宜蘭縣"
                title="宜蘭縣"
                class="area_13 pin pin bluePin">
              </div>
            </area>
            <area
              title="花蓮縣"
              shape="polygon"
              coords="320, 180, 337, 185, 345, 179, 364, 186, 349, 201, 342, 220, 342, 235, 321, 309, 321, 326, 316, 340, 304, 336, 282, 391, 277, 384, 255, 364, 246, 352, 254, 334, 261, 317, 277, 305, 282, 271, 287, 234, 294, 204"
              href="javascript:void(0);"
              >
              <div 
                id="area_14"               
                data-name="花蓮縣"
                title="花蓮縣"
                class="area_14 pin pin bluePin">
              </div>
            </area>
            <area
              title="臺東縣"
              shape="polygon"
              coords="245, 360, 237, 363, 234, 377, 225, 390, 224, 408, 217, 420, 217, 436, 222, 458, 203, 477, 197, 489,199, 509, 199, 520, 207, 525, 207, 538, 216, 546, 225, 511, 238, 481, 238, 471, 267, 452, 269, 441, 283, 421, 300, 391, 307, 370, 317, 348, 308, 341, 283, 399"
              href="javascript:void(0);"
              >
              <div 
                id="area_15"               
                data-name="臺東縣"
                title="臺東縣"
                class="area_15 pin pin bluePin">
              </div>
            </area>
            <area
              title="金門縣"
              shape="rect"
              coords="10, 135, 114, 207"
              href="javascript:void(0);"
              >
              <div 
                id="area_16"               
                data-name="金門縣"
                title="金門縣"
                class="area_16 pin pin bluePin">
              </div>
            </area>
            <area
              title="澎湖縣"
              shape="rect"
              coords="5, 278, 82, 325"
              href="javascript:void(0);"
              >
              <div 
                id="area_17"               
                data-name="澎湖縣"
                title="澎湖縣"
                class="area_17 pin pin bluePin">
              </div>
            </area>
            <area
              title="新北市"
              shape="rect"
              coords="311, 72, 339, 72, 339, 53, 363, 55, 363, 41, 378, 41, 388, 61, 311, 115, 303, 90, 297, 91, 292, 74, 301, 58, 290, 40, 303, 36, 305, 20, 322, 11, 335, 14, 342, 26, 310, 25"
              href="javascript:void(0);"
              >
              <div 
                id="area_19"               
                data-name="新北市"
                title="新北市"
                class="area_19 pin pin bluePin">
              </div>
            </area>      
			      <area
              title="基隆市"
              shape="circle"
              coords="347, 41, 11"
              href="javascript:void(0);"
              >
              <div 
                id="area_20"               
                data-name="基隆市"
                title="基隆市"
                class="area_20 pin pin bluePin">
              </div>
            </area>
            <area
              title="連江縣"
              shape="rect"
              coords="11, 10, 115, 80"
              href="javascript:void(0);"
              >
              <div 
                id="area_21"               
                data-name="連江縣"
                title="連江縣"
                class="area_21 pin pin bluePin">
              </div>
            </area>
            <area
              title="新竹縣"
              shape="circle"
              coords="235, 99, 12"
              href="javascript:void(0);"
              >
              <div 
                id="area_22"               
                data-name="新竹縣"
                title="新竹縣"
                class="area_22 pin pin bluePin">
              </div>
            </area>
            <area
              title="嘉義縣"
              shape="polygon"
              coords="233, 336, 221, 326, 221, 314, 212, 310, 204, 317, 185, 316, 160, 303, 146, 308, 136, 320, 124, 322, 121, 326, 109, 326, 103, 352, 114, 353, 128, 347, 149, 338, 159, 341, 174, 353, 176, 368, 185, 372, 186, 359, 197, 358, 208, 350"
              href="javascript:void(0);"
              >
              <div 
                id="area_23"               
                data-name="嘉義縣"
                title="嘉義縣"
                class="area_23 pin pin bluePin">
              </div>
            </area>
          </map>
          <img hidefocus="true" src="../../components/mrp/image/Taiwan.png" width="430" usemap="#FPMap1" />
        </div>
        <table id="areaResultTable" :style="{fontSize: '16px'}">
          <tr>
            <td>
              <b-table
                hover
                show-empty
                responsive
                class="m-0"
                v-bind="$attrs"
                v-on="$listeners"
                :bordered="bordered"
                :fields="state.pickedFields"
                :per-page="state.pagination.perPage"
                :current-page="isServerSidePaging ? 1 : state.pagination.currentPage"
                :empty-text="state.emptyText"
                :empty-filtered-text="state.emptyFilterText"
                :no-sort-reset="isServerSidePaging"
                :no-local-sorting="isServerSidePaging"
                :filter="state.filter"
                :filter-included-fields="state.filterOn"
                :filter-ignored-fields="state.ignoredFields"
                @filtered="state.filtered"
                @sort-changed="handleSortChanged"
                :style="{fontSize: '16px'}"
              >
                <template v-for="(_, slot) of $scopedSlots" v-slot:[slot]="scope">
                  <slot :name="slot" v-bind="scope" />
                </template>
                <template v-slot:head(err_price)="data">
                  <div>標準差 <img id="popoverId1" src="../../components/mrp/image/exclaim_icon.png" width="20" height="20" style="cursor:pointer;"></img></div>
                  <b-popover target="popoverId1" triggers="hover">
                    標準差說明為變異數開算術平方根，反映組內個體間的離散程度。如近似於常態分布的機率分布，則約有近7成的價格分布在平均價格±標準差之內的範圍。
                  </b-popover>
                </template>                
                <template #empty>
                  <div class="text-center m-3" v-if="state.itemsUndefinedBehaviorComputed">
                    <b-spinner></b-spinner>
                    <p>查詢中，請稍候...</p>
                  </div>
                </template>
              </b-table>
              <div id="areaFooter" class="card-footer" v-if="$attrs.items && $attrs.items.length > 0 && state.showFooter">
                <b-row align-h="around" align-v="center">
                  <b-col lg="3" class="pl-0 pr-0">
                    <b-input-group prepend="每頁" append="筆" class="mr-md-3" :size="size">
                      <b-form-select
                        v-model="state.pagination.perPage"
                        :options="state.pagination.perPageOptions"
                        @input="handlePageChanged"
                      ></b-form-select>
                    </b-input-group>
                  </b-col>
                  <b-col lg="4" class="pt-1 pt-lg-0">
                    <div>
                      <b-pagination
                        class="my-0"
                        align="fill"
                        :size="size"
                        v-model="state.pagination.currentPage"
                        :total-rows="state.pagination.totalRows"
                        :per-page="state.pagination.perPage"
                        @input="handlePageChanged"
                      />
                    </div>
                  </b-col>
                  <b-col md="8" xl="2" order="4" order-md="4" order-xl="3" class="pt-1 pt-xl-0 text-center">
                    第 {{ state.pagination.start }} 到 {{ state.pagination.end }} , 共
                    {{ state.pagination.totalRows ? state.pagination.totalRows : '沒給totalItems' }} 筆 , 頁次 {{ state.pagination.currentPage }}/{{
                      state.pagination.totalPages
                    }}
                  </b-col>
                  <b-col md="4" xl="3" order="3" order-md="3" order-xl="4" class="pt-1 pt-xl-0">
                    <b-input-group prepend="前往頁數" :size="size">
                      <b-form-input v-model="state.pagination.gotoPage" @keydown.enter.prevent="handleGotoPage" />
                      <b-input-group-append>
                        <b-button variant="outline-success" @click="handleGotoPage">GO</b-button>
                      </b-input-group-append>
                    </b-input-group>
                  </b-col>
                </b-row>
              </div>
              <div id="mapFooterButtonGroup">
                <button id="mapCollectionDownloadButton" class="mt-1 ml-1 btn btn-info">下載</button>
                <button id="mapCollectionButton" type="button" v-if="footerQueryButton" class="mt-1 ml-1 btn btn-info">
                  查詢集合
                  <span id="areaCollectionNumberIcon" class="badge badge-light">0</span>
                </button>
              </div>
            </td>
          </tr>
        </table>
      </div>

      <!-- 樹狀顯示 -->
      <div id="treeResult" class="resultDiv hidden table-responsive d-flex" :style="{minHeight: '90px'}">
        <div id="treeblock" class="text-center m-3 w-100 hidden" :style="{position: 'absolute', zIndex: '1'}">
          <b-spinner></b-spinner>
          <p>查詢中，請稍候...</p>
        </div>        
        <div :style="{position: 'absolute', right: '0', marginTop: '-42px'}">
          <b-button class=" mr-4"  @click="toggleBtn.isTreeOpen = !toggleBtn.isTreeOpen">
            <font-awesome-icon v-if="!toggleBtn.isTreeOpen" :icon="['fas', 'expand-arrows-alt']" />
            <font-awesome-icon v-if="toggleBtn.isTreeOpen" :icon="['fas', 'compress-arrows-alt']" />
          </b-button>
        </div>
        <div id="treeContainer" :class="toggleBtn.isTreeOpen ? 'mw-50' : 'w-100'" :style="{fontSize: '16px'}"></div>
        <div :class="toggleBtn.isTreeOpen ? 'w-75' : 'w-100'">
          <table id="treeResultTable" :class="toggleBtn.isTreeOpen ? '' : 'hidden'"  :style="{fontSize: '16px'}">
            <tr>
              <td id="treeResultTable_2" class="hidden">
                <b-table
                  hover
                  show-empty
                  responsive
                  class="m-0"
                  v-bind="$attrs"
                  v-on="$listeners"
                  :bordered="bordered"
                  :fields="state.pickedFields"
                  :per-page="state.pagination.perPage"
                  :current-page="isServerSidePaging ? 1 : state.pagination.currentPage"
                  :empty-text="state.emptyText"
                  :empty-filtered-text="state.emptyFilterText"
                  :no-sort-reset="isServerSidePaging"
                  :no-local-sorting="isServerSidePaging"
                  :filter="state.filter"
                  :filter-included-fields="state.filterOn"
                  :filter-ignored-fields="state.ignoredFields"
                  @filtered="state.filtered"
                  @sort-changed="handleSortChanged"
                >
                  <template v-slot:head(err_price)="data">
                    <div>標準差 <img id="popoverId2" src="../../components/mrp/image/exclaim_icon.png" width="20" height="20" style="cursor:pointer;"></img></div>
                    <b-popover target="popoverId2" triggers="hover">
                      標準差說明為變異數開算術平方根，反映組內個體間的離散程度。如近似於常態分布的機率分布，則約有近7成的價格分布在平均價格±標準差之內的範圍。
                    </b-popover>
                  </template>                  
                  <template v-for="(_, slot) of $scopedSlots" v-slot:[slot]="scope">
                    <slot :name="slot" v-bind="scope" />
                  </template>
                </b-table>
                <div id="areaFooter" class="card-footer" v-if="$attrs.items && $attrs.items.length > 0 && state.showFooter">
                  <b-row align-h="around" align-v="center">
                    <b-col lg="3" class="pl-0 pr-0">
                      <b-input-group prepend="每頁" append="筆" class="mr-md-3" :size="size">
                        <b-form-select
                          v-model="state.pagination.perPage"
                          :options="state.pagination.perPageOptions"
                          @input="handlePageChanged"
                        ></b-form-select>
                      </b-input-group>
                    </b-col>
                    <b-col lg="4" class="pt-1 pt-lg-0">
                      <div>
                        <b-pagination
                          class="my-0"
                          align="fill"
                          :size="size"
                          v-model="state.pagination.currentPage"
                          :total-rows="state.pagination.totalRows"
                          :per-page="state.pagination.perPage"
                          @input="handlePageChanged"
                        />
                      </div>
                    </b-col>
                    <b-col md="8" xl="2" order="4" order-md="4" order-xl="3" class="pt-1 pt-xl-0 text-center">
                      第 {{ state.pagination.start }} 到 {{ state.pagination.end }} , 共
                      {{ state.pagination.totalRows ? state.pagination.totalRows : '沒給totalItems' }} 筆 , 頁次 {{ state.pagination.currentPage }}/{{
                        state.pagination.totalPages
                      }}
                    </b-col>
                    <b-col md="4" xl="3" order="3" order-md="3" order-xl="4" class="pt-1 pt-xl-0">
                      <b-input-group prepend="前往頁數" :size="size">
                        <b-form-input v-model="state.pagination.gotoPage" @keydown.enter.prevent="handleGotoPage" />
                        <b-input-group-append>
                          <b-button variant="outline-success" @click="handleGotoPage">GO</b-button>
                        </b-input-group-append>
                      </b-input-group>
                    </b-col>
                  </b-row>
                </div>
                <div id="treeFooterButtonGroup">
                  <button id="treeCollectionDownloadButton" class="mt-1 ml-1 btn btn-info">下載</button>
                  <button id="treeCollectionButton" type="button" v-if="footerQueryButton" class="mt-1 ml-1 btn btn-info">
                    查詢集合
                    <span id="treeCollectionNumberIcon" class="badge badge-light">0</span>
                  </button>
                </div>
              </td>
            </tr>
          </table>
        </div>
      </div>

      <!-- 趨勢圖表 -->
      <div id="trendResult" class="resultDiv hidden">
        <div id="trendblock" class="text-center m-3 hidden">
          <b-spinner></b-spinner>
          <p>查詢中，請稍候...</p>
        </div>
        <figure class="highcharts-figure">
          <div id="highChartContainer"></div>
        </figure>
      </div>
    </b-card-body>
  </b-card>
</template>

<script lang="ts">
import i18n from '@/shared/i18n';
import TableFieldsPicker from '@/shared/table-fields-picker/table-fields-picker.vue';
import { computed, onMounted, reactive, ref } from '@vue/composition-api';
import axios from 'axios';
import { useGetters } from '@u3u/vue-hooks';
import {
  BButton,
  BCard,
  BCardBody,
  BCardHeader,
  BCol,
  BFormInput,
  BFormSelect,
  BInputGroup,
  BInputGroupAppend,
  BPagination,
  BRow,
  BTable,
  BSpinner,
  BPopover,
} from 'bootstrap-vue';
import { Pagination } from '../model/pagination.model';

export default {
  name: 'mrp-table',
  inheritAttrs: false,
  components: {
    TableFieldsPicker,
    BCol,
    BPagination,
    BRow,
    BButton,
    BCard,
    BCardBody,
    BCardHeader,
    BFormInput,
    BFormSelect,
    BInputGroup,
    BInputGroupAppend,
    BTable,
    BSpinner,
    BPopover,
  },
  props: {
    // 頁面名稱
    page: {
      type: String,
      required: false,
      default: '',
    },
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
    showFilterIcon: {
      type: Boolean,
      required: false,
      default: false,
    },
    hideCardHeader: {
      type: Boolean,
      required: false,
      default: false,
    },
    footerQueryButton: {
      type: Boolean,
      required: false,
      default: false,
    },

    // hideTable: {
    //   type: Boolean,
    //   required: false,
    //   default: false,
    // },
    showButtonGroup: {
      type: Boolean,
      required: false,
      default: false,
    },
    hasSdPopupModal: {
      type: Boolean,
      required: false,
      default: false,
    },
  },
  setup(props, context) {
    const mobileDevice = computed(() => useGetters(['mobileDevice']).mobileDevice.value);
    const toggleBtn = reactive({
      isMapOpen: true,
      isTreeOpen: true,
    });
    // 在table最左側添加流水序號
    // 第三個判斷是否已經有tableNo，是為了避免同頁面多個元件傳入相同fields時重複添加元素
    if (!props.hideNo && context.attrs.fields instanceof Array && !context.attrs.fields.find(field => field.key === 'tableNo')) {
      context.attrs.fields.unshift({
        // 序號
        key: 'tableNo',
        label: i18n.t('label.no'),
        sortable: false,
        thClass: 'text-center align-middle',
        tdClass: 'text-center align-middle',
      });
    }

    // assign新的避免使用TableFieldsPicker時，又被傳入的props.filterIgnoredFields覆蓋
    let filterIgnoredFieldsProp = Object.assign([], props.filterIgnoredFields);
    // 如有序號field則篩選器須去除
    if (context.attrs.fields instanceof Array && context.attrs.fields.find(field => field.key === 'tableNo')) {
      filterIgnoredFieldsProp.push('tableNo');
    }

    const pagination = new Pagination(0, 20, [], 'ASC');
    const state = reactive({
      itemsUndefinedBehaviorComputed: computed(() => {
        return context.attrs.items === undefined && props.itemsUndefinedBehavior === 'loading';
      }),
      //      emptyText: props.isServerSidePaging ? '查無資料，請修改條件後再重新查詢一次。' : '查無資料',
      emptyText: '查無資料',
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
        perPageOptions: [20, 30, 40, 50, 100],
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
        // state.pagination.currentPage = 1; filtered時currentPage會自己跳到1
        state.showFooter = state.pagination.filterRows !== 0 ? true : false;
      },
      initToggleBtn: () => {
        toggleBtn.isMapOpen = true;
        toggleBtn.isTreeOpen = true;
      },
    });

    const tabKeyupLeft = (targetId) => {
      document.getElementById(targetId).click();
      document.getElementById(targetId).focus();
    }; 

    const tabKeyupRight = (targetId) => {
      document.getElementById(targetId).click();
      document.getElementById(targetId).focus();
    }; 

    // 使用TableFieldsPicker時，改變顯示的欄位及變更篩選checkbox的選項

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

    const handlePageChanged = () => {
      pagination.perPage = state.pagination.perPage;
      emitPagination();
    };

    const emitPagination = () => {
      if (props.isServerSidePaging) {
        pagination.currentPage = state.pagination.currentPage - 1;
        const param = Object.assign({}, pagination);
        context.emit('changePagination', param);
      }
    };

    onMounted(() => {
      if (context.attrs.hasOwnProperty('per-page') && context.attrs['per-page'] && !isNaN(<any>context.attrs['per-page'])) {
        state.pagination.perPage = context.attrs['per-page'];
      }
      if (context.attrs.hasOwnProperty('empty-text') && context.attrs['empty-text']) {
        state.emptyText = context.attrs['empty-text'];
      }
      if (context.attrs.hasOwnProperty('empty-filtered-text') && context.attrs['empty-filtered-text']) {
        state.emptyFilterText = String(context.attrs['empty-filtered-text']);
      }
    });

    function download(name) {
      axios
        .get('/service/getTheFile', {
          params: { fileName: name },
          responseType: 'blob',
        })
        .then(response => {
          console.log('downloadPDF.reponse : ', response);
          const href = URL.createObjectURL(response.data);
          const link = document.createElement('a');
          link.href = href;
          link.setAttribute('download', `${name}`);
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
          URL.revokeObjectURL(href);
        });
    }

    return {
      state,
      handlePicked,
      handleGotoPage,
      handleSortChanged,
      handlePageChanged,
      pagination,
      emitPagination,
      download,
      toggleBtn,
      tabKeyupLeft,
      tabKeyupRight,
      mobileDevice
    };
  },
};
</script>

<style>
#basicFooterButtonGroup,
#mapFooterButtonGroup,
#treeFooterButtonGroup {
  padding: 10px 20px;
  border: 1px solid #dee2e6;
}

td {
  margin: 0px 10px 0px 0px !important;
}

#searchTips {
  padding: 20px 25px;
}

#searchTips button {
  margin-left: 0px !important;
  margin-right: 4px !important;
}

#searchTips p {
  margin: 0px !important;
  /* border: 1px solid green; */
}

#badgeGroup {
  margin: 20px 0px 20px 0px;
}

#optionButtonGroup {
  margin: 0px 20px 20px 20px;
}

/* mrp0101r, mrp0201r Style */

#sdPopupModal.popup {
  position: absolute;
  top: 290px;
  left: 60%;
  width: 400px;
  padding: 15px !important;
  border-radius: 10px;
  border: 2px solid #17a2b8;
  background-color: white;
}

.popup p {
  margin: 0px !important;
}

.hidden {
  display: none !important;
}

#basicResult table {
  width: 100%;
}

#basicResult td,
#areaResult td,
#treeResult td {
  vertical-align: top;
}

#areaResultTable, #treeResultTable {
  margin: 0 auto;
}
#areaResultTable > tr > td,
#treeResultTable > tr > td {
  padding: 0px;
}

#mapContainer {
  width: 460px !important;
  /* border: 1px solid green; */
}
#areaResultTable > tr > td:last-child,
#treeResultTable > tr > td:last-child {
  width: 100%;
}

#mapContainer {
  position: relative;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  /* border: 1px solid blue; */
}
.bluePin {
  content: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAA0gAAAKgCAMAAACWWa2yAAAAAXNSR0IB2cksfwAAAAlwSFlzAAALEwAACxMBAJqcGAAAAuJQTFRFAAAAw8PDqqqqkpKSd3d3Xl5eSkpKREREQUFBQEBAPj4+PDw8Ojo6ODg4Nzc3NTU1MzMzMDAwLS0tKysrKioqKSkpJCQkLy8vQkJCVFRUaGhoeXl5i4uLnZ2dsLCwtbW1nJychISEbW1tVVVVQ0NDExMTCwsLCgoKCAgIBwcHBQUFBAQEAwMDAgICAQEBBgYGCQkJERERIyMjRkZGX19fdXV1lJSUvLy8ycnJoKCgdnZ2TExMEBAQAAAADQ0NUVFRe3t7pKSkwsLCmpqacHBwHx8fFhYWOzs7ZGRkjo6Ot7e3urq6eHh4GRkZJSUlioqKx8fHwMDAh4eHS0tLFxcXISEhWlpayMjIGxsbNDQ0qKiowcHBDAwMFRUVgYGBu7u7dHR0XV1dp6enbm5uJiYmb29vxMTEfn5+OTk5hYWFmZmZR0dHDw8PU1NTpaWlvb29bGxsICAgampqk5OTMTExhoaGgoKCJycnHBwcysrKcnJyZ2dnv7+/YGBgWFhYsrKyDg4OSUlJsbGxvr6+VlZWoqKiY2NjEhISlZWVcXFxGBgYrKysf39/HR0dkZGRAQYHCj5FEWp2FoqaGZ6wGqS3SEhIjY2Nra2tUFBQtLS0ubm5MjIyaWlpDldhfHx8pqamLCwsFBQUNjY2q6urYmJim5uba2trCDI3F5GhoaGhHh4egICAC0VNZmZml5eXZWVlfX19n5+fr6+vtra2KCgoRUVFGhoaPz8/rq6uiYmJW1tbenp6s7OzV1dXPT09y8vLxsbGuLi4c3NzLi4uWVlZTk5Oj4+Pqampg4ODIiIijIyMiIiIXFxcTU1NT09Pnp6emJiYlpaWxcXFkJCQYWFhUlJSo6OjL6i5bKu0eKmwfqithaaqRau5iaSokp+hZ6y1lJ6gUay4mJucP6q5KKe4l5ydYqy2PKq5jaKllZ2fX6y3SKu5bquzHqW3gKesMqm5fKiuOKq5Vqy4ZKy2kKCjdqqxx4e6lQAAAPZ0Uk5TAC6u///////////////////////////////////wj3b1/////////////////////////////1EQ4f/////////NMv////////9sXP////8ZPv//////Ff//uDj///9X//+9////KP/////////HTf///////////wv//0L//4X//4pI/9f//////6P//////////////5//e2H/////wv///6n/+v///9z/////////5pNx/////5n///+A//8GH2b//////7P/////////6///JP///9L/////////////////////////////////////////XhJTAAAAKQ9JREFUeJzt3XmcHOV54HE5IIy4wQiJK5zqu6t7RLiEasQlAbKRwZFAwAzCmEMowoDBIBDgIQLLgHUhcYoBBBjJXAYD4jIQwBDAkTFgvM5md7NJvEc22exuNvv/Tk/PaI5+nu7q6Z563qr6fT8WGLBF1fvWM89bT73HhAkAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEukrf7TDjhN3+urOk3bZdbfd99hzr733+dq+k/ebMnX/Aw486OA//or15QFOO+TQww4/4sjJ01LpTDaXLxSKfbz+P1QVC7lSNlNOpzqm73nUnxx9zLHWFww45bjjZ5yw38x0OVPK5Yue7/f9p/KH/j+J+mIqn8uWOyfPOvEk64sHHHDyKaceNTuVyRUGgmdUwIhRNPS3vUIpk5pz2ulnzLW+D8DM178x9cx0NldQE4/OGx5SxXw23Tlv1jfPsr4hIGxnf+tP56dzxdqso4/ntv9TOU8VSqkF5xxjfV9AaM49aNbCVDY/Ijq84X9RLwvVDbN8tvO8w8+3vj9g/F2w45SubGF4aNQJndp/1DCairly91EXLrK+TWAcHXvR17pK9cpxbdE3yisv+PbF1jcLjIu537mkKzfOMVQNpMqvfHnepeQlxM1ll09OhxJFQ4q5rsVXWN830EZL/mxpKdwo6ucVSx1Xftf65oG2uOrqazL5xk99G+Nn+F8UyrO/d5V1EwCtunaXrtzohzvkkMqnrvu+dTMArbj+hkyxziM+TrFTM9PIyy67yLopgDG68aZlWctMNOK/l7qXW7cHMAY339JdshrSiUozb73ZulGA5vxgVk+oBYYAPD/fedufWzcMENyK23vGMqd73HmF1K53WDcOENAPVw5mI4dGdgO89N4/sm4fIIA778pZR0tdXnnPu63bCGjgx6tK1cfVOFxqDV1RsWvW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');
}
.redPin {
  content: url('../../components/mrp/image/selectedPin.png') !important;
}
.pin {
  transform: scale(0.05);
  position: absolute;
}

.area_1 {
  /* 臺北市 */
  left: -55px;
  top: -290px;
}

.area_2 {
  /* 桃園市 */
  left: -105px;
  top: -265px;
}
.area_3 {
  /* 新竹縣 */
  left: -140px;
  top: -245px;
}
.area_4 {
  /* 苗栗縣 */
  left: -150px;
  top: -190px;
}
.area_5 {
  /* 臺中市 */
  left: -175px;
  top: -140px;
}
.area_6 {
  /* 彰化縣 */
  left: -215px;
  top: -105px;
}
.area_7 {
  /* 南投縣 */
  left: -145px;
  top: -80px;
}
.area_8 {
  /* 雲林縣 */
  left: -240px;
  top: -65px;
}
.area_9 {
  /* 嘉義市 */
  left: -225px;
  top: -30px;
}
.area_10 {
  /* 臺南市 */
  left: -235px;
  top: 30px;
}
.area_11 {
  /* 高雄市 */
  left: -185px;
  top: 55px;
}
.area_12 {
  /* 屏東縣 */
  left: -195px;
  top: 135px;
}
.area_13 {
  /* 宜蘭縣 */
  left: -45px;
  top: -202px;
}
.area_14 {
  /* 花蓮縣 */
  left: -75px;
  top: -70px;
}
.area_15 {
  /* 臺東縣 */
  left: -135px;
  top: 70px;
}
.area_16 {
  /* 金門縣 */
  left: -320px;
  top: -175px;
}
.area_17 {
  /* 澎湖縣 */
  left: -345px;
  top: -60px;
}
.area_19 {
  /* 新北市 */
  left: -60px;
  top: -250px;
}
.area_20 {
  /* 基隆市 */
  left: -30px;
  top: -300px;
}
.area_21 {
  /* 連江縣 */
  left: -310px;
  top: -305px;
}
.area_22 {
  /* 新竹縣 */
  left: -110px;
  top: -215px;
}

.area_23 {
  /* 嘉義縣 */
  left: -190px;
  top: -15px;
}

/* tree */
#treeContainer * {
  list-style: none !important;
}

#treeContainer > ul {
  padding-left: 25px;
}

#treeContainer > ul ul {
  padding-left: 1.2em;
}

.last_ul {
  padding-left: 0px;
}

.last_li {
  padding-left: 1.2em;
  background: url(../../components/mrp/image/file.png) no-repeat;
  background-position: left;
}
.closeFolder {
  padding-left: 1.2em;
  background: url(../../components/mrp/image/closeFolder.png) no-repeat;
  background-position: left;
}
.openFolder {
  padding-left: 1.2em;
  background: url(../../components/mrp/image/openFolder.png) no-repeat;
  background-position: left;
}

#treeContainer li {
  cursor: pointer;
}

/* tree */

/* trend */
/* trend */
</style>
