import React from 'react';
import { Divider, Typography, Link } from '@material-ui/core';
import { makeStyles } from '@material-ui/core';
import Paper from '@material-ui/core/Paper';

const useStyles = makeStyles((theme) => ({
  root: {
    padding: theme.spacing(4),
    background: theme.palette.background.default,
    color: theme.palette.contrastText,
  },
  copyright: {
    marginTop: theme.spacing(2),
    marginBottom: theme.spacing(0.5)
  }
}));

export default function Footer() {
  const classes = useStyles();
  
  return (
    <div className={classes.root}>
      <Divider />
      <Typography className={classes.copyright} variant="body1">
        &copy; Lol i dunno 2020 |{' '} CSE Capstone Project
      </Typography>
      <Typography variant="caption">
        Powered by The Movie DB |{' '} This product uses the TMDb API but is not endorsed or certified by TMDb.
        <Paper style={{width: 268, margin: 20}}>
            <img src="https://www.themoviedb.org/assets/2/v4/logos/v2/blue_long_2-9665a76b1ae401a510ec1e0ca40ddcb3b0cfe45f1d51b77a308fea0845885648.svg" />
        </Paper>
      </Typography>
    </div>
  );
}