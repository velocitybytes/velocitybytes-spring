import * as React from 'react';
import { Helmet } from 'react-helmet-async';
import { SearchPage } from '../../features/search/pages/SearchPage/Loadable';

export function HomePage() {
  return (
    <>
      <Helmet>
        <title>Home Page</title>
        <meta name="description" content="A Boilerplate application homepage" />
      </Helmet>
      <SearchPage />
    </>
  );
}
