import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: {}) => {
  return http.get<PageResult>("/sysUser/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>(`/sysUser?id=${id}`);
};
export const current = () => {
  return http.get<ApiResult>("/sysUser/current");
};
export const save = (params: {}) => {
  return http.post<ApiResult>("/sysUser", params);
};
export const edit = (params: {}) => {
  return http.put<ApiResult>("/sysUser", params);
};
export const del = (id: number) => {
  return http.delete<ApiResult>(`/sysUser?id=${id}`);
};
export const dels = (params: string[]) => {
  return http.delete<ApiResult>("/sysUser/dels", params);
};
export const resetPassword = (params: {}) => {
  return http.put<ApiResult>("/sysUser/resetPassword", params);
};
export const changePassword = (params: {}) => {
  return http.put<ApiResult>("/sysUser/changePassword", params);
};
