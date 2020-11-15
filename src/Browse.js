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
import InputBase from '@material-ui/core/InputBase';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';

const useStyles = makeStyles((theme) => ({
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
    link: {
        margin: theme.spacing(1, 1.5),
        color: `${theme.palette.text.primary}`,
    },
}));

export default function Browse(props) {
    const classes = useStyles();
    const location = useLocation();
    const [genre, setGenre] = React.useState(null);
    const [director, setDirector] = React.useState("");

    const handleGenre = React.useCallback( 
        event => {
            setGenre(event.target.value);
            setDirector(null);
        },
        [genre]
    );

    const handleDirector = (event) => {
        setDirector(event.target.value)
        setGenre(null);
    };

    console.log(genre, director);

    const requestOptions = {
        method: 'POST',
        headers: { 
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify({title: null, genre: genre, director: director, limit: 12})
    };
    const state = useFetch('/rest/search', requestOptions);

    useEffect(state.run, [director, genre]);

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
                    <h1>Browsing by {genre} {director}</h1>
                    <div className={classes.right}>
                        <Drawer handleGenre={handleGenre}/>
                        <TextField
                            variant="outlined"
                            margin="normal"
                            fullWidth
                            id="search"
                            placeholder="Search Director"
                            name="search"
                            size='large'
                            onChange={handleDirector}
                            InputProps={{
                                classes: {
                                    input: classes.resize,
                                },
                                }}
                                className={classes.textField}
                        />
                    </div>
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