import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: {}) => {
  return http.get<PageResult>("/sysAdminLog", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>(`/sysAdminLog/${id}`);
};
export const save = (params: {}) => {
  return http.post<ApiResult>("/sysAdminLog", params);
};
export const edit = (params: {}) => {
  return http.put<ApiResult>("/sysAdminLog", params);
};
export const del = (id: number) => {
  return http.delete<ApiResult>(`/sysAdminLog/${id}`);
};
export const dels = (params: string[]) => {
  return http.delete<ApiResult>("/sysAdminLog/dels", params);
};
