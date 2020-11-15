import Header from './components/Header';
import Footer from './components/Footer';

import React from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import SentimentVeryDissatisfiedIcon from '@material-ui/icons/SentimentVeryDissatisfied';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';


const useStyles = makeStyles((theme) => ({
    center: {
        textAlign: "center",
        fontFamily: ["Montserrat", "sans-serif"],
    },
    number: {
        fontSize: "calc(30px + 15vmin)",
    },
    text: {
        fontSize: "calc(10px + 2vmin)",
        fontWeight: "bold",
    },
    subText: {
        fontSize: "calc(4px + 2vmin)",
        lineHeight: 3,
    },
    largeIcon: {
        width: 200,
        height: 200,
      },
}));

export default function Page404(props) {
    const classes = useStyles();
  
    return (
        <React.Fragment>
            <Header isLoggedIn={props.loggedIn} handleLogout={props.handleLogout}/>
            <Content />
            <Footer />
        </React.Fragment>
        

    );
}

function Content() {
  const classes = useStyles();

  return (

    <Container component="main" maxWidth="lg">
      <CssBaseline />
        <div className={classes.center}>
            <div className={classes.number}>
                404
            </div>
            <div className={classes.text}>
                Page Not Found
            </div>
            <div className={classes.subText}>
                The page you were looking for doesn't exist or an error occured
            </div>
            <SentimentVeryDissatisfiedIcon className={classes.largeIcon}/>
        </div>
    </Container>
  );
}