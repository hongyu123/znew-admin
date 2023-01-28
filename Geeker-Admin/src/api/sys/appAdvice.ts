import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/appAdvice", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>(`/appAdvice/${id}`);
};
export const save = (params: object) => {
  return http.post<ApiResult>("/appAdvice", params);
};
export const edit = (params: object) => {
  return http.put<ApiResult>("/appAdvice", params);
};
export const del = (id: number) => {
  return http.delete<ApiResult>(`/appAdvice/${id}`);
};
export const dels = (params: string[]) => {
  return http.delete<ApiResult>("/appAdvice/dels", params);
};
export const read = (id: number) => {
  return http.post<ApiResult>(`/appAdvice/read/${id}`);
};
export const reads = (params: string[]) => {
  return http.post<ApiResult>("/appAdvice/reads", params);
};
export const unreadCount = () => {
  return http.get<ApiResult>("/appAdvice/unreadCount");
};
