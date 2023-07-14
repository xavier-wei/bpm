<template>
  <div id="stepGroup" class="flex">
    <span id="step1" class="step on"><p>步驟一 填表及XML上傳</p></span>
    <span><p>-></p></span>
    <span id="step2" class="step"><p>步驟二 上傳預覧</p></span>
    <span><p>-></p></span>
    <span id="step3" class="step"><p>步驟三 線上檢覆</p></span>
    <span><p>-></p></span>
    <span id="step4" class="step"><p>步驟四 完成上傳</p></span>
  </div>
</template>

<script lang="ts"></script>

<style scoped>
#stepGroup {
  flex-direction: row;
  flex-wrap: nowrap;
  align-items: center;
  justify-content: space-evenly;
  padding: 0px 50px;
  margin-top: 50px;
  /* border: 1px solid red; */
}

#stepGroup span {
  padding: 5px 10px;
}

#stepGroup p {
  margin: 0px;
  padding: 0px;
}

.on {
  background-color: lightgray;
}
</style>
