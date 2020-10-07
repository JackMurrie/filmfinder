import MovieCard from './components/MovieCard';
import Header from './components/Header';
import PublicReview from './components/PublicReview'
import './css/Movie.css';
import { getMovies } from './services/getMovies';

import React from 'react';
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
import Async from 'react-async';

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

    const [state, setState] = React.useState({
      seen: false,
      wishlist: false,
    });
  
    const handleChange = (event) => {
      setState({ ...state, [event.target.name]: event.target.checked });
    };

    return (
      <React.Fragment>
        <CssBaseline />
            <Header />
            <div className="title">
                <h1>The Lord of the Rings: The Fellowship of the Ring</h1>
            </div>
            <Box className="title" component="fieldset" mb={3} borderColor="transparent">
                <Rating name="rating" precision={0.5} value={0} size="large"/>
            </Box>
            
            <Container component="main" maxWidth="xl">
                <Grid container spacing={3}>
                    {/* Movie Card */}
                    <Grid item xs={2.5}>
                        <Paper className={fixedHeightPaper}>
                            <LargeMovieCard />
                            <div className="title">
                              <FormControlLabel
                                  control={<Checkbox checked={state.wishlist} onChange={handleChange} icon={<FavoriteBorder />} checkedIcon={<Favorite />} name="wishlist" />}
                                  label="Wishlist"
                                  labelPlacement="start"
                                />
                              </div>
                        </Paper>
                    </Grid>
                    {/* Information */}
                    <Grid item xs={8} >
                        <Paper className={fixedHeightPaper}>
                            <h1> Movie Details </h1>
                            <div className={classes.flexGrow}></div>
                            <div className="right">
                                <FormControlLabel
                                  control={<Switch checked={state.seen} onChange={handleChange} name="seen" color="primary"/>}
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
                                  <PublicReview /> 
                                  <PublicReview /> 
                                  <PublicReview /> 
                                  <PublicReview /> 
                                  <PublicReview /> 
                                  <PublicReview /> 
                                  <PublicReview /> 
                                  <PublicReview />
                              </Grid>
                          </Paper>
                       </Grid>
                    </Container>
                </Grid>
            </Container>

            <div className="text">
                <h1>Similar Movies</h1>
            </div>
            {/* Display Movies */}
            <Container component="main" maxWidth="lg">   
                <Async promiseFn={getMovies}>
                <Async.Loading>Loading...</Async.Loading>
                <Async.Fulfilled>
                    {data => {
                    return(
                            <div className="container">
                              {data.movies.map((movie, index) => (
                              <MovieCard 
                              key={index} 
                              title={movie.title}
                              yearReleased={movie.yearReleased}
                              imageURL={movie.imageURL}
                              />
                              ))} 
                            </div> 
                    )
                    }}
                </Async.Fulfilled>
                <Async.Rejected>
                    Something went wrong.
                </Async.Rejected>
                </Async>
            </Container>
      </React.Fragment>
    );
}

function LargeMovieCard() {
    return (
      <Card style={{width: 350, margin: 20}}>
        <CardActionArea href="/Movie">
          <CardMedia style={{height: 500}} image="https://m.media-amazon.com/images/M/MV5BN2EyZjM3NzUtNWUzMi00MTgxLWI0NTctMzY4M2VlOTdjZWRiXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_SY999_CR0,0,673,999_AL_.jpg"/>
          <CardContent>
            <div className='title'>
                <Box component="fieldset" mb={3} borderColor="transparent">
                    <Rating name="read-only" precision={0.5} value={5} readOnly/>
                </Box>
                             
                  <Chip label={'Drama'} style={{margin: 5}}/>
                  <Chip label={'Adventure'} style={{margin: 5}}/>
                  <Chip label={'Action'} style={{margin: 5}}/>
            </div>
          </CardContent>
        </CardActionArea>
      </Card>
    );
  }

  function ReviewButton() {
    const [open, setOpen] = React.useState(false);
  
    const handleClickOpen = () => {
      setOpen(true);
    };
  
    const handleClose = () => {
      setOpen(false);
    };
  
    return (
      <div>
        <Button variant="outlined" color="primary" onClick={handleClickOpen}>
          Leave a Review
        </Button>
        <Dialog fullwidth open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
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
              id="name"
              label=""
              type="email"
              fullWidth
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose} color="primary">
              Cancel
            </Button>
            <Button onClick={handleClose} color="primary">
              Leave Review
            </Button>
          </DialogActions>
        </Dialog>
      </div>
    );
  }