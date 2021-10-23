import * as qs from 'qs';
import { PathLike } from 'fs';

export const apiConfig = {
  // returnRejectedPromiseOnError: true,
  // withCredentials: true,
  // timeout: 30000,
  baseURL: 'http://localhost:8080/api/v1',
  /*headers: {
    common: {
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE',
      'Access-Control-Allow-Headers': 'Origin, Content-Type, X-Auth-Token',
      'Cache-Control': 'no-cache, no-store, must-revalidate',
      Pragma: 'no-cache',
      'Content-Type': 'application/json',
      Accept: 'application/json',
    },
  },*/
  paramsSerializer: (params: PathLike) =>
    qs.stringify(params, { indices: false }),
};
