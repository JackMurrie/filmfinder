import './css/Home.css';
import Header from './components/Header';
import MovieCard from './components/MovieCard';
import Footer from './components/Footer';

import React from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { fade, makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Switch from '@material-ui/core/Switch';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import { IfFulfilled, IfPending, IfRejected, useFetch } from 'react-async';

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
    height: "100vh",
  },
  space: {
    lineHeight: 10,
  }
}));


export default function Home({darkMode, handleThemeChange}) {
  const classes = useStyles();

  const requestOptions = {
    method: 'GET',
    headers: { 
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }
  };

  const getPopularMovies = useFetch('/rest/recommend', requestOptions, {defer: true});
  React.useEffect(getPopularMovies.run, []);

  return (
    <React.Fragment>
      <CssBaseline />
          <div className={classes.image}>
            <Header />
            <div className={classes.space}>
              <h1>FilmFinder</h1>
            </div>
            <FormControlLabel
              control={<Switch checked={darkMode} onChange={handleThemeChange} name="Dark Mode" color="primary"/>}
              label="Dark Mode"
              labelPlacement="bottom"
              />
          </div>
        <div class="title">
          <h2>Trending</h2>
          </div>
      {/* Display Movies */}
      <Container component="main" maxWidth="lg">
          <IfPending state={getPopularMovies}>Loading...</IfPending>
          <IfFulfilled state={getPopularMovies}>
            {data => {
              return(
                <div className="container">
                  {data.movies.map(({ movieId, name, year, genres, imageUrl }) => (
                    <MovieCard 
                      key={movieId}
                      movieId={movieId}
                      title={name}
                      imageUrl={imageUrl}
                    />
                  ))} 
                </div> 
              )
            }}
          </IfFulfilled>
          <IfRejected state={getPopularMovies}>
            Something went wrong.
          </IfRejected>
      </Container>
      <Footer />
    </React.Fragment>
  );
}