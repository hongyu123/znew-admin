import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/sysDemo/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>("/sysDemo/detail", { id });
};
export const save = (params: object) => {
  return http.post<ApiResult>("/sysDemo/add", params);
};
export const edit = (params: object) => {
  return http.post<ApiResult>("/sysDemo/edit", params);
};
export const del = (params: { id: number }) => {
  return http.post<ApiResult>("/sysDemo/del", params);
};
export const dels = (params: string[]) => {
  return http.post<ApiResult>("/sysDemo/dels", params);
};
