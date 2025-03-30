import { http } from "@/utils/http";

export const page = params => {
  return http.get("/sysDataScope/page", params);
};
export const detail = id => {
  return http.get("/sysDataScope", { id });
};
export const add = data => {
  return http.post("/sysDataScope", data);
};
export const edit = data => {
  return http.put("/sysDataScope", data);
};
export const del = id => {
  return http.delete(
    "/sysDataScope",
    { id },
    { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
  );
};
export const dels = ids => {
  return http.delete("/sysDataScope/dels", ids);
};
