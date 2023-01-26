import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/appUser", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>(`/appUser/${id}`);
};
export const add = (params: object) => {
  return http.post<ApiResult>("/appUser/add", params);
};
export const edit = (params: object) => {
  return http.put<ApiResult>("/appUser/edit", params);
};
export const del = (id: number) => {
  return http.delete<ApiResult>(`/appUser/${id}`);
};
export const dels = (params: string[]) => {
  return http.delete<ApiResult>("/appUser/dels", params);
};
export const state = (params: object) => {
  return http.put<ApiResult>("/appUser/state", params);
};
