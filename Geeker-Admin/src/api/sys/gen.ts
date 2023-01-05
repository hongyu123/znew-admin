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
export const genForm = (params: object) => {
  return http.post<ApiResult>("/gen/genForm", params);
};
