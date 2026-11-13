import http from "@/api";

export const page = params => {
  return http.get("/sysDictionary/page", params);
};
export const children = (id, leafFlag) => {
  return http.get("/sysDictionary/children", { id, leafFlag });
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
  return http.delete("/sysDictionary", { id }, { headers: { "Content-Type": "application/x-www-form-urlencoded" } });
};
export const dels = ids => {
  return http.delete("/sysDictionary/dels", ids);
};

export const dict = key => {
  return http.get("/sysDictionary/dict", { key });
};
export const dptDictList = key => {
  return http.get("/sysDictionary/dptDictList", { key });
};
