import './css/Home.css';
import Header from './components/Header';
import MovieCard from './components/MovieCard';
import { getMovies } from './services/getMovies';

import React from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { fade, makeStyles } from '@material-ui/core/styles';
import Async from 'react-async';

const useStyles = makeStyles((theme) => ({
  '@global': {
    ul: {
      margin: 0,
      padding: 0,
      listStyle: 'none',
    },
  },
}));


export default function Home() {
  const classes = useStyles();

  return (
    <React.Fragment>
      <CssBaseline />
      <Header/ >
        <header className="App-header">
          <h1>FilmFinder</h1>
          <Button href="/SignUp" color="white" variant="contained">
            Sign Up
          </Button>
        </header>
        <div className="text">
          <h1>Trending</h1>
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
                    movieId={index}
                    title={movie.title}
                    yearReleased={movie.yearReleased}
                    imageUrl={movie.imageURL}
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