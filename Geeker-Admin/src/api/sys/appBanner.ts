import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: {}) => {
  return http.get<PageResult>("/appBanner/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>(`/appBanner?id=${id}`);
};
export const add = (params: {}) => {
  return http.post<ApiResult>("/appBanner", params);
};
export const edit = (params: {}) => {
  return http.put<ApiResult>("/appBanner", params);
};
export const del = (id: number) => {
  return http.delete<ApiResult>(`/appBanner?id=${id}`);
};
export const dels = (params: string[]) => {
  return http.delete<ApiResult>("/appBanner/dels", params);
};
