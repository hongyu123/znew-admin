import { http } from "@/utils/http";

export const page = params => {
  return http.get("/sysAdminLog/page", params);
};
export const detail = id => {
  return http.get("/sysAdminLog", { id });
};
export const add = data => {
  return http.post("/sysAdminLog", data);
};
export const edit = data => {
  return http.put("/sysAdminLog", data);
};
export const del = id => {
  return http.delete(
    "/sysAdminLog",
    { id },
    { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
  );
};
export const dels = ids => {
  return http.delete("/sysAdminLog/dels", ids);
};
