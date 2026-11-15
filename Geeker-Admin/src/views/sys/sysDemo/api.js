import http from "@/api";

/**
 * @description 分页查询系统示例
 * @param {Object} params 查询参数（名称、手机、性别、状态、生日、注册时间、分页参数）
 */
export const page = params => {
  return http.get("/sysDemo/page", params);
};

/**
 * @description 导出系统示例
 * @param {Object} params 查询参数
 */
export const exp = params => {
  return http.get("/sysDemo/export", params, { responseType: "blob" });
};

/**
 * @description 导入系统示例
 * @param {FormData} params 导入文件
 */
export const imp = params => {
  return http.post("/sysDemo/import", params);
};

/**
 * @description 获取系统示例详情
 * @param {String|Number} id 系统示例id
 */
export const detail = id => {
  return http.get(`/sysDemo?id=${id}`);
};

/**
 * @description 新增系统示例
 * @param {Object} params 系统示例信息
 */
export const add = params => {
  return http.post("/sysDemo", params);
};

/**
 * @description 编辑系统示例
 * @param {Object} params 系统示例信息
 */
export const edit = params => {
  return http.put("/sysDemo", params);
};

/**
 * @description 删除系统示例
 * @param {String|Number} id 系统示例id
 */
export const del = id => {
  return http.delete(`/sysDemo?id=${id}`);
};

/**
 * @description 批量删除系统示例
 * @param {Array} params 系统示例id数组
 */
export const dels = params => {
  return http.delete("/sysDemo/dels", params);
};
