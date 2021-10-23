import React, { forwardRef, useEffect, useMemo, useState } from 'react';
import '../../../../assets/css/search.css';
import {
  Avatar,
  CardHeader,
  IconButton,
  InputBase,
  List,
  ListItemAvatar,
  ListItemButton,
  ListItemText,
} from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { useSearchSlice } from '../../slice';
import { SearchSelector } from '../../slice/SearchSelector';
import { Skeleton } from '@mui/lab';
import { searchStyles } from '../../styles/searchStyles';

const SearchPage: React.FC = () => {
  const classes = searchStyles();

  const dispatch = useDispatch();
  const { searchSliceActions } = useSearchSlice();
  const searchResponse = useSelector(SearchSelector);

  const [searchTerm, setSearchTerm] = useState<string>('');

  useEffect(() => {
    const delayDebounceFn = setTimeout(() => {
      if (searchTerm.length > 0) {
        dispatch(
          searchSliceActions.search({
            query: searchTerm,
            pageNum: 0,
            pageSize: 5,
          }),
        );
      } else {
        dispatch(searchSliceActions.searchReset());
      }
    }, 500);
    return () => clearTimeout(delayDebounceFn);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [searchTerm]);

  const handleSearchTermChange = event => {
    setSearchTerm(event.target.value);
  };

  const getNumArray = () => {
    return [1, 2, 3, 4];
  };

  return (
    <>
      <img
        src={`https://i.redd.it/27o4b7i519i31.jpg`}
        alt="Wallpaper"
        className="search-background"
      />
      <div id="page-wrap">
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
          {searchResponse?.data &&
          searchResponse?.data?.searchList?.length > 0 ? (
            <div className={classes.scrollDiv}>
              <List className={classes.list}>
                {searchResponse?.data?.searchList?.map((searchItem, index) => (
                  <SearchItem searchItem={searchItem} key={index} />
                ))}
              </List>
            </div>
          ) : searchResponse?.loading ? (
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
                  title={<Skeleton animation="wave" height={25} width="100%" />}
                />
              ))}
            </>
          ) : null}
        </div>
      </div>
    </>
  );
};

const SearchItem = props => {
  const { searchItem } = props;

  const renderLink = useMemo(
    () =>
      forwardRef((itemProps, ref) => (
        // @ts-ignore
        <Link to={`/search?q=${searchItem.title}`} ref={ref} {...itemProps} />
      )),
    [searchItem.title],
  );

  return (
    <>
      <ListItemButton component={renderLink}>
        <ListItemAvatar>
          <Avatar />
        </ListItemAvatar>
        <ListItemText primary={<>{searchItem.title}</>} />
      </ListItemButton>
    </>
  );
};

export default SearchPage;
