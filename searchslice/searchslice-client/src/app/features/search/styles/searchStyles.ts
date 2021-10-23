import { makeStyles } from '@mui/styles';

export const searchStyles = makeStyles(theme => ({
  searchPaper: {
    padding: '0',
    display: 'flex',
    alignItems: 'center',
    width: 400,
    height: 35,
    margin:
      window.devicePixelRatio !== 1 ? '5px 8.25em 0px 0' : '5px 30em 0px 0',
    borderRadius: '22px',
    backgroundColor: '#092958',
  },
  iconButton: {
    padding: 10,
    marginLeft: 10,
  },
  inputBase: {
    borderRadius: '22px !important',
    marginLeft: 12,
    flex: 1,
    zIndex: 999,
    color: 'grey',
    width: '85%',
  },
  scrollDiv: {
    maxHeight: '23em',
    overflowY: 'auto',
    '&::-webkit-scrollbar': {
      width: '0.5em',
      height: '9px',
    },
    '&::-webkit-scrollbar-track': {
      boxShadow: 'inset 0 0 6px rgba(0, 0, 0, 0.3)',
      borderRadius: '8px',
    },
    '&::-webkit-scrollbar-thumb': {
      backgroundColor: '#B1B5B8',
      // outline: "1px solid slategrey",
      borderRadius: '8px',
    },
  },
  list: {
    width: '100%',
  },
  results: {
    margin: '120px 10%',
  },
  closeText: {
    cursor: 'pointer',
  },
  listItemButton: {
    '&:hover': {
      backgroundColor: 'white',
    },
  },
}));
