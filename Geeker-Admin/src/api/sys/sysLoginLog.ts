import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/sysLoginLog/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>("/sysLoginLog/detail", { id });
};
export const save = (params: object) => {
  return http.post<ApiResult>("/sysLoginLog/save", params);
};
export const edit = (params: object) => {
  return http.post<ApiResult>("/sysLoginLog/edit", params);
};
export const del = (params: { id: number }) => {
  return http.post<ApiResult>("/sysLoginLog/del", params);
};
export const dels = (params: string[]) => {
  return http.post<ApiResult>("/sysLoginLog/dels", params);
};
