import Header from './components/Header';

import React from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import { useHistory } from 'react-router-dom';
import { useLocation } from "react-router-dom";


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

export default function ResetPassword(props) {
    const classes = useStyles();
  
    return (
        <React.Fragment>
            <Header isLoggedIn={props.loggedIn} handleLogout={props.handleLogout}/>
            <ResetPasswordScreen />
        </React.Fragment>
        

    );
}

function ResetPasswordScreen() {
  const classes = useStyles();
  const history = useHistory();
  const location = useLocation();
  const email = location.state.email;

  const [pass, setPass] = React.useState('');
  const [code, setCode] = React.useState('');
  const [wrong_credentials, setWrongCredentials] = React.useState(false);

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = {
      code: code,
      password: pass,
      email: email,
    };
    
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    };

    fetch('/rest/auth/reset_password', requestOptions)
      .then(response => {
        if (response.ok) {
          history.push('/NewPassConf');
        } else {
          setWrongCredentials(true);
        }
      });
  }

  return (

    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Typography component="h1" variant="h5">
          Reset Password
        </Typography>
        <form className={classes.form} noValidate>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="code"
            label="Code from email"
            name="pass1"
            autoFocus
            onChange={event => setCode(event.target.value)}
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="pass"
            label="New Password"
            name="pass2"
            autoFocus
            onChange={event => setPass(event.target.value)}
          />
          <Grid container justify="flex-end">
            <Grid item>
              <Link href="/ForgotPass" variant="body2">
                Resend Email
              </Link>
            </Grid>
          </Grid>
          { wrong_credentials &&
            <Typography variant="subtitle2" color="secondary">
              Sorry, that is not a valid code. Resend email?
            </Typography>
          }
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            onClick={handleSubmit}
          >
            Update 
          </Button>
        </form>
      </div>
    </Container>
  );
}