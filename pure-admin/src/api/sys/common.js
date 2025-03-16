import { http } from "@/utils/http";

export const enums = (code, filter, type) => {
  return http.get("/common/enums", { code, filter, type });
};

export const upload = data => {
  return http.post("/common/upload", data, {
    headers: {
      "Content-Type": "multipart/form-data"
    }
  });
};
