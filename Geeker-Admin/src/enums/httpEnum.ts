/**
 * @description：请求配置
 */
export enum ResultEnum {
  // 后端 Result 约定：code=1 成功、code=0 失败
  SUCCESS = 1,
  ERROR = 0,
  // 登录失效（后端 ErrorCode.NO_LOGIN）
  OVERDUE = 100,
  TIMEOUT = 30000,
  TYPE = "success"
}

/**
 * @description：请求方法
 */
export enum RequestEnum {
  GET = "GET",
  POST = "POST",
  PATCH = "PATCH",
  PUT = "PUT",
  DELETE = "DELETE"
}

/**
 * @description：常用的 contentTyp 类型
 */
export enum ContentTypeEnum {
  // json
  JSON = "application/json;charset=UTF-8",
  // text
  TEXT = "text/plain;charset=UTF-8",
  // form-data 一般配合qs
  FORM_URLENCODED = "application/x-www-form-urlencoded;charset=UTF-8",
  // form-data 上传
  FORM_DATA = "multipart/form-data;charset=UTF-8"
}
