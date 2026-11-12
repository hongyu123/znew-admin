import http from "@/api";

/**
 * @description 获取当前用户可见的组织机构树
 * @param {Object} params 查询参数（状态）
 */
export const tree = params => {
  return http.get("/sysOrganization/tree", params);
};

/**
 * @description 获取组织机构详情
 * @param {String|Number} id 组织机构id
 */
export const detail = id => {
  return http.get("/sysOrganization", { id });
};

/**
 * @description 新增组织机构
 * @param {Object} data 组织机构信息
 */
export const add = data => {
  return http.post("/sysOrganization", data);
};

/**
 * @description 编辑组织机构
 * @param {Object} data 组织机构信息
 */
export const edit = data => {
  return http.put("/sysOrganization", data);
};

/**
 * @description 删除组织机构
 * @param {String|Number} id 组织机构id
 */
export const del = id => {
  return http.delete("/sysOrganization", { id });
};
