import http from "@/api";
import { ApiResult } from "@/api/interface/znew";

export const gets = () => {
  return http.get<ApiResult>(`/sysConfig`);
};
export const sets = (params: {}) => {
  return http.post<ApiResult>("/sysConfig", params);
};
