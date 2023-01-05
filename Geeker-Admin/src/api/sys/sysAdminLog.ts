import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/sysAdminLog/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>("/sysAdminLog/detail", { id });
};
export const save = (params: object) => {
  return http.post<ApiResult>("/sysAdminLog/save", params);
};
export const edit = (params: object) => {
  return http.post<ApiResult>("/sysAdminLog/edit", params);
};
export const del = (params: { id: number }) => {
  return http.post<ApiResult>("/sysAdminLog/del", params);
};
export const dels = (params: string[]) => {
  return http.post<ApiResult>("/sysAdminLog/dels", params);
};
