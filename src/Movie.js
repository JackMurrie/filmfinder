import Header from './components/Header';
import PublicReview from './components/PublicReview'
import './css/Movie.css';

import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardMedia from '@material-ui/core/CardMedia';
import CardHeader from '@material-ui/core/CardHeader';
import CssBaseline from '@material-ui/core/CssBaseline';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { makeStyles } from '@material-ui/core/styles';
import Chip from '@material-ui/core/Chip';
import Rating from '@material-ui/lab/Rating';
import Box from '@material-ui/core/Box';
import Paper from '@material-ui/core/Paper';
import clsx from 'clsx';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Favorite from '@material-ui/icons/Favorite';
import FavoriteBorder from '@material-ui/icons/FavoriteBorder';
import Switch from '@material-ui/core/Switch';

import React, { useEffect, useState } from 'react';
import { useAsync, useFetch, IfFulfilled } from 'react-async';
import { useLocation } from 'react-router-dom';

import _ from 'lodash';


const useStyles = makeStyles((theme) => ({
  '@global': {
    ul: {
      margin: 0,
      padding: 0,
      listStyle: 'none',
    },
  },
  root: {
    display: 'flex',
    flexWrap: 'wrap',
    '& > *': {
      margin: theme.spacing(1),
      width: theme.spacing(16),
      height: theme.spacing(16),
    },
  },
  paper: {
    padding: theme.spacing(2),
    display: 'flex',
    overflow: 'auto',
    flexDirection: 'column',
  },
  fixedHeight: {
    height: 770,
  },
  fixedHeightReviews: {
    height: 500,
  },
  reviewPlace: {
    flexGrow: 1,
  },
  flexGrow: {
    flexGrow: 1,
  },
  largeIcon: {
    width: 40,
    height: 40,
  },
}));

const requestOptions = {
  method: 'GET',
  headers: { 
    'Accept': 'application/json',
    'Content-Type': 'application/json' 
  }
};

export default function Movie() {
  const classes = useStyles();
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
  const fixedHeightPaperReview = clsx(classes.paper, classes.fixedHeightReviews);
  const location = useLocation();
  const movieId = parseInt(location.pathname.split('/').pop(), 10);



  const [watched, setWatched] = useState(false);
  const [wished, setWished] = useState(false);
  const [hasReview, setHasReview] = useState(false);
  const [rating, setRating] = useState(0);

  const movieData = useFetch(`/rest/movies/${movieId}`, requestOptions, { defer: true });
  useEffect(movieData.run, []);

  const loadUserData = async () => {
    const userRequestOptions = { 
      ...requestOptions,
      method: 'POST',
      body: JSON.stringify({ limit: 10 }) 
    };
    const userDataResponse = await fetch('/rest/user', userRequestOptions);
    const { reviews, watchlist, wishlist } = await userDataResponse.json();
    const myReview = _.find(reviews, review => review.movieId === movieId);
    if (myReview) {
      setRating(myReview.rating);
      setHasReview(true);
    };

    const inWishlist = _.find(wishlist.movies, movie => movie.movieId === movieId);
    if (inWishlist) {
      setWished(true);
    };

    const inWatchlist = _.find(watchlist.movies, movie => movie.movieId === movieId);
    if (inWatchlist) {
      setWatched(true);
    };
  };
  const userData = useAsync({ deferFn: loadUserData });
  useEffect(userData.run, []);

  const updateWishlist = useFetch('/rest/user/wishlist', requestOptions, { defer: true });
  const updateWatchlist = useFetch('/rest/user/watchedlist', requestOptions, { defer: true });
  const updateRating = useFetch(`/rest/rating/${movieId}`, requestOptions, { defer: true });
  const updateReview = useFetch(`/rest/review/${movieId}`, requestOptions, { defer: true });

  const toggleWishlist = (event) => {
    if (wished) {
      updateWishlist.run({
        method: 'DELETE',
        body: JSON.stringify({ movieId: movieId })
      });
    } else {
      updateWishlist.run({
        method: 'POST',
        body: JSON.stringify({ movieId: movieId })
      });
    };

    setWished(wished => !wished);
  };

  const toggleWatched = (event) => {
    if (watched) {
      updateWatchlist.run({
        method: 'DELETE',
        body: JSON.stringify({ movieId: movieId })
      });
    } else {
      updateWatchlist.run({
        method: 'POST',
        body: JSON.stringify({ movieId: movieId })
      });
    };

    setWatched(watched => !watched);
  }

  const changeRating = (event, newRating) => {
    if (hasReview) {
      updateRating.run({
        method: 'PUT',
        body: JSON.stringify({
          rating: (newRating ? newRating : 0)
        })
      });
    } else {
      updateRating.run({
        method: 'POST',
        body: JSON.stringify({
          rating: (newRating ? newRating : 0)
        })
      });
    }

    setRating(newRating);
  };

  return (
    <React.Fragment>
      <CssBaseline />
      <div className={classes.background}>
      <Header />
      <IfFulfilled state={movieData}>
        { ({ movie, reviews }) => 
          <div>
            <div className="title">
              <h1>{movie.name}</h1>
            </div>
            <Box className="title" component="fieldset" mb={3} borderColor="transparent">
              <Rating 
              name="rating" 
              precision={0.5} 
              value={rating} 
              size="large" 
              onChange={changeRating}/>
            </Box>
            <Container component="main" maxWidth="lg">
              <Grid container spacing={3}>
                {/* Movie Card */}
                <Grid item>
                  <Paper className={fixedHeightPaper}>
                    <MoviePoster movieGenreList={movie.genres} movieImageUrl={movie.imageUrl} movieRating={movie.averageRating} />
                    <div className="title">
                      <FormControlLabel
                        control={<Checkbox checked={wished} onChange={toggleWishlist} icon={<FavoriteBorder className={classes.largeIcon}/>} checkedIcon={<Favorite className={classes.largeIcon}/>} name="wishlist" />}
                      />
                      <FormControlLabel
                        control={<Switch checked={watched} onChange={toggleWatched} name="seen" color="primary"/>}
                        label="Seen"
                      />
                    </div>
                  </Paper>
                </Grid>
                {/* Information */}
                <Grid item xs={7} >
                  <Paper className={fixedHeightPaper}>
                  <div className="heading">
                    Movie Details
                  </div>
                  <div className="text">
                    {movie.description}
                  </div>
                    <div className="right">
                      <ReviewButton movieId={movieId} hasReview={hasReview} updateReview={updateReview} reloadMovieData={movieData} />  
                    </div>
                  </Paper>
                </Grid>
                {/* Reviews */}
                <Container component="main" maxWidth="md">
                  <Grid item xs={12}>
                    <Paper className={fixedHeightPaperReview} variant="outlined">
                        <Grid container spacing={1}>
                          {reviews.map(({ comment, rating, post_date, userId }) => 
                            <PublicReview text={comment} rating={rating} postDate={post_date} user={userId} />
                          )}
                        </Grid>
                    </Paper>
                  </Grid>
                </Container>
              </Grid>
            </Container>

            <div className="title">
              <h1>Similar Movies</h1>
            </div>
            <Container component="main" maxWidth="lg">   
            </Container>
          </div>
        }
      </IfFulfilled>
      </div>
    </React.Fragment>
  );
}

function MoviePoster(props) {
  return (
    <Card style={{width: 350, margin: 20, backgroundColor: "#282828"}}>
      <CardActionArea>
        <CardMedia style={{height: 480}} image={props.movieImageUrl}/>
        <CardContent>
          <div className='title'>
            <Box component="fieldset" mb={-1} borderColor="transparent">
              <Rating name="read-only" precision={0.5} value={props.movieRating} readOnly/>
            </Box>
            {props.movieGenreList.map(genre => <Chip label={genre} style={{margin: 5}}/>)}
          </div>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}

function ReviewButton(props) {
  const [open, setOpen] = useState(false);
  const [newComment, setNewComment] = useState('');

  const openReviewDialogBox = () => {
    setOpen(true);
  };

  const closeReviewDialogBox = () => {
    setOpen(false);
  };

  const submitReview = (event) => {
    event.preventDefault();
    if (props.hasReview) {
      props.updateReview.run({
        method: 'PUT',
        body: JSON.stringify({
          comment: newComment,
        })
      });
    } else {
      props.updateReview.run({
        method: 'POST',
        body: JSON.stringify({
          comment: newComment,
        })
      });
    };

    props.reloadMovieData.run();
  };

  return (
    <div>
      <Button variant="outlined" color="white" onClick={openReviewDialogBox}>
        Leave a Review
      </Button>
      <Dialog fullwidth open={open} onClose={closeReviewDialogBox} aria-labelledby="form-dialog-title">
        <form onSubmit={submitReview}>
          <DialogTitle id="form-dialog-title">Review</DialogTitle>
          <DialogContent>
            <DialogContentText>
              Enter your review here...
            </DialogContentText>
            <TextField
              autoFocus
              multiline
              rowsMax={10}
              margin="dense"
              id="review"
              label=""
              fullWidth
              onChange={(event) => setNewComment(event.target.value)}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={closeReviewDialogBox} color="primary">
              Cancel
            </Button>
            <Button onClick={closeReviewDialogBox} color="primary" type="submit">
              Leave Review
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    </div>
  );
}