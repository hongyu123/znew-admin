import http from "@/api";
import { ApiResult } from "@/api/interface/znew";

export const upload = (params: object) => {
  return http.post<ApiResult>("/common/upload", params);
};

export const errorReport = (params: {}) => {
  return http.get<BlobPart>("/common/error/report", params, { responseType: "blob" });
};

export const exportTemplate = (params: {}) => {
  return http.get<BlobPart>("/export/template", params, { responseType: "blob" });
};
