import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: {}) => {
  return http.get<PageResult>("/sysDemo", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>(`/sysDemo/${id}`);
};
export const add = (params: {}) => {
  return http.post<ApiResult>("/sysDemo", params);
};
export const edit = (params: {}) => {
  return http.put<ApiResult>("/sysDemo", params);
};
export const del = (id: number) => {
  return http.delete<ApiResult>(`/sysDemo/${id}`);
};
export const dels = (params: string[]) => {
  return http.delete<ApiResult>("/sysDemo/dels", params);
};
