import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: {}) => {
  return http.get<PageResult>("/appVersion/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>(`/appVersion?id=${id}`);
};
export const save = (params: {}) => {
  return http.post<ApiResult>("/appVersion", params);
};
export const edit = (params: {}) => {
  return http.put<ApiResult>("/appVersion", params);
};
export const del = (id: number) => {
  return http.delete<ApiResult>(`/appVersion?id=${id}`);
};
export const dels = (params: string[]) => {
  return http.delete<ApiResult>("/appVersion/dels", params);
};
