import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: {}) => {
  return http.get<PageResult>("/${beanName}/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>(`/${beanName}/?id=${r'${id}'}`);
};
export const add = (params: {}) => {
  return http.post<ApiResult>("/${beanName}", params);
};
export const edit = (params: {}) => {
  return http.put<ApiResult>("/${beanName}", params);
};
export const del = (id: number) => {
  return http.delete<ApiResult>(`/${beanName}?id=${r'${id}'}`);
};
export const dels = (params: string[]) => {
  return http.delete<ApiResult>("/${beanName}/dels", params);
};
