import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/sysRole/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>("/sysRole/detail", { id });
};
export const save = (params: object) => {
  return http.post<ApiResult>("/sysRole/save", params);
};
export const edit = (params: object) => {
  return http.post<ApiResult>("/sysRole/edit", params);
};
export const del = (params: { id: number }) => {
  return http.post<ApiResult>("/sysRole/del", params);
};
export const dels = (params: string[]) => {
  return http.post<ApiResult>("/sysRole/dels", params);
};
export const list = (params: object) => {
  return http.get<ApiResult>("/sysRole/list", params);
};

export const users = (params: object) => {
  return http.get<PageResult>("/sysRole/users", params);
};
export const addUsers = (params: object) => {
  return http.post<ApiResult>("/sysRole/addUsers", params);
};
export const delUsers = (params: object) => {
  return http.post<ApiResult>("/sysRole/delUsers", params);
};
