import React, { forwardRef, useEffect, useMemo, useState } from 'react';
import '../../../../assets/css/search.css';
import {
  Avatar,
  CardHeader,
  IconButton,
  InputBase,
  List,
  ListItem,
  ListItemAvatar,
  ListItemButton,
  ListItemText,
} from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import { useSearchSlice } from '../../slice';
import { useDispatch, useSelector } from 'react-redux';
import { SearchSelector } from '../../slice/SearchSelector';
import { Skeleton } from '@mui/lab';
import { Link } from 'react-router-dom';
import { Grid } from '@mui/material';
import SearchResults from './SearchResults';
import { searchStyles } from '../../styles/searchStyles';

const SearchResultsPage = props => {
  const query = new URLSearchParams(props.location.search);

  const keyword = query.get('q');

  const classes = searchStyles();

  const dispatch = useDispatch();
  const searchResponse = useSelector(SearchSelector);
  const { searchSliceActions } = useSearchSlice();

  const [searchTerm, setSearchTerm] = useState<string | any>(keyword);
  const [searchTermClicked, setSearchTermClicked] = useState<boolean>(false);

  const handleSearchTermChange = event => {
    setSearchTerm(event.target.value);
  };

  const getNumArray = () => {
    return [1, 2, 3, 4];
  };

  useEffect(() => {
    if (searchTerm && searchTerm.length === 0) {
      dispatch(searchSliceActions.searchReset());
    }
    const delayDebounceFn = setTimeout(() => {
      if (searchTerm.length > 0) {
        setSearchTermClicked(true);
        dispatch(
          searchSliceActions.search({
            query: searchTerm,
            pageNum: 0,
            pageSize: 6,
          }),
        );
      } else {
        dispatch(searchSliceActions.searchReset());
      }
    }, 500);
    return () => clearTimeout(delayDebounceFn);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [searchTerm]);

  return (
    <>
      <div className="search-results-background">
        <div id="results-page-wrap">
          <div>
            <IconButton className={classes.iconButton}>
              <SearchIcon />
            </IconButton>
            <InputBase
              autoFocus
              className={classes.inputBase}
              placeholder="Search..."
              value={searchTerm}
              onChange={handleSearchTermChange}
            />
            {searchTermClicked &&
            searchResponse?.data &&
            searchResponse?.data?.searchList?.length > 0 ? (
              <div className={classes.scrollDiv}>
                <List className={classes.list}>
                  {searchResponse?.data?.searchList
                    ?.slice(0, 5)
                    .map((searchItem, index) => (
                      <SearchItem
                        searchItem={searchItem}
                        key={index}
                        setSearchTermClicked={setSearchTermClicked}
                      />
                    ))}
                  <ListItem
                    secondaryAction={
                      <>
                        <span
                          className={classes.closeText}
                          onClick={() => setSearchTermClicked(false)}
                        >
                          Close
                        </span>
                      </>
                    }
                  />
                </List>
              </div>
            ) : searchTermClicked && searchResponse?.loading ? (
              <>
                {getNumArray().map((item, index) => (
                  <CardHeader
                    key={index}
                    avatar={
                      <Skeleton
                        animation="wave"
                        variant="rectangular"
                        width={40}
                        height={35}
                        style={{ borderRadius: 5 }}
                      />
                    }
                    title={
                      <Skeleton animation="wave" height={25} width="100%" />
                    }
                  />
                ))}
              </>
            ) : null}
          </div>
        </div>
        <div className={classes.results}>
          <Grid container>
            <Grid xs={8}>
              <SearchResults />
            </Grid>
            <Grid item xs>
              result view
            </Grid>
          </Grid>
        </div>
      </div>
    </>
  );
};

const SearchItem = props => {
  const { searchItem, setSearchTermClicked } = props;

  const renderLink = useMemo(
    () =>
      forwardRef((itemProps, ref) => (
        <Link
          to={`/search?q=${searchItem.title}`}
          // @ts-ignore
          ref={ref}
          {...itemProps}
          onClick={() => setSearchTermClicked(false)}
        />
      )),
    [searchItem.title, setSearchTermClicked],
  );

  return (
    <>
      <ListItemButton component={renderLink}>
        <ListItemAvatar>
          <Avatar />
        </ListItemAvatar>
        <ListItemText
          primary={
            <>
              <span>{searchItem.title}</span>
            </>
          }
        />
      </ListItemButton>
    </>
  );
};

export default SearchResultsPage;
