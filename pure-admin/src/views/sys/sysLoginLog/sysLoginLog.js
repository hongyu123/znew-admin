import { http } from "@/utils/http";

export const page = params => {
  return http.get("/sysLoginLog/page", params);
};
export const detail = id => {
  return http.get("/sysLoginLog", { id });
};
export const add = data => {
  return http.post("/sysLoginLog", data);
};
export const edit = data => {
  return http.put("/sysLoginLog", data);
};
export const del = id => {
  return http.delete(
    "/sysLoginLog",
    { id },
    { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
  );
};
export const dels = ids => {
  return http.delete("/sysLoginLog/dels", ids);
};

export const kickout = data => {
  return http.post("/sysLoginLog/kickout", data, {
    headers: { "Content-Type": "application/x-www-form-urlencoded" }
  });
};
