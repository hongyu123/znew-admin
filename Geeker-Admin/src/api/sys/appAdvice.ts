import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/appAdvice/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>("/appAdvice/detail", { id });
};
export const save = (params: object) => {
  return http.post<ApiResult>("/appAdvice/save", params);
};
export const edit = (params: object) => {
  return http.post<ApiResult>("/appAdvice/edit", params);
};
export const del = (params: { id: number }) => {
  return http.post<ApiResult>("/appAdvice/del", params);
};
export const dels = (params: string[]) => {
  return http.post<ApiResult>("/appAdvice/dels", params);
};
export const read = (params: { id: number }) => {
  return http.post<ApiResult>("/appAdvice/read", params);
};
export const reads = (params: string[]) => {
  return http.post<ApiResult>("/appAdvice/reads", params);
};
export const unreadCount = () => {
  return http.get<ApiResult>("/appAdvice/unreadCount");
};
