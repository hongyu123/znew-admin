import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/sysLoginLog/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>(`/sysLoginLog?id=${id}`);
};
export const save = (params: object) => {
  return http.post<ApiResult>("/sysLoginLog", params);
};
export const edit = (params: object) => {
  return http.put<ApiResult>("/sysLoginLog", params);
};
export const del = (params: { id: number }) => {
  return http.delete<ApiResult>("/sysLoginLog", params);
};
export const dels = (params: string[]) => {
  return http.delete<ApiResult>("/sysLoginLog/dels", params);
};
