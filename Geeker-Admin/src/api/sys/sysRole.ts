import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/sysRole/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>(`/sysRole?id=${id}`);
};
export const save = (params: object) => {
  return http.post<ApiResult>("/sysRole", params);
};
export const edit = (params: object) => {
  return http.put<ApiResult>("/sysRole", params);
};
export const del = (id: number) => {
  return http.delete<ApiResult>(`/sysRole?id=${id}`);
};
export const dels = (params: string[]) => {
  return http.delete<ApiResult>("/sysRole/dels", params);
};
export const list = (params: object) => {
  return http.get<ApiResult>("/sysRole/list", params);
};

export const users = (params: object) => {
  return http.get<PageResult>("/sysRole/users", params);
};
export const addUsers = (params: object) => {
  return http.post<ApiResult>("/sysRole/users", params);
};
export const delUsers = (params: object) => {
  return http.put<ApiResult>("/sysRole/users", params);
};
