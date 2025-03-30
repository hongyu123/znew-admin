import { http } from "@/utils/http";

export const page = params => {
  return http.get("/sysDictionary/page", params);
};
export const children = id => {
  return http.get("/sysDictionary/children", { id });
};
export const detail = id => {
  return http.get("/sysDictionary", { id });
};
export const add = data => {
  return http.post("/sysDictionary", data);
};
export const edit = data => {
  return http.put("/sysDictionary", data);
};
export const del = id => {
  return http.delete(
    "/sysDictionary",
    { id },
    { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
  );
};
export const dels = ids => {
  return http.delete("/sysDictionary/dels", ids);
};
