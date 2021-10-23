import { Search } from './SearchTypes';
import { createSlice } from 'utils/@reduxjs/toolkit';
import { PayloadAction } from '@reduxjs/toolkit';
import { useInjectReducer, useInjectSaga } from 'utils/redux-injectors';
import searchWatcher from './SearchSaga';

const name = 'search';

export const initialState: Search = {
  query: null,
  pageNum: 0,
  pageSize: 5,
  loading: false,
  status: null,
  success: null,
  message: null,
  data: null,
};

const searchSlice = createSlice({
  name,
  initialState,
  reducers: {
    search: (
      state,
      action: PayloadAction<{
        query: string;
        pageNum: number;
        pageSize: number;
      }>,
    ) => {
      state.loading = true;
      state.query = action.payload.query;
      state.pageNum = action.payload.pageNum;
      state.pageSize = action.payload.pageSize;
    },
    searchStatus: (
      state,
      action: PayloadAction<{
        status: number;
        success: boolean;
        message: string;
        data: any;
      }>,
    ) => {
      state.loading = false;
      state.status = action.payload.status;
      state.success = action.payload.success;
      state.message = action.payload.message;
      state.data = action.payload.data;
    },
    searchReset: () => {
      return initialState;
    },
  },
});

export const { actions: searchSliceActions, reducer } = searchSlice;

export const useSearchSlice = () => {
  useInjectReducer({ key: searchSlice.name, reducer: searchSlice.reducer });
  useInjectSaga({ key: searchSlice.name, saga: searchWatcher });
  return { searchSliceActions: searchSlice.actions };
};
