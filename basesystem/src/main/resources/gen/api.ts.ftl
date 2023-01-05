import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/${beanName}/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>("/${beanName}/detail", { id });
};
export const add = (params: object) => {
  return http.post<ApiResult>("/${beanName}/add", params);
};
export const edit = (params: object) => {
  return http.post<ApiResult>("/${beanName}/edit", params);
};
export const del = (params: { id: number }) => {
  return http.post<ApiResult>("/${beanName}/del", params);
};
export const dels = (params: string[]) => {
  return http.post<ApiResult>("/${beanName}/dels", params);
};
