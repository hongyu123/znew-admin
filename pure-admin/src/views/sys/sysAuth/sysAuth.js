import { http } from "@/utils/http";

export const detail = id => {
  return http.get("/sysAuth", { id });
};
export const add = data => {
  return http.post("/sysAuth", data);
};
export const edit = data => {
  return http.put("/sysAuth", data);
};
export const del = id => {
  return http.delete(
    "/sysAuth",
    { id },
    { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
  );
};
export const dels = ids => {
  return http.delete("/sysAuth/dels", ids);
};

export const tree = state => {
  return http.get("/sysAuth/tree", { state });
};
