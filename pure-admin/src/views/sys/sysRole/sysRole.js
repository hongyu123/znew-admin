import { http } from "@/utils/http";

export const page = params => {
  return http.get("/sysRole/page", params);
};
export const detail = id => {
  return http.get("/sysRole", { id });
};
export const add = data => {
  return http.post("/sysRole", data);
};
export const edit = data => {
  return http.put("/sysRole", data);
};
export const del = id => {
  return http.delete(
    "/sysRole",
    { id },
    { headers: { "Content-Type": "multipart/form-data" } }
  );
};
export const dels = ids => {
  return http.delete("/sysRole/dels", ids);
};

export const currentUserRolesWithOwn = () => {
  return http.get("/sysRole/currentUserRolesWithOwn", {});
};
