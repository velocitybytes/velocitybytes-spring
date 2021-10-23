import { call, put, takeLatest } from 'redux-saga/effects';
import { searchSliceActions } from './index';
import { PayloadAction } from '@reduxjs/toolkit';
import { axiosApi } from '../../../@app/utils/axios-utils/axios-api';

const searchApi = (
  action: PayloadAction<{ query: string; pageNum: number; pageSize: number }>,
) => {
  const { query, pageNum, pageSize } = action.payload;
  return axiosApi.get(
    `/search/keyword/${query}?pageNum=${pageNum}&pageSize=${pageSize}&title=title&summary=summary`,
  );
};

function* searchFlow(
  action: PayloadAction<{ query: string; pageNum: number; pageSize: number }>,
) {
  try {
    const response = yield call(searchApi, action);

    if (response !== undefined) {
      yield put(
        searchSliceActions.searchStatus({
          status: response.status,
          success: true,
          message: 'Fetched results',
          data: response.data.data,
        }),
      );
    }
  } catch (error) {
    yield put(
      searchSliceActions.searchStatus({
        status: error.response.status,
        success: false,
        message: 'Oops, something went wrong!',
        data: null,
      }),
    );
  }
}

function* searchWatcher() {
  yield takeLatest(searchSliceActions.search.type, searchFlow);
}

export default searchWatcher;
