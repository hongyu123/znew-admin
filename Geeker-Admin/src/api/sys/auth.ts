import DynamicRouter from "@/assets/json/dynamicRouter.json";
import { Login } from "@/api/interface/index";
import http from "@/api";
import { ApiResult } from "@/api/interface/znew";

//登录
export const loginApi = (params: Login.ReqLoginForm) => {
  return http.post<Login.ResLogin>("/login", params, { headers: { noLoading: true } });
};
//登出
export const logoutApi = () => {
  return http.post<ApiResult>("/logout", { headers: { noLoading: true } });
};
// * 获取按钮权限
export const getAuthButtonListApi = () => {
  return http.get<Login.ResAuthButtons>("/auth/buttons", {}, { headers: { noLoading: true } });
};
// * 获取菜单列表
export const getAuthMenuListApi = () => {
  return http.get<Menu.MenuOptions[]>("/auth/menu", {}, { headers: { noLoading: true } });
  // 如果想让菜单变为本地数据，注释上一行代码，并引入本地 dynamicRouter.json 数据
  return DynamicRouter;
};
