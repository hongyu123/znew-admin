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
    { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
  );
};
export const dels = ids => {
  return http.delete("/sysUser/dels", ids);
};

export const changePassword = data => {
  return http.post("/sysUser/changePassword", data);
};
export const resetPassword = data => {
  return http.post("/sysUser/resetPassword", data);
};
export const userInfo = () => {
  return http.get("/sysUser/userInfo");
};
export const editInfo = data => {
  return http.post("/sysUser/editInfo", data);
};
