import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/appVersion/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>("/appVersion/detail", { id });
};
export const save = (params: object) => {
  return http.post<ApiResult>("/appVersion/save", params);
};
export const edit = (params: object) => {
  return http.post<ApiResult>("/appVersion/edit", params);
};
export const del = (params: { id: number }) => {
  return http.post<ApiResult>("/appVersion/del", params);
};
export const dels = (params: string[]) => {
  return http.post<ApiResult>("/appVersion/dels", params);
};
