import Header from './components/Header';
import Drawer from './components/FilterDrawer';
import MovieCard from './components/MovieCard';
import './css/Home.css';

import React, { useEffect } from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import { useLocation } from "react-router-dom";
import Container from '@material-ui/core/Container';
import { makeStyles } from '@material-ui/core/styles';
import { useFetch, IfFulfilled, IfPending, IfRejected } from 'react-async';
import Backdrop from '@material-ui/core/Backdrop';
import CircularProgress from '@material-ui/core/CircularProgress';
import SentimentVeryDissatisfiedIcon from '@material-ui/icons/SentimentVeryDissatisfied';

const useStyles = makeStyles({
    right: {
        textAlign: "right",
    },
    center: {
        textAlign: "center",
        fontFamily: ["Montserrat", "sans-serif"],
    },
    background: {
        backgroundColor: "	#282828",
      },
});

export default function SearchResults(props) {
    const classes = useStyles();
    const location = useLocation();
    const title = location.pathname.split('/').pop();

    const requestOptions = {
        method: 'POST',
        headers: { 
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify({title: title, genre: null, director: null, limit: 12})
    };
    const state = useFetch('/rest/search', requestOptions);

    useEffect(state.run, [title]);

    const handleResults = ({ movies }) => {
        if (movies[0] === undefined) {
            return (
                <Container component="main" maxWidth="lg">
                <div className={classes.center}>
                    <h2>No results found</h2>
                    <SentimentVeryDissatisfiedIcon />
                </div>
                </Container>
            );
        }
        else {
            const searchResults = movies.map(({ movieId, name, year, imageUrl, averageRating }) => {
                return <MovieCard key={movieId} movieId={movieId} title={name} yearReleased={year} imageUrl={imageUrl} rating={averageRating}/>
            });
            return searchResults;
        }
    };

    return (
        <React.Fragment>
            <CssBaseline />
            <Header isLoggedIn={props.loggedIn} handleLogout={props.handleLogout}/>
            <Container component="main" maxWidth="lg">
                <div className={classes.center}>
                    <h1>Results for "{title}"</h1>
                    <div className="container">
                    <IfRejected state={state}>Error...</IfRejected>
                    <IfPending state={state}>
                        <Backdrop className={classes.backdrop} open={true}>
                            <CircularProgress color="inherit" />
                        </Backdrop>
                    </IfPending>
                    <IfFulfilled state={state}>{handleResults}</IfFulfilled>
                    </div>
                </div>
            </Container>
        </React.Fragment>
    )
}