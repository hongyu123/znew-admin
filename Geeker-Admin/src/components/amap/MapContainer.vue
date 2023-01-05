<template>
  <div id="container"></div>
  <div id="myPageTop">
    <input id="tipinput" style="border: none" placeholder="请输入搜索" />
  </div>
</template>

<script setup lang="ts" name="MapContainer">
import AMapLoader from "@amap/amap-jsapi-loader";
import { ref, shallowRef } from "@vue/reactivity";
import { onMounted } from "vue";

const mapRef = shallowRef(null);

let addr: any = ref({});
let marker: any;
const initMap = () => {
  AMapLoader.load({
    key: "1e8884bf03341405ec3a8df0c31e2529", // 申请好的Web端开发者Key，首次调用 load 时必填
    version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
    plugins: ["AMap.PlaceSearch", "AMap.AutoComplete", "AMap.Geocoder"] // 需要使用的的插件列表，如比例尺'AMap.Scale'等
  })
    .then(AMap => {
      let map = new AMap.Map("container", {
        //设置地图容器id
        viewMode: "3D", //是否为3D地图模式
        zoom: 11, //初始化地图级别
        center: [102.833669, 24.88149] //初始化地图中心点位置
      });
      let auto = new AMap.AutoComplete({ input: "tipinput" });
      let placeSearch = new AMap.PlaceSearch({
        map: map
      }); //构造地点查询类
      let geocoder = new AMap.Geocoder();

      //注册监听，当选中某条记录时会触发
      auto.on("select", (e: any) => {
        placeSearch.setCity(e.poi.adcode);
        placeSearch.search(e.poi.name); //关键字查询查询
      });

      map.on("click", (e: any) => {
        addr.value.lng = e.lnglat.lng;
        addr.value.lat = e.lnglat.lat;
        if (marker) {
          marker.setMap(null);
          marker = null;
        }
        marker = new AMap.Marker({
          icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-red.png",
          position: [e.lnglat.lng, e.lnglat.lat],
          offset: new AMap.Pixel(-13, -30)
        });
        geocoder.getAddress([e.lnglat.lng, e.lnglat.lat], function (status: any, result: any) {
          if (status === "complete" && result.info === "OK") {
            addr.value.province = result.regeocode.addressComponent.province;
            addr.value.city = result.regeocode.addressComponent.city;
            addr.value.addressDetail = result.regeocode.formattedAddress;
            // 自定义点标记内容
            let markerContent = document.createElement("div");

            // 点标记中的图标
            let markerImg = document.createElement("img");
            markerImg.className = "markerlnglat";
            markerImg.src = "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-red.png";
            markerImg.setAttribute("width", "25px");
            markerImg.setAttribute("height", "34px");
            markerContent.appendChild(markerImg);

            // 点标记中的文本
            let markerSpan = document.createElement("span");
            markerSpan.className = "marker";
            markerSpan.innerHTML = addr.value.addressDetail;
            markerContent.appendChild(markerSpan);
            marker.setContent(markerContent);
          }
        });
        marker.setMap(map);
      });
    })
    .catch(e => {
      console.log(e);
    });
};

onMounted(() => {
  window._AMapSecurityConfig = {
    securityJsCode: "9d838728cd140a360034a0d784277001"
  };
  initMap();
});

const getAddress = () => {
  return addr.value;
};

defineExpose({ mapRef, getAddress });
</script>

<style scoped>
#container {
  padding: 0px;
  margin: 0px;
  width: 100%;
  height: 450px;
}
#myPageTop {
  position: absolute;
  top: 5px;
  right: 10%;
  font-family: "Microsoft Yahei", Pinghei;
  font-size: 14px;
  background: none 0px 0px repeat scroll rgb(255, 255, 255);
  border-width: 1px;
  border-style: solid;
  border-color: rgb(204, 204, 204);
  border-image: initial;
  margin: 10px auto;
  padding: 6px;
}
</style>
<style>
.amap-sug-result {
  z-index: 9999;
}
.amap-icon img,
.amap-marker-content img {
  width: 25px;
  height: 34px;
}
.marker {
  position: absolute;
  top: -20px;
  /*right: -118px;*/
  color: #fff;
  padding: 4px 10px;
  box-shadow: 1px 1px 1px rgba(10, 10, 10, 0.2);
  white-space: nowrap;
  font-size: 12px;
  font-family: "";
  background-color: #25a5f7;
  border-radius: 3px;
}
</style>
