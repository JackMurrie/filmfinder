import Header from './components/Header';
import MovieCard from './components/MovieCard';
import WishlistItem from './components/WishlistItem';
import WatchlistItem from './components/WatchlistItem';
import PrivateReview from './components/PrivateReview';
import Footer from './components/Footer';
import './css/Account.css';

import React, { useEffect } from 'react';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import LockIcon from '@material-ui/icons/Lock';
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
import Divider from '@material-ui/core/Divider';
import Container from '@material-ui/core/Container';
import Backdrop from '@material-ui/core/Backdrop';
import CircularProgress from '@material-ui/core/CircularProgress';
import { useFetch, IfFulfilled, IfPending, IfRejected } from 'react-async';
import { useHistory } from 'react-router-dom';

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
  },
  right: {
    textAlign: "right",
  },
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: '#fff',
  },
  image: {
    backgroundImage: "url(https://images.unsplash.com/photo-1521967906867-14ec9d64bee8?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80)",
    backgroundPosition: 'center',
    backgroundSize: 'cover',
    textAlign: "center",
    height: 400,
  },
  container: {
    display: "flex",
    flexWrap: "wrap",
    marginLeft: "10%",
  },
  centerText: {
    textAlign: "center",
    fontFamily: ["Montserrat", "sans-serif"],
  },
}));
  
export default function Account(props) {
  const classes = useStyles();

  const requestOptions = {
    method: 'POST',
    headers: { 
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ limit: 10 })
  };

  const fetchDashboardData = useFetch('/rest/user/dashboard', requestOptions, {defer: true});
  useEffect(fetchDashboardData.run, []);

  return (
    <React.Fragment>
      <CssBaseline />
      <IfFulfilled state={fetchDashboardData}>
        { dashboardData => (
          <React.Fragment>
            <div className={classes.image}>
              <Header isLoggedIn={props.loggedIn} handleLogout={props.handleLogout}/>
              </div>
            <Container component="main" maxWidth="lg">
              <Dashboard dashboardData={dashboardData} reloadDashboardData={fetchDashboardData.run}/>
            </Container>
            <Footer />
          </React.Fragment>
        )}
      </IfFulfilled>
      <IfPending state={fetchDashboardData}>
        <Backdrop className={classes.backdrop} open={true}>
          <CircularProgress color="inherit" />
        </Backdrop>
      </IfPending>
      <IfRejected state={fetchDashboardData}>
        <React.Fragment>
           <div className={classes.image}>
              <Header isLoggedIn={props.loggedIn} handleLogout={props.handleLogout}/>
            </div>
            <div className={classes.centerText}>
              <h1>Log in to view your account </h1>
            </div>
        </React.Fragment>
      </IfRejected>
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
  const history = useHistory();

  const toggleTab = (event, newValue) => {
    setValue(newValue);
  };

  const handlePassReset = (event) => {
    const data = {
      email: null,
    };

    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    };

    fetch('/rest/auth/request_reset', requestOptions)
      .then(response => {
        if (response.ok) {
          history.push('/ResetPassword', data);
        } 
      });
  };

  const { wishlist, watchlist, recommendations, reviews } = props.dashboardData;

  const Wishlist = wishlist.movies.map(({ movieId, name, year, imageUrl }) => {
    return <WishlistItem key={movieId} movieId={movieId} title={name} yearReleased={year} imageUrl={imageUrl}/>;
  });

  const Watchlist = watchlist.movies.map(({ movieId, name, year, imageUrl }) => {
    return <WatchlistItem key={movieId} movieId={movieId} title={name} yearReleased={year} imageUrl={imageUrl}/>;
  });

  const Recommendations = recommendations.movies.map(({ movieId, name, year, imageUrl, averageRating }) => {
    return <MovieCard key={movieId} movieId={movieId} title={name} yearReleased={year} imageUrl={imageUrl} rating={averageRating} />;
  });

  const Reviews = reviews.map(({ movieName, movieId, comment, rating, post_date, user }) => {


    return <PrivateReview 
      title={movieName}
      text={comment}
      rating={rating}
      postDate={post_date}
      user={user.userId}
      movieId={movieId}
      onChange={props.reloadDashboardData}
    />;
  });


  return (
    <div className={classes.root}>
      <AppBar position="static" color="default">
        <Tabs
          value={value}
          onChange={toggleTab}
          variant="fullWidth"
          indicatorColor="secondary"
          textColor="secondary"
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
        <div className={classes.container}>
          {Recommendations}
        </div>
      </TabPanel>
      <TabPanel value={value} index={2}>
        {Watchlist}
      </TabPanel>
      <TabPanel value={value} index={3}>
        {Reviews}
      </TabPanel>
      <TabPanel value={value} index={4}>
        Delete Account 
        <IconButton color="secondary" component="span">
              <DeleteIcon />
        </IconButton>
        <Divider />
        Reset Password
        <IconButton color="primary" component="span" onClick={handlePassReset}> 
              <LockIcon />
        </IconButton>
        <Divider />
      </TabPanel>
    </div>
  );
}