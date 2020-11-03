import Header from './components/Header';
import Drawer from './components/FilterDrawer';
import MovieCard from './components/MovieCard';

import React, { useEffect } from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import { useLocation } from "react-router-dom";
import Container from '@material-ui/core/Container';
import { makeStyles } from '@material-ui/core/styles';
import { useFetch, IfFulfilled, IfPending, IfRejected } from 'react-async';

const useStyles = makeStyles({
    right: {
        textAlign: "right",
    },
    center: {
        textAlign: "center",
      }
});

export default function SearchResults() {
    const classes = useStyles();
    const location = useLocation();
    const title = location.state.title;

    const requestOptions = {
        method: 'POST',
        headers: { 
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify({searchString: title})
    };
    const state = useFetch('/rest/search', requestOptions);

    useEffect(state.run, [title]);

    const handleResults = ({ movies }) => {
        const searchResults = movies.map(({ movieId, name, year, imageUrl }) => {
            return <MovieCard key={movieId} title={name} yearReleased={year} imageURL={imageUrl}/>
        });
        return searchResults;
    };

    return (
        <React.Fragment>
            <CssBaseline />
            <Header />
            <Container component="main" maxWidth="lg">
                <div className={classes.center}>
                    <h1>Results for "{title}"</h1>
                    <h1>
                    <IfRejected state={state}>Error...</IfRejected>
                    <IfPending state={state}>Loading...</IfPending>
                    <IfFulfilled state={state}>{handleResults}</IfFulfilled>
                    </h1>
                </div>
                <div className={classes.right}>
                    <Drawer title={title}/>
                </div>
            </Container>
        </React.Fragment>
    )
}