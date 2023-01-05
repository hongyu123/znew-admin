export interface Result {
  code: number;
  message: string;
}
export interface ApiResult<T = any> extends Result {
  data: T;
}
export interface PageResult<T = any> extends Result {
  total: number;
  data: T[];
}
export interface PageParam {
  pageNumber: number;
  pageSize: number;
}
