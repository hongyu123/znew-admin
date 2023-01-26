import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: {}) => {
  return http.get<PageResult>("/appArticle", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>(`/appArticle/${id}`);
};
export const add = (params: {}) => {
  return http.post<ApiResult>("/appArticle", params);
};
export const edit = (params: {}) => {
  return http.put<ApiResult>("/appArticle", params);
};
export const del = (id: number) => {
  return http.delete<ApiResult>(`/appArticle/${id}`);
};
export const dels = (params: string[]) => {
  return http.delete<ApiResult>("/appArticle/dels", params);
};
