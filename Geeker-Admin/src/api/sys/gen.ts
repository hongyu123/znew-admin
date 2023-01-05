import http from "@/api";
import { ApiResult, PageResult } from "@/api/interface/znew";

export const tablePage = (params: object) => {
  return http.get<PageResult>("/gen/tablePage", params);
};
export const genToPath = (params: object) => {
  return http.post<ApiResult>("/gen/genToPath", params);
};
export const batchGenToPath = (params: string[]) => {
  return http.post<ApiResult>("/gen/batchGenToPath", params);
};
export const genToProject = (params: object) => {
  return http.post<ApiResult>("/gen/genToProject", params);
};
export const batchGenToProject = (params: string[]) => {
  return http.post<ApiResult>("/gen/batchGenToProject", params);
};

export const formInfo = (params: object) => {
  return http.get<ApiResult>("/gen/formInfo", params);
};
export const genFormToPath = (params: object) => {
  return http.post<ApiResult>("/gen/genFormToPath", params);
};
export const genFormToProject = (params: object) => {
  return http.post<ApiResult>("/gen/genFormToProject", params);
};

export const page = (params: object) => {
  return http.get<PageResult>("/sysGenTable/page", params);
};
export const detail = (id: any) => {
  return http.get<ApiResult>("/sysGenTable/detail", { id });
};
