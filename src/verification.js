import Header from './components/Header';

import React from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';


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

export default function Verification(props) {
    const classes = useStyles();
  
    return (
        <React.Fragment>
            <Header isLoggedIn={props.loggedIn} handleLogout={props.handleLogout}/>
            <VerificationScreen/ >
        </React.Fragment>
        

    );
}

function VerificationScreen() {
  const classes = useStyles();

  return (

    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Typography component="h1" variant="h5">
          Verification
        </Typography>
        <Typography component="h8" variant="h7">
          Enter the code from your email below 
        </Typography>
        <form className={classes.form} noValidate>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="code"
            label="CODE"
            name="code"
            autoFocus
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            href="/ResetPassword"
          >
            Submit
          </Button>
          <Grid container justify="flex-end">
                <Grid item>
                  <Link href="/ForgotPass" variant="body2">
                    Resend Email
                  </Link>
                </Grid>
              </Grid>
        </form>
      </div>
    </Container>
  );
}