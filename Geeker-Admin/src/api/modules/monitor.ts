import http from "@/api";
import { ApiResult } from "@/api/interface/znew";

export const getServer = () => {
  return http.get<ApiResult>("/monitor/server");
};
