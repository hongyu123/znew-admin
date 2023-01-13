import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/appArticle/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>("/appArticle/detail", { id });
};
export const add = (params: object) => {
  return http.post<ApiResult>("/appArticle/add", params);
};
export const edit = (params: object) => {
  return http.post<ApiResult>("/appArticle/edit", params);
};
export const del = (params: { id: number }) => {
  return http.post<ApiResult>("/appArticle/del", params);
};
export const dels = (params: string[]) => {
  return http.post<ApiResult>("/appArticle/dels", params);
};
