import { http } from "@/utils/http";

export const page = params => {
  return http.get("/sysLoginLog/page", params);
};

export const kickout = data => {
  return http.post("/sysLoginLog/kickout", data, {
    headers: { "Content-Type": "application/x-www-form-urlencoded" }
  });
};

export const userLoginLog = params => {
  return http.get("/sysLoginLog/userLoginLog", params);
};
