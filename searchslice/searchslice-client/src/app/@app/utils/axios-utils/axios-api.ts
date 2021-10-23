import { Axios } from './axios';
import { apiConfig } from '../api';
import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from 'axios';

export class AxiosApi extends Axios {
  public constructor(config?: AxiosRequestConfig) {
    super(config);
  }

  /**
   * Generic request.
   *
   * @access public
   * @template T - `TYPE`: expected object.
   * @template R - `RESPONSE`: expected object inside a axios response format.
   * @param  [config] - axios request configuration.
   * @returns {Promise<R>} - HTTP axios response payload.
   * @memberof Api
   *
   * @example
   * api.request({
   *   method: "GET|POST|DELETE|PUT|PATCH"
   *   baseUrl: "http://www.domain.com",
   *   url: "/api/v1/users",
   *   headers: {
   *     "Content-Type": "application/json"
   *  }
   * }).then((response: AxiosResponse<User>) => response.data)
   *
   */
  public request<T, R = AxiosResponse<T>>(
    config: AxiosRequestConfig,
  ): Promise<R> {
    return axios.request(config);
  }

  /**
   * HTTP GET method, used to fetch data `statusCode`: 200.
   *
   * @access public
   * @template T - `TYPE`: expected object.
   * @template R - `RESPONSE`: expected object inside a axios response format.
   * @param {string} url - endpoint you want to reach.
   * @param [config] - axios request configuration.
   * @returns {Promise<R>} HTTP `axios` response payload.
   * @memberof Api
   */
  public get<T, R = AxiosResponse<T>>(
    url: string,
    config?: AxiosRequestConfig,
  ): Promise<R> {
    return axios.get(url, config);
  }

  /**
   * HTTP OPTIONS method.
   *
   * @access public
   * @template T - `TYPE`: expected object.
   * @template R - `RESPONSE`: expected object inside a axios response format.
   * @param {string} url - endpoint you want to reach.
   * @param [config] - axios request configuration.
   * @returns {Promise<R>} HTTP `axios` response payload.
   * @memberof Api
   */
  public options<T, R = AxiosResponse<T>>(
    url: string,
    config?: AxiosRequestConfig,
  ): Promise<R> {
    return axios.options(url, config);
  }

  /**
   * HTTP DELETE method, `statusCode`: 204 No Content.
   *
   * @access public
   * @template T - `TYPE`: expected object.
   * @template R - `RESPONSE`: expected object inside a axios response format.
   * @param {string} url - endpoint you want to reach.
   * @param [config] - axios request configuration.
   * @returns {Promise<R>} - HTTP [axios] response payload.
   * @memberof Api
   */
  public delete<T, R = AxiosResponse<T>>(
    url: string,
    config?: AxiosRequestConfig,
  ): Promise<R> {
    return axios.delete(url, config);
  }

  /**
   * HTTP HEAD method.
   *
   * @access public
   * @template T - `TYPE`: expected object.
   * @template R - `RESPONSE`: expected object inside a axios response format.
   * @param {string} url - endpoint you want to reach.
   * @param [config] - axios request configuration.
   * @returns {Promise<R>} - HTTP [axios] response payload.
   * @memberof Api
   */
  public head<T, R = AxiosResponse<T>>(
    url: string,
    config?: AxiosRequestConfig,
  ): Promise<R> {
    return axios.head(url, config);
  }

  /**
   * HTTP POST method `statusCode`: 201 Created.
   *
   * @access public
   * @template T - `TYPE`: expected object.
   * @template B - `BODY`: body request object.
   * @template R - `RESPONSE`: expected object inside a axios response format.
   * @param {string} url - endpoint you want to reach.
   * @param {B} data - payload to be send as the `request body`,
   * @param [config] - axios request configuration.
   * @returns {Promise<R>} - HTTP [axios] response payload.
   * @memberof Api
   */
  public post<T, B, R = AxiosResponse<T>>(
    url: string,
    data?: B,
    config?: AxiosRequestConfig,
  ): Promise<R> {
    return axios.post(url, data, config);
  }

  /**
   * HTTP PUT method.
   *
   * @access public
   * @template T - `TYPE`: expected object.
   * @template B - `BODY`: body request object.
   * @template R - `RESPONSE`: expected object inside a axios response format.
   * @param {string} url - endpoint you want to reach.
   * @param {B} data - payload to be send as the `request body`,
   * @param [config] - axios request configuration.
   * @returns {Promise<R>} - HTTP [axios] response payload.
   * @memberof Api
   */
  public put<T, B, R = AxiosResponse<T>>(
    url: string,
    data?: B,
    config?: AxiosRequestConfig,
  ): Promise<R> {
    return axios.put(url, data, config);
  }

  /**
   * HTTP PATCH method.
   *
   * @access public
   * @template T - `TYPE`: expected object.
   * @template B - `BODY`: body request object.
   * @template R - `RESPONSE`: expected object inside a axios response format.
   * @param {string} url - endpoint you want to reach.
   * @param {B} data - payload to be send as the `request body`,
   * @param [config] - axios request configuration.
   * @returns {Promise<R>} - HTTP [axios] response payload.
   * @memberof Api
   */
  public patch<T, B, R = AxiosResponse<T>>(
    url: string,
    data?: B,
    config?: AxiosRequestConfig,
  ): Promise<R> {
    return axios.patch(url, data, config);
  }

  /**
   *
   * @template T - type.
   * @param response - axios response.
   * @returns {T} - expected object.
   * @memberof Api
   */
  public success<T>(response: AxiosResponse<T>): T {
    console.log('Response: ', response.data);
    return response.data;
  }
  /**
   *
   *
   * @template T type.
   * @param {AxiosError<T>} error
   * @memberof Api
   */
  public error<T>(error: AxiosError<T>): void {
    throw error;
  }
}

export const axiosApi = new AxiosApi(apiConfig);
