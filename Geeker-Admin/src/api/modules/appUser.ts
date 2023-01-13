import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/appUser/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>("/appUser/detail", { id });
};
export const add = (params: object) => {
  return http.post<ApiResult>("/appUser/add", params);
};
export const edit = (params: object) => {
  return http.post<ApiResult>("/appUser/edit", params);
};
export const del = (params: { id: number }) => {
  return http.post<ApiResult>("/appUser/del", params);
};
export const dels = (params: string[]) => {
  return http.post<ApiResult>("/appUser/dels", params);
};
export const state = (params: object) => {
  return http.post<ApiResult>("/appUser/state", params);
};
