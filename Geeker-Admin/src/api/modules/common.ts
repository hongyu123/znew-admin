import http from "@/api";
import { ApiResult } from "@/api/interface/znew";

export const upload = (params: object) => {
  return http.post<ApiResult>("/common/upload", params);
};
