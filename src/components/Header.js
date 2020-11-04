import React, { useEffect, useState } from 'react';
import AppBar from '@material-ui/core/AppBar';
import Button from '@material-ui/core/Button';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import SearchIcon from '@material-ui/icons/Search';
import { fade, makeStyles } from '@material-ui/core/styles';
import InputBase from '@material-ui/core/InputBase';
import PersonPinIcon from '@material-ui/icons/PersonPin';
import IconButton from '@material-ui/core/IconButton';
import { useHistory } from "react-router-dom";

const useStyles = makeStyles((theme) => ({
  '@global': {
    ul: {
      margin: 0,
      padding: 0,
      listStyle: 'none',
    },
  },
  appBar: {
    borderBottom: `1px solid ${theme.palette.divider}`,
  },
  toolbar: {
    flexWrap: 'wrap',
  },
  toolbarTitle: {
    flexGrow: 1,
  },
  link: {
    margin: theme.spacing(1, 1.5),
    color: "white",
  },
  search: {
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: fade(theme.palette.common.white, 0.15),
    '&:hover': {
      backgroundColor: fade(theme.palette.common.white, 0.25),
    },
    marginLeft: 0,
    width: '100%',
    [theme.breakpoints.up('sm')]: {
      marginLeft: theme.spacing(1),
      width: 'auto',
    },
  },
  searchIcon: {
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  inputRoot: {
    color: 'inherit',
  },
  inputInput: {
    padding: theme.spacing(1, 1, 1, 0),
    // vertical padding + font size from searchIcon
    paddingLeft: `calc(1em + ${theme.spacing(4)}px)`,
    transition: theme.transitions.create('width'),
    width: '100%',
    [theme.breakpoints.up('sm')]: {
      width: '12ch',
      '&:focus': {
        width: '20ch',
      },
    },
    color:"white",
  },
}));

export default function Header(props) {
    const classes = useStyles();
    const [search, setSearch] = React.useState('');
    const history = useHistory();

    const handleSearch = (event) => {
      event.preventDefault();
      const data = {
        title: search,
      };
      history.push("/SearchResults", data);
    }; 

    let headerButtons;
    if (props.isLoggedIn) {
      headerButtons = (
        <Button href={`/Logout`} color="primary" variant="outlined" className={classes.link}>
          Logout
        </Button>
      );
    } else {
      headerButtons = ['Login', 'Signup'].map(buttonName => (
        <Button href={`/${buttonName}`} color="primary" variant="outlined" className={classes.link}>
          {buttonName}
        </Button>
      ));
    }

    //Scrollable Header
    const [navBackground, setNavBackground] = useState('transparent')
    const navRef = React.useRef()
    navRef.current = navBackground
    useEffect(() => {
        const handleScroll = () => {
            const show = window.scrollY > 100
            if (show) {
                setNavBackground('primary')
            } else {
                setNavBackground('transparent')
            }
        }
        document.addEventListener('scroll', handleScroll)
        return () => {
            document.removeEventListener('scroll', handleScroll)
        }
    }, [])
    
    return (
        <AppBar position="sticky" color={navRef.current} elevation={0}>
            <Toolbar className={classes.toolbar}>
                <Button href="/" color="primary" className={classes.link}>
                  FilmFinder
                </Button>
                <div className={classes.toolbarTitle}></div>
                <div className={classes.search}>
                  <div className={classes.searchIcon}>
                    <SearchIcon />
                  </div>
                    <form onSubmit={handleSearch}>
                      <InputBase
                        id="search"
                        placeholder="Search…"
                        onChange={(event) => setSearch(event.target.value)}
                        classes={{
                          root: classes.inputRoot,
                          input: classes.inputInput,
                        }}
                        inputProps={{ 'aria-label': 'search' }}
                      />
                    </form>
                </div>
                {headerButtons}
                <IconButton aria-label="account" href="/Account">
                  <PersonPinIcon style={{fill: "white"}}/>
                </IconButton>
            </Toolbar>
         </AppBar>
    );
}