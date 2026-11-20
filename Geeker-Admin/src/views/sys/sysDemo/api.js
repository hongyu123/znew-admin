import http from "@/api";

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
  return http.delete("/sysDemo", { id }, { headers: { "Content-Type": "application/x-www-form-urlencoded" } });
};
export const dels = ids => {
  return http.delete("/sysDemo/dels", ids);
};

export const exp = params => {
  return http.get("/sysDemo/export", params, { responseType: "blob" });
};
export const imp = params => {
  return http.post("/sysDemo/import", params);
};
