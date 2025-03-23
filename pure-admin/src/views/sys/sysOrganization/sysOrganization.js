import { http } from "@/utils/http";

export const page = params => {
  return http.get("/sysOrganization/page", params);
};
export const detail = id => {
  return http.get("/sysOrganization", { id });
};
export const add = data => {
  return http.post("/sysOrganization", data);
};
export const edit = data => {
  return http.put("/sysOrganization", data);
};
export const del = id => {
  return http.delete(
    "/sysOrganization",
    { id },
    { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
  );
};
export const dels = ids => {
  return http.delete("/sysOrganization/dels", ids);
};

export const tree = state => {
  return http.get("/sysOrganization/tree", { state });
};
