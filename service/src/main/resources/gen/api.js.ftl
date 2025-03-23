import { http } from "@/utils/http";

export const page = params => {
  return http.get("/${beanName}/page", params);
};
export const detail = id => {
  return http.get("/${beanName}", { id });
};
export const add = data => {
  return http.post("/${beanName}", data);
};
export const edit = data => {
  return http.put("/${beanName}", data);
};
export const del = id => {
  return http.delete(
    "/${beanName}",
    { id },
    { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
  );
};
export const dels = ids => {
  return http.delete("/${beanName}/dels", ids);
};
