import MovieCard from './components/MovieCard';
import Header from './components/Header';
import PublicReview from './components/PublicReview'
import './css/Movie.css';
import { getMovies } from './services/getMovies';

import React, { useEffect } from 'react';
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
import { useFetch, IfFulfilled, IfPending, IfRejected } from 'react-async';
import { useLocation } from "react-router-dom";


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
  }));


export default function Movie() {
    const classes = useStyles();
    const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
    const fixedHeightPaperReview = clsx(classes.paper, classes.fixedHeightReviews);
    const location = useLocation();
    const movieId = location.pathname.split('/').pop();

    const requestOptions = {
      method: 'GET',
      headers: { 'Accept': 'application/json' },
    };
  
    const movieData = useFetch(`/rest/movies/${movieId}`, requestOptions, {defer: true});
    useEffect(movieData.run, []);

    const userData = useFetch('/rest/user', requestOptions, {defer: true});
    useEffect(userData.run, []);


    const [watched, setWatched] = React.useState(false);
    const [wished, setWishlist] = React.useState(false);
    const [rating, setRating] = React.useState(0);
  
    const toggleWishlist = (event) => {
      setWishlist(wished => !wished);

    };

    const toggleWatched = (event) => {
      setWatched(watched => !watched);
    };

    const changeRating = (event, newRating) => {
      setRating(newRating);

    };

    return (
      <React.Fragment>
        <CssBaseline />
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
              
              <Container component="main" maxWidth="xl">
                <Grid container spacing={3}>
                    {/* Movie Card */}
                    <Grid item>
                        <Paper className={fixedHeightPaper}>
                          <MoviePoster movie={movie} />
                          <div className="title">
                            <FormControlLabel
                              control={<Checkbox checked={wished} onChange={toggleWishlist} icon={<FavoriteBorder />} checkedIcon={<Favorite />} name="wishlist" />}
                              label="Wishlist"
                              labelPlacement="start"
                            />
                          </div>
                        </Paper>
                    </Grid>
                    {/* Information */}
                    <Grid item xs={8} >
                        <Paper className={fixedHeightPaper}>
                            <Typography variant="h4">
                              Movie Details
                            </Typography>
                            <Typography variant="body1" paragraph={true} >
                              {movie.description}
                            </Typography>
                            <div className="right">
                                <FormControlLabel
                                  control={<Switch checked={watched} onChange={toggleWatched} name="seen" color="primary"/>}
                                  label="Seen"
                                  />
                                <ReviewButton />  
                            </div>
                        </Paper>
                    </Grid>
                    {/* Reviews */}
                    <Container component="main" maxWidth="lg">
                      <Grid item xs={12}>
                          <Paper className={fixedHeightPaperReview} variant="outlined">
                              <h1> Reviews </h1>
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

              <div className="text">
                <h1>Similar Movies</h1>
              </div>
              <Container component="main" maxWidth="lg">   
              </Container>
            </div>
          }
        </IfFulfilled>
      </React.Fragment>
    );
}

function MoviePoster(props) {
    return (
      <Card style={{width: 350, margin: 20}}>
        <CardActionArea>
          <CardMedia style={{height: 500}} image={props.movie.imageUrl}/>
          <CardContent>
            <div className='title'>
              <Box component="fieldset" mb={3} borderColor="transparent">
                <Rating name="read-only" precision={0.5} value={props.movie.rating} readOnly/>
              </Box>
              {props.movie.genres.map(genre => <Chip label={genre} style={{margin: 5}}/>)}
            </div>
          </CardContent>
        </CardActionArea>
      </Card>
    );
  }

  function ReviewButton() {
    const [open, setOpen] = React.useState(false);
    const [review, setReview] = React.useState('');
  
    const handleClickOpen = () => {
      setOpen(true);
    };
  
    const handleClose = () => {
      setOpen(false);
    };

    const handleSubmit = (event) => {
      event.preventDefault();
      const data = {
        new_review: review,
      };
    }; 
  
    return (
      <div>
        <Button variant="outlined" color="primary" onClick={handleClickOpen}>
          Leave a Review
        </Button>
        <Dialog fullwidth open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
          <form onSubmit={handleSubmit}>
            <DialogTitle id="form-dialog-title">Review</DialogTitle>
            <DialogContent>
              <DialogContentText>
                Leave a review for The Lord of the Rings: The Fellowship of the Ring below 
              </DialogContentText>
              <TextField
                autoFocus
                multiline
                rowsMax={10}
                margin="dense"
                id="review"
                label=""
                fullWidth
                onChange={(event) => setReview(event.target.value)}
              />
            </DialogContent>
            <DialogActions>
              <Button onClick={handleClose} color="primary">
                Cancel
              </Button>
              <Button onClick={handleClose} color="primary" type="submit">
                Leave Review
              </Button>
            </DialogActions>
          </form>
        </Dialog>
      </div>
    );
  }