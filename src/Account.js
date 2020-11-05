import Header from './components/Header';
import MovieCard from './components/MovieCard';
import WishlistItem from './components/WishlistItem';
import WatchlistItem from './components/WatchlistItem';
import PrivateReview from './components/PrivateReview';

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
    backgroundColor: "#282828", //theme.palette.background.paper,
  },
  right: {
    textAlign: "right",
  },
  background: {
    backgroundColor: "#282828",
    height: "1000px",
    overflow: "auto",
  },
}));
  
export default function Account() {
  const classes = useStyles();

  const requestOptions = {
    method: 'GET',
    headers: { 'Accept': 'application/json' },
  };

  const fetchDashboardData = useFetch('/rest/user', requestOptions, {defer: true});
  useEffect(fetchDashboardData.run, []);
  
  return (
    <React.Fragment>
      <CssBaseline />
      <div className={classes.background}>
      <IfFulfilled state={fetchDashboardData}>
        { dashboardData => (
          <React.Fragment>
            <Header isLoggedIn={true}/>
            <header className="Account-header">
            </header>
            <Container component="main" maxWidth="lg">
              <Dashboard dashboardData={dashboardData}/>
            </Container>
          </React.Fragment>
        )}
      </IfFulfilled>
      <IfPending state={fetchDashboardData}>
        {/* TODO: Put loading screen elements here */}
        Loading...
      </IfPending>
      <IfRejected state={fetchDashboardData}>
        <React.Fragment>
          <Header isLoggedIn={false}/>
          {/* TODO: Page to show/ go to if something went wrong or you're not logged in yet */}
        </React.Fragment>
      </IfRejected>
      </div>
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

function Dashboard(props) {
  const classes = useStyles();
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const { wishlist, watchlist, recommendations, reviews } = props.dashboardData;

    const Wishlist = wishlist.movies.map(({ movieId, name, year, imageUrl }) => {
      return <WishlistItem key={movieId} movieId={movieId} title={name} yearReleased={year} imageUrl={imageUrl}/>;
    });

    const Watchlist = watchlist.movies.map(({ movieId, name, year, imageUrl }) => {
      return <WatchlistItem key={movieId} movieId={movieId} title={name} yearReleased={year} imageUrl={imageUrl}/>;
    });

    const Recommendations = recommendations.movies.map(({ movieId, name, year, imageUrl }) => {
      return <MovieCard key={movieId} movieId={movieId} title={name} yearReleased={year} imageUrl={imageUrl}/>;
    });

    const Reviews = reviews.map(({ movieName, movieId, comment, rating, post_date, userId }) => {
      return <PrivateReview title={movieName} text={comment} rating={rating} postDate={post_date} user={userId} movieId={movieId}/>;
    });


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
        {Wishlist}
      </TabPanel>
      <TabPanel value={value} index={1}>
        {Recommendations}
      </TabPanel>
      <TabPanel value={value} index={2}>
        {Watchlist}
      </TabPanel>
      <TabPanel value={value} index={3}>
        {Reviews}
      </TabPanel>
      <TabPanel value={value} index={4}>
        Account Settings
      </TabPanel>
    </div>
  );
}