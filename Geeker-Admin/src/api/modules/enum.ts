import http from "@/api";
import { ApiResult } from "@/api/interface/znew";

//common模块枚举
export const commonEnum = (params: object) => {
  return http.get<ApiResult>("/common/enum/common", params);
};
export const GenderEnum = () => {
  return commonEnum({ code: "Gender" });
};
export const EnableStateEnum = () => {
  return commonEnum({ code: "EnableState" });
};

//base模块枚举
export const baseEnum = (params: object) => {
  return http.get<ApiResult>("/common/enum/base", params);
};
export const SysAuthEnum = () => {
  return baseEnum({ code: "SysAuthEnum" });
};
export const LogoutType = () => {
  return baseEnum({ code: "LogoutType" });
};
export const AppArticleType = () => {
  return baseEnum({ code: "AppArticleType" });
};
export const AppBannerEnum = () => {
  return baseEnum({ code: "AppBannerEnum" });
};
