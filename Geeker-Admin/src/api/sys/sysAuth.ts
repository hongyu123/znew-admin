import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: {}) => {
  return http.get<PageResult>("/sysAuth", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>(`/sysAuth/${id}`);
};
export const save = (params: {}) => {
  return http.post<ApiResult>("/sysAuth", params);
};
export const edit = (params: {}) => {
  return http.put<ApiResult>("/sysAuth", params);
};
export const del = (id: number) => {
  return http.delete<ApiResult>(`/sysAuth/${id}`);
};
export const dels = (params: string[]) => {
  return http.delete<ApiResult>("/sysAuth/dels", params);
};

export const children = (parentId: number = 0) => {
  return http.get<ApiResult>("/sysAuth/children", { parentId });
};
export const tree = (params: {}) => {
  return http.get<ApiResult>("/sysAuth/tree", params);
};
