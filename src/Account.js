import Header from './components/Header';
import MovieCard from './components/MovieCard';
import './css/Account.css';

import React, { useEffect } from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import { makeStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import FavoriteIcon from '@material-ui/icons/Favorite';
import PersonPinIcon from '@material-ui/icons/PersonPin';
import VisibilityIcon from '@material-ui/icons/Visibility';
import ThumbUp from '@material-ui/icons/ThumbUp';
import RateReviewIcon from '@material-ui/icons/RateReview';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import Container from '@material-ui/core/Container';
import { useFetch, IfFulfilled, IfPending, IfRejected } from 'react-async';

const useStyles = makeStyles((theme) => ({
  '@global': {
    ul: {
      margin: 0,
      padding: 0,
      listStyle: 'none',
    },
  },
  root: {
      flexGrow: 1,
      width: '100%',
      backgroundColor: theme.palette.background.paper,
  },
  right: {
    textAlign: "right",
  }  
}));
  
export default function Account() {
    const classes = useStyles();
  
    return (
        <React.Fragment>
        <CssBaseline />
            <Header />
            <header className="Account-header">
                <h1>Welcome User</h1>
            </header>
        <Container component="main" maxWidth="lg">
            <TabButtons />
        </Container>

        </React.Fragment>
    );
}

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`scrollable-force-tabpanel-${index}`}
      aria-labelledby={`scrollable-force-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={3}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
  };

function a11yProps(index) {
    return {
        id: `full-width-tab-${index}`,
        'aria-controls': `full-width-tabpanel-${index}`,
    };
}

function TabButtons() {
    const classes = useStyles();
    const [value, setValue] = React.useState(0);
  
    const handleChange = (event, newValue) => {
      setValue(newValue);
    };

    const requestOptions = {
      method: 'GET',
      headers: { 'Accept': 'application/json' },
    };
  
    const dashboardData = useFetch('/rest/user', requestOptions, {defer: true});
    useEffect(dashboardData.run, []);

    const displayWishlist = ({ wishlist }) => {
      const componentWishlist = wishlist.movies.map(({ movieId, name, year, imageUrl }) => {
        return <MovieCard key={movieId} title={name} yearReleased={year} imageURL={imageUrl}/>;
      });
      return componentWishlist;
    };
  
    const displayWatchlist = ({ watchlist }) => {
      const componentWatchlist = watchlist.movies.map(({ movieId, name, year, imageUrl }) => {
        return <MovieCard key={movieId} title={name} yearReleased={year} imageURL={imageUrl}/>;
      });
      return componentWatchlist;
    };

    const displayRecommendations = ({ recommendations }) => {
      const componentRecommendations = recommendations.movies.map(({ movieId, name, year, imageUrl }) => {
        return <MovieCard key={movieId} title={name} yearReleased={year} imageURL={imageUrl}/>;
      });
      return componentRecommendations;
    };

    return (
      <div className={classes.root}>
        <AppBar position="static" color="default">
          <Tabs
            value={value}
            onChange={handleChange}
            variant="fullWidth"
            indicatorColor="primary"
            textColor="primary"
            aria-label="Buton Tabs"
          >
            <Tab label="Wishlist" icon={<FavoriteIcon />} {...a11yProps(1)} />
            <Tab label="Recommended" icon={<ThumbUp />} {...a11yProps(2)} />
            <Tab label="Seen" icon={<VisibilityIcon />} {...a11yProps(3)} />
            <Tab label="My Reviews" icon={<RateReviewIcon />} {...a11yProps(4)} />
            <Tab label="Account Settings" icon={<PersonPinIcon />} {...a11yProps(5)} />
            
          </Tabs>
        </AppBar>
        <TabPanel value={value} index={0}>
          <IfRejected state={dashboardData}>No results found</IfRejected>
          <IfPending state={dashboardData}>Loading...</IfPending>
          <IfFulfilled state={dashboardData}>{displayWishlist}</IfFulfilled>
        </TabPanel>
        <TabPanel value={value} index={1}>
          <IfRejected state={dashboardData}>No results found</IfRejected>
          <IfPending state={dashboardData}>Loading...</IfPending>
          <IfFulfilled state={dashboardData}>{displayRecommendations}</IfFulfilled>
        </TabPanel>
        <TabPanel value={value} index={2}>
          <IfRejected state={dashboardData}>No results found</IfRejected>
          <IfPending state={dashboardData}>Loading...</IfPending>
          <IfFulfilled state={dashboardData}>{displayWatchlist}</IfFulfilled>
        </TabPanel>
        <TabPanel value={value} index={3}>
          My Reviews
        </TabPanel>
        <TabPanel value={value} index={4}>
          Account Settings
        </TabPanel>
      </div>
    );
  }