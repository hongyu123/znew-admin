<template>
  <el-tag v-if="props.id" type="success" style="width: 100%">验证成功!</el-tag>
  <el-popover @show="refreshCaptcha" placement="top-start" :width="303" trigger="hover" v-else>
    <div class="slider">
      <div class="content">
        <div class="bg-img-div">
          <img id="bg-img" :src="captcha.backgroundImage" />
        </div>
        <div class="slider-img-div" id="slider-img-div">
          <img id="slider-img" :src="captcha.sliderImage" />
        </div>
      </div>
      <div class="slider-move">
        <div class="slider-move-track">拖动滑块完成拼图</div>
        <div class="slider-move-btn" id="slider-move-btn" @mousedown="startMove($event)"></div>
      </div>
      <div class="bottom">
        <!-- <div class="close-btn" id="slider-close-btn"></div> -->
        <div class="refresh-btn" id="slider-refresh-btn" @click="refreshCaptcha"></div>
      </div>
    </div>
    <template #reference>
      <el-tag style="color: #88949d; width: 100%">请点击验证</el-tag>
    </template>
  </el-popover>
</template>
<script setup lang="ts" name="Captcha">
import { ref } from "vue";
import { ElMessage } from "element-plus";
import { genCaptcha, checkCaptcha } from "@/api/sys/auth";

interface CaptchaProps {
  id: string;
}
// 接受父组件参数
const props = withDefaults(defineProps<CaptchaProps>(), {
  id: ""
});
interface CaptchaEmits {
  (e: "update:id", value: string): void;
}
const emit = defineEmits<CaptchaEmits>();

const captcha = ref<any>({ backgroundImage: "", sliderImage: "", startX: 0, startY: 0, trackList: [] });
// onMounted(() => {
//   refreshCaptcha();
// });
const refreshCaptcha = () => {
  window.document.getElementById("slider-move-btn")!.style.transform = `translate(0px,0px)`;
  window.document.getElementById("slider-img-div")!.style.transform = `translate(0px,0px)`;
  genCaptcha().then((res: any) => {
    captcha.value.id = res.id;
    captcha.value.backgroundImage = res.captcha.backgroundImage;
    captcha.value.sliderImage = res.captcha.sliderImage;
    captcha.value.startTime = new Date();
  });
};

const startMove = (event: MouseEvent) => {
  const moveBtn = window.document.getElementById("slider-move-btn");
  if (!moveBtn) {
    return;
  }
  captcha.value.startX = event.pageX;
  captcha.value.startY = event.pageY;
  moveBtn.style.backgroundPosition = "-5px 31.0092%";
  window.addEventListener("mousemove", move);
  window.addEventListener("mouseup", endMove);

  const track = {
    x: 0,
    y: 0,
    type: "down",
    t: new Date().getTime() - captcha.value.startTime.getTime()
  };
  captcha.value.trackList.push(track);
};
const endMove = (event: MouseEvent) => {
  window.removeEventListener("mousemove", move);
  window.removeEventListener("mouseup", endMove);
  const track = {
    x: event.pageX - captcha.value.startX,
    y: event.pageY - captcha.value.startY,
    type: "up",
    t: new Date().getTime() - captcha.value.startTime.getTime()
  };
  captcha.value.trackList.push(track);

  const bgImg = document.getElementById("bg-img");
  const sliderImg = document.getElementById("slider-img");
  const checkData = {
    bgImageWidth: bgImg?.offsetWidth,
    bgImageHeight: bgImg?.offsetHeight,
    sliderImageWidth: sliderImg?.offsetWidth,
    sliderImageHeight: sliderImg?.offsetHeight,
    startSlidingTime: format(captcha.value.startTime),
    entSlidingTime: format(new Date()),
    trackList: captcha.value.trackList
  };
  checkCaptcha(captcha.value.id, checkData).then((res: any) => {
    if (res.code == 1) {
      emit("update:id", captcha.value.id);
    } else {
      ElMessage.error(res.message);
      refreshCaptcha();
    }
  });
};
const move = (event: MouseEvent) => {
  let moveX = event.pageX - captcha.value.startX;
  if (moveX < 0) {
    moveX = 0;
  }
  if (moveX > 206) {
    moveX = 206;
  }
  window.document.getElementById("slider-move-btn")!.style.transform = `translate(${moveX}px,0px)`;
  window.document.getElementById("slider-img-div")!.style.transform = `translate(${moveX}px,0px)`;
  const track = {
    x: event.pageX - captcha.value.startX,
    y: event.pageY - captcha.value.startY,
    type: "move",
    t: new Date().getTime() - captcha.value.startTime.getTime()
  };
  captcha.value.trackList.push(track);
};

//格式化时间
function format(dat: Date) {
  //获取年月日，时间
  let year = dat.getFullYear();
  let mon = dat.getMonth() + 1 < 10 ? "0" + (dat.getMonth() + 1) : dat.getMonth() + 1;
  let data = dat.getDate() < 10 ? "0" + dat.getDate() : dat.getDate();
  let hour = dat.getHours() < 10 ? "0" + dat.getHours() : dat.getHours();
  let min = dat.getMinutes() < 10 ? "0" + dat.getMinutes() : dat.getMinutes();
  let seon = dat.getSeconds() < 10 ? "0" + dat.getSeconds() : dat.getSeconds();

  let newDate = year + "-" + mon + "-" + data + " " + hour + ":" + min + ":" + seon;
  return newDate;
}
</script>
<style scoped>
.slider {
  background-color: #fff;
  height: 285px;
  z-index: 999;
  box-sizing: border-box;
  padding: 9px;
  border-radius: 6px;
  width: 278px;
  /*box-shadow: 0 0 11px 0 #999999;
  */
}

.slider .content {
  width: 100%;
  height: 159px;
  position: relative;
}

.bg-img-div {
  width: 100%;
  height: 100%;
  position: absolute;
  transform: translate(0px, 0px);
}

.slider-img-div {
  height: 100%;
  position: absolute;
  transform: translate(0px, 0px);
}

.bg-img-div img {
  width: 100%;
}

.slider-img-div img {
  height: 100%;
}

.slider .slider-move {
  height: 60px;
  width: 100%;
  margin: 11px 0;
  position: relative;
}

.slider .bottom {
  height: 19px;
  width: 100%;
}

.refresh-btn,
.close-btn,
.slider-move-track,
.slider-move-btn {
  background: url("@/assets/images/sprite.1.2.4.png") no-repeat;
}

.refresh-btn,
.close-btn {
  display: inline-block;
}

.slider-move .slider-move-track {
  line-height: 38px;
  font-size: 14px;
  text-align: center;
  white-space: nowrap;
  color: #88949d;
  -moz-user-select: none;
  -webkit-user-select: none;
  user-select: none;
}

.slider {
  user-select: none;
}

.slider-move .slider-move-btn {
  transform: translate(0px, 0px);
  background-position: -5px 11.79625%;
  position: absolute;
  top: -12px;
  left: 0;
  width: 66px;
  height: 66px;
}

.slider-move-btn:hover,
.close-btn:hover,
.refresh-btn:hover {
  cursor: pointer;
}

.bottom .close-btn {
  width: 20px;
  height: 20px;
  background-position: 0 44.86874%;
}

.bottom .refresh-btn {
  width: 20px;
  height: 20px;
  background-position: 0 81.38425%;
}
</style>
