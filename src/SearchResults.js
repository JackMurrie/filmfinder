import Header from './components/Header';
import Drawer from './components/FilterDrawer';

import React from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import { useLocation } from "react-router-dom";
import Container from '@material-ui/core/Container';
import { makeStyles } from '@material-ui/core/styles';

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
    const filters = location.state.filters;

    return (
        <React.Fragment>
            <CssBaseline />
            <Header />
            <Container component="main" maxWidth="lg">
                <div className={classes.center}>
                    <h1>Results for "{title}"</h1>
                </div>
                <div className={classes.right}>
                    <Drawer title={title}/>
                </div>
                {/* Call get from API */}
                {console.log("GET movies title ", title)}
            </Container>
        </React.Fragment>
    )
}