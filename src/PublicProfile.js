import Header from './components/Header';
import PublicReview from './components/PublicReview'

import React, { useEffect, useState } from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Switch from '@material-ui/core/Switch';
import clsx from 'clsx';
import PropTypes from 'prop-types';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import FavoriteIcon from '@material-ui/icons/Favorite';
import VisibilityIcon from '@material-ui/icons/Visibility';
import RateReviewIcon from '@material-ui/icons/RateReview';
import Box from '@material-ui/core/Box';
import { useLocation } from 'react-router-dom';
import { useTheme } from '@material-ui/core/styles';
import { IfFulfilled, useAsync, useFetch } from 'react-async';
import MovieCard from './components/MovieCard';
import _ from 'lodash';

const useStyles = makeStyles((theme) => ({
    '@global': {
      ul: {
        margin: 0,
        padding: 0,
        listStyle: 'none',
      },
    },
      paper: {
        padding: theme.spacing(2),
        display: 'flex',
        overflow: 'auto',
        flexDirection: 'column',
      },
      fixedHeight: {
        height: 500,
      },
      flexGrow: {
        flexGrow: 1,
      },
  }));

export default function PublicProfile(props) {
    const classes = useStyles();
    const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
    const location = useLocation();
    const userId = parseInt(location.pathname.split('/').pop(), 10);
    const theme = useTheme();

    const requestOptions = {
      method: 'GET',
      headers: { 
        'Accept': 'application/json',
        'Content-Type': 'application/json' 
      }
    };
  
    const loadUserData = async () => {
      const userRequestOptions = { 
        ...requestOptions,
        method: 'GET',
      };
      const userDataResponse = await fetch(`/rest/user/${userId}`, userRequestOptions);

      const data = await userDataResponse.json();

      const inBlacklist = _.find(data.blacklisted.users, user => user.userId === userId);
      if (inBlacklist) {
        setBlacklist(true);
      };

      return data;
    };
  
    const userData = useAsync({ deferFn: loadUserData });
    useEffect(userData.run, []);
  
    const updateBlacklist = useFetch('/rest/user/blacklist', requestOptions, { defer: true });

    const [blacklist, setBlacklist] = useState(false);

    const toggleBlacklist = (event) => {
        if (blacklist) {
        updateBlacklist.run({
          method: 'DELETE',
          body: JSON.stringify({ userId: userId })
        });
      } else {
        updateBlacklist.run({
          method: 'POST',
          body: JSON.stringify({ userId: userId })
        });
      };
      setBlacklist(blacklisted => !blacklisted);
    };
  
    return (
        <React.Fragment>
        <CssBaseline />
        <Header isLoggedIn={props.loggedIn} handleLogout={props.handleLogout}/>
        <IfFulfilled state={userData}>
          {({ userInfo, wishlist, watchlist, reviews }) =>
            <Container component="main" maxWidth="lg">
                <Grid container spacing={3}>
                    {/* Profile Picture */}
                    <Grid item xs={4.5}>
                        <Paper className={fixedHeightPaper}>
                            <Card style={{width: 350, margin: 20}}>
                                <CardMedia style={{height: 400}} image={userInfo.profilePicUrl}/>
                                <CardContent>
                                </CardContent>
                            </Card>
                            <div className="title">
                              {`${userInfo.first} ${userInfo.last}`}
                            </div>
                        </Paper>
                    </Grid>
                    {/* Bio */}
                    <Grid item xs={7} >
                        <Paper className={fixedHeightPaper}>
                            <Typography>
                                TODO: Add user description
                            </Typography>
                            <div className={classes.flexGrow}></div>
                            <div className="right">
                                <FormControlLabel
                                  control={<Switch checked={blacklist} onChange={toggleBlacklist} name="blacklist" color="primary"/>}
                                  label="Blacklist"
                                />
                            </div>
                        </Paper>
                    </Grid>
                    <Grid item xs={12}>
                        <PublicDashboard wishlist={wishlist} watchlist={watchlist} reviews={reviews}/>
                    </Grid>
                </Grid>
            </Container>
          }
        </IfFulfilled>
        </React.Fragment>
    )
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

function PublicDashboard(props) {
    const classes = useStyles();
    const [value, setValue] = React.useState(0);
  
    const toggleTab = (event, newValue) => {
      setValue(newValue);
    };


    const { wishlist, watchlist, reviews } = props;

    const Wishlist = wishlist.movies.map(({ movieId, name, year, imageUrl, averageRating }) => {
      return <MovieCard key={movieId} movieId={movieId} title={name} yearReleased={year} imageUrl={imageUrl} rating={averageRating} />;
    });
  
    const Watchlist = watchlist.movies.map(({ movieId, name, year, imageUrl, averageRating }) => {
      return <MovieCard key={movieId} movieId={movieId} title={name} yearReleased={year} imageUrl={imageUrl} rating={averageRating} />;
    });
    
    const Reviews = reviews.map(({ movieName, movieId, comment, rating, post_date, user }) => {
      return <PublicReview 
        title={movieName}
        text={comment}
        rating={rating}
        postDate={post_date}
        user={user.userId}
        movieId={movieId}
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
          <Tab label="Wishlist" icon={<FavoriteIcon />}/>
          <Tab label="Seen" icon={<VisibilityIcon />}/>
          <Tab label="My Reviews" icon={<RateReviewIcon />}/>
          
        </Tabs>
      </AppBar>
      <TabPanel value={value} index={0}>
        <div className="container">
          {Wishlist}
        </div>
      </TabPanel>
      <TabPanel value={value} index={1}>
        <div className="container">
          {Watchlist}
        </div>
      </TabPanel>
      <TabPanel value={value} index={2}>
        {Reviews}
      </TabPanel>
    </div>
    );
  }