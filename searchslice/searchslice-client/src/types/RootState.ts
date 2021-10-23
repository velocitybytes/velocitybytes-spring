import { Search } from '../app/features/search/slice/SearchTypes';
// [IMPORT NEW CONTAINER STATE ABOVE] < Needed for generating containers seamlessly

/*
  Because the redux-injectors injects your reducers asynchronously somewhere in your code
  You have to declare them here manually
*/

export interface RootState {
  search?: Search;
  // [INSERT NEW REDUCER KEY ABOVE] < Needed for generating containers seamlessly
}
