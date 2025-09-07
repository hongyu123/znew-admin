import { http } from "@/utils/http";

export const page = params => {
  return http.get("/sysDemo/page", params);
};
export const detail = id => {
  return http.get("/sysDemo", { id });
};
export const add = data => {
  return http.post("/sysDemo", data);
};
export const edit = data => {
  return http.put("/sysDemo", data);
};
export const del = id => {
  return http.delete(
    "/sysDemo",
    { id },
    { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
  );
};
export const dels = ids => {
  return http.delete("/sysDemo/dels", ids);
};

export const imp = data => {
  return http.post("/sysDemo/import", data);
};
export const exp = params => {
  return http.get("/sysDemo/export", params, { responseType: "blob" });
};
