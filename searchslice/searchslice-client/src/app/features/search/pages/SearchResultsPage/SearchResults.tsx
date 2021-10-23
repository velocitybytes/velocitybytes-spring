import React from 'react';
import { useSelector } from 'react-redux';
import { SearchSelector } from '../../slice/SearchSelector';
import {
  Avatar,
  List,
  ListItemAvatar,
  ListItemButton,
  ListItemText,
  Typography,
} from '@mui/material';
import { searchStyles } from '../../styles/searchStyles';

const SearchResults = () => {
  const classes = searchStyles();
  const searchResponse = useSelector(SearchSelector);

  return (
    <>
      <div>
        <Typography variant="h6">
          {searchResponse?.data
            ? `Showing 6 of  ${searchResponse?.data?.searchCount} results`
            : null}
        </Typography>
        <List>
          {searchResponse?.data?.searchList?.map((searchItem, index) => (
            <SearchItem searchItem={searchItem} key={index} classes={classes} />
          ))}
        </List>
      </div>
    </>
  );
};

const SearchItem = props => {
  const { classes, searchItem } = props;

  return (
    <>
      <ListItemButton className={classes.listItemButton}>
        <ListItemAvatar>
          <Avatar />
        </ListItemAvatar>
        <ListItemText
          primary={
            <>
              <span style={{ color: 'grey' }}>
                {searchItem.url.substring(0, 65) + '...'}
              </span>
              <Typography variant="body1" style={{ color: 'skyblue' }}>
                {searchItem.title}
              </Typography>
            </>
          }
          secondary={
            <span
              style={{
                color: 'black',
              }}
            >
              {searchItem.summary}
            </span>
          }
        />
      </ListItemButton>
    </>
  );
};

export default SearchResults;
