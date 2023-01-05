import { defineStore } from "pinia";
import { unreadCount } from "@/api/sys/appAdvice";
import piniaPersistConfig from "@/config/piniaPersist";

// AuthStore
export const OtherStore = defineStore({
  id: "OtherState",
  state: (): any => ({
    unreadCnt: 0
  }),
  getters: {
    unreadCntGet: state => state.unreadCnt
  },
  actions: {
    // getAuthButtonList
    async loadUnreadCnt() {
      const { data } = await unreadCount();
      this.unreadCnt = data.count;
    }
  },
  persist: piniaPersistConfig("OtherState", ["authButtonList", "routeName"])
});
