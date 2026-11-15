import http from "@/api";

/**
 * @description 分页查询数据权限配置
 * @param {Object} params 查询参数（configType、configName、dataKey、分页参数）
 */
export const page = params => {
  return http.get("/sysDataScope/page", params);
};

/**
 * @description 获取数据权限配置详情
 * @param {String|Number} id 数据权限配置id
 */
export const detail = id => {
  return http.get("/sysDataScope", { id });
};

/**
 * @description 新增数据权限配置
 * @param {Object} data 数据权限配置信息
 */
export const add = data => {
  return http.post("/sysDataScope", data);
};

/**
 * @description 编辑数据权限配置
 * @param {Object} data 数据权限配置信息
 */
export const edit = data => {
  return http.put("/sysDataScope", data);
};

/**
 * @description 删除数据权限配置
 * @param {String|Number} id 数据权限配置id
 */
export const del = id => {
  return http.delete("/sysDataScope", { id }, { headers: { "Content-Type": "application/x-www-form-urlencoded" } });
};
