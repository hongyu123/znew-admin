import { http } from "@/utils/http";

export const page = params => {
  return http.get("/sysUser/page", params);
};
export const detail = id => {
  return http.get("/sysUser", { id });
};
export const add = data => {
  return http.post("/sysUser", data);
};
export const edit = data => {
  return http.put("/sysUser", data);
};
export const del = id => {
  return http.delete(
    "/sysUser",
    { id },
    { headers: { "Content-Type": "multipart/form-data" } }
  );
};
export const dels = ids => {
  return http.delete("/sysUser/dels", ids);
};
