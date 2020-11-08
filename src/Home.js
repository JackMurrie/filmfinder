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
import Grid from '@material-ui/core/Grid';
import Async from 'react-async';

const useStyles = makeStyles((theme) => ({
  '@global': {
    ul: {
      margin: 0,
      padding: 0,
      listStyle: 'none', 
    },
  },
  background: {
    backgroundColor: theme.palette.background.default,
  },
  header: {
    backgroundImage:
      theme.palette.type === 'light' ? "white" : "black",
  },
  image: {
    backgroundImage:
      theme.palette.type === 'dark' 
      ? "url(https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80)" 
      : "url(https://images.unsplash.com/photo-1521967906867-14ec9d64bee8?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80)",
    alignItems: "center",
    justifyContent: "center",
    fontSize: "calc(10px + 2vmin)",
    fontFamily: ['Montserrat', "sans-serif"],
    color: "white",
    backgroundPosition: 'center',
    backgroundSize: 'cover',
    textAlign: "center",
    height: 900,
  },
  space: {
    lineHeight: 10,
  }
}));


export default function Home() {
  const classes = useStyles();

  return (
    <React.Fragment>
      <CssBaseline />
          <div className={classes.image}>
            <Header />
            <div className={classes.space}>
              <h1>FilmFinder</h1>
            </div>
          </div>
        <div class="title">
          <h2>Trending</h2>
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