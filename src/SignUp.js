import StaticHeader from './components/StaticHeader';

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
import Paper from '@material-ui/core/Paper';

import { useHistory } from 'react-router-dom';

const useStyles = makeStyles((theme) => ({
    paper: {
      marginTop: theme.spacing(8),
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
    },
    form: {
      width: '100%', // Fix IE 11 issue.
      marginTop: theme.spacing(3),
    },
    submit: {
      margin: theme.spacing(3, 0, 2),
    },
    image: {
      backgroundImage: 'url(https://images.unsplash.com/photo-1503726876301-67e54d319b60?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1443&q=80)',
      backgroundRepeat: 'no-repeat',
      backgroundColor:
        theme.palette.type === 'light' ? theme.palette.grey[50] : theme.palette.grey[900],
      backgroundSize: 'cover',
      backgroundPosition: 'center',
    },
    root: {
      height: '100vh',
    },
  }));

export default function SignUp() {
    const classes = useStyles();
  
    return (
      <React.Fragment>
        <StaticHeader />
        <Grid container component="main" className={classes.root}> 
          <Grid item xs={false} sm={4} md={7} className={classes.image} />
          <SignUpScreen />
        </Grid>
      </React.Fragment>
    );
}


function SignUpScreen() {
    const classes = useStyles();
    const history = useHistory();

    const [state, setState] = React.useState({
      email: '',
      password: '',
      first_name: '',
      last_name: '',
    });
  
    const handleSubmit = (event) => {
      event.preventDefault();
      const data = {
        email: state.email,
        password: state.password,
        firstName: state.first_name,
        lastName: state.last_name,
      };
      
      const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
      };
  
      fetch('/rest/auth/register', requestOptions)
        .then(() => history.push('/Account'))
    };
  
    return (
      <Paper style={{width:460, height: 450, marginTop: 150, marginLeft: 150}} elevation={24}>
        <Container component="main" maxWidth="xs">
          <CssBaseline />
          <div className={classes.paper}>
            <Typography component="h1" variant="h5">
              Sign up
            </Typography>
            <form className={classes.form} noValidate>
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <TextField
                    autoComplete="fname"
                    name="first_name"
                    variant="outlined"
                    required
                    fullWidth
                    id="firstName"
                    label="First Name"
                    autoFocus
                    onChange={(event) => setState({ ...state, [event.target.name]: event.target.value })}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    variant="outlined"
                    required
                    fullWidth
                    id="lastName"
                    label="Last Name"
                    name="last_name"
                    autoComplete="lname"
                    onChange={(event) => setState({ ...state, [event.target.name]: event.target.value })}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    variant="outlined"
                    required
                    fullWidth
                    id="email"
                    label="Email Address"
                    name="email"
                    autoComplete="email"
                    onChange={(event) => setState({ ...state, [event.target.name]: event.target.value })}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    variant="outlined"
                    required
                    fullWidth
                    name="password"
                    label="Password"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                    onChange={(event) => setState({ ...state, [event.target.name]: event.target.value })}
                  />
                </Grid>
              </Grid>
              <Button
                type="submit"
                fullWidth
                variant="contained"
                color="primary"
                className={classes.submit}
                onClick={handleSubmit}
              >
                Sign Up
              </Button>
              <Grid container justify="flex-end">
                <Grid item>
                  <Link href="/Login" variant="body2">
                    Already have an account? Sign in
                  </Link>
                </Grid>
              </Grid>
            </form>
          </div>
        </Container>
        </Paper>
      );
    }