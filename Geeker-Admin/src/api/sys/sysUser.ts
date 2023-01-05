import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/sysUser/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>("/sysUser/detail", { id });
};
export const current = () => {
  return http.get<ApiResult>("/sysUser/current");
};
export const save = (params: object) => {
  return http.post<ApiResult>("/sysUser/save", params);
};
export const edit = (params: object) => {
  return http.post<ApiResult>("/sysUser/edit", params);
};
export const del = (params: { id: number }) => {
  return http.post<ApiResult>("/sysUser/del", params);
};
export const dels = (params: string[]) => {
  return http.post<ApiResult>("/sysUser/dels", params);
};
export const resetPassword = (params: object) => {
  return http.post<ApiResult>("/sysUser/resetPassword", params);
};
export const changePassword = (params: object) => {
  return http.post<ApiResult>("/sysUser/changePassword", params);
};
