import { http } from "@/utils/http";

export const tablePage = params => {
  return http.get("/gen/tablePage", params);
};
export const genToPath = data => {
  return http.post("/gen/genToPath", data);
};
export const batchGenToPath = data => {
  return http.post("/gen/batchGenToPath", data);
};
export const genToProject = data => {
  return http.post("/gen/genToProject", data);
};
export const batchGenToProject = data => {
  return http.post("/gen/batchGenToProject", data);
};

export const formInfo = params => {
  return http.get("/gen/formInfo", params);
};
export const genFormToPath = data => {
  return http.post("/gen/genFormToPath", data);
};
export const genFormToProject = data => {
  return http.post("/gen/genFormToProject", data);
};

export const page = params => {
  return http.get("/sysGenTable/page", params);
};
export const detail = id => {
  return http.get("/sysGenTable", { id });
};
export const saveGenFormRecord = data => {
  return http.post("/sysGenTable", data);
};

export const javaCode = (tableName, templateName) => {
  return http.get("/gen/javaCode", { tableName, templateName });
};

export const formCode = (data, templateName) => {
  return http.post(`/gen/formCode/${templateName}`, data);
};
export const tableFormCode = (id, templateName) => {
  return http.get("/gen/formCode", { id, templateName });
};
