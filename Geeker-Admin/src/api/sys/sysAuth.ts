import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const page = (params: object) => {
  return http.get<PageResult>("/sysAuth/page", params);
};
export const detail = (id: number) => {
  return http.get<ApiResult>("/sysAuth/detail", { id });
};
export const save = (params: object) => {
  return http.post<ApiResult>("/sysAuth/save", params);
};
export const edit = (params: object) => {
  return http.post<ApiResult>("/sysAuth/edit", params);
};
export const del = (params: { id: number }) => {
  return http.post<ApiResult>("/sysAuth/del", params);
};
export const dels = (params: string[]) => {
  return http.post<ApiResult>("/sysAuth/dels", params);
};

export const children = (parentId: number = 0) => {
  return http.get<ApiResult>("/sysAuth/children", { parentId });
};
export const tree = (params: object) => {
  return http.get<ApiResult>("/sysAuth/tree", params);
};
