import Header from '../components/Header';

import React from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';

const useStyles = makeStyles((theme) => ({
    paper: {
      marginTop: theme.spacing(15),
      display: 'flex',
      flexWrap: 'wrap',
      flexDirection: 'column',
      alignItems: 'center',
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
      },
    heading: {
    textAlign: "center",
    fontFamily: ["Montserrat", "sans-serif"],
    },
}));

export default function FilmPoker() {
    const classes = useStyles();

    return (
        <React.Fragment>
        <CssBaseline />
            <Header />
            <Container component="main" maxWidth="lg">
                <div className={classes.paper}>
                    <Typography component="h1" variant="h1" className={classes.heading}>
                    Film Poker
                    </Typography>
                    <div className={classes.paper}> </div>
                    <form className={classes.form} noValidate>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        size="large"
                        href="/CreateGame"
                    >
                        Create Game
                    </Button>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        size="large"
                        href="/JoinGame"
                    >
                        Join Game
                    </Button>
                    </form>
                </div>
                </Container>
        </React.Fragment>
    )
}