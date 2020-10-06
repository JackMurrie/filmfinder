import Header from './components/Header';

import Container from '@material-ui/core/Container';
import React from 'react';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';

const useStyles = makeStyles((theme) => ({
    paper: {
      marginTop: theme.spacing(8),
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
    },
    avatar: {
      margin: theme.spacing(1),
      backgroundColor: theme.palette.secondary.main,
    },
    form: {
      width: '100%', // Fix IE 11 issue.
      marginTop: theme.spacing(1),
    },
    submit: {
      margin: theme.spacing(3, 0, 2),
    },
  }));

export default  function AccountConf() {
    const classes = useStyles();
  
    return (
        <React.Fragment>
            <Header/ >
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <div className={classes.paper}>
                    <Typography component="h1" variant="h5">
                        Congratulations User!  
                    </Typography>
                    <Button href="/SignUp" color="white" variant="outlined">
                        User Dashboard 
                    </Button>
                </div>
            </Container>           
        </React.Fragment>
    );
}