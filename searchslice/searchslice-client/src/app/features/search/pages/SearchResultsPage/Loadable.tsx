/**
 * Asynchronously loads the component for SearchPage
 */

import { lazyLoad } from 'utils/loadable';

export const SearchResultsPage = lazyLoad(
  () => import('./index'),
  module => module.default,
);
