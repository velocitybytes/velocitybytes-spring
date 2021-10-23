/**
 * Asynchronously loads the component for SearchPage
 */

import { lazyLoad } from 'utils/loadable';

export const SearchPage = lazyLoad(
  () => import('./index'),
  module => module.default,
);
