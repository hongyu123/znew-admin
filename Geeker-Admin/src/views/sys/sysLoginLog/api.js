import http from "@/api";

/**
 * @description 分页查询登录日志
 * @param {Object} params 查询参数
 */
export const page = params => {
  return http.get("/sysLoginLog/page", params);
};

/**
 * @description 强制用户下线
 * @param {String|Number} id 登录日志id
 * @returns {Promise}
 */
export const kickoutLoginLog = id => {
  return http.post(
    "/sysLoginLog/kickout",
    { id },
    {
      headers: { "Content-Type": "application/x-www-form-urlencoded" }
    }
  );
};
