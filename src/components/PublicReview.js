import React from 'react';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import { makeStyles } from '@material-ui/core/styles';
import Rating from '@material-ui/lab/Rating';
import Box from '@material-ui/core/Box';

const useStyles = makeStyles((theme) => ({
    '@global': {
      ul: {
        margin: 0,
        padding: 0,
        listStyle: 'none',
      },
    },
      paper: {
        padding: theme.spacing(2),
        display: 'flex',
        overflow: 'auto',
        flexDirection: 'column',
      },
      right: {
        textAlign: 'right',
      },
    }));

export default function PublicReview(props) {
    const classes = useStyles();
  
    return (
        <Grid item xs={12}>
          <Paper className={classes.paper}>
            <div className="text">
              {props.text}
            </div>
            <Box component="fieldset" mb={-1} borderColor="transparent" marginTop={5}>
              <Rating name="read-only" precision={0.5} value={props.rating} readOnly/>
            </Box>
            <div className="right">
            <Button href={`/movie/${props.movieId}`} color="primary">
                {`${props.title}`}
            </Button>
            <Button href={`/user/${props.user.userId}`} color="secondary" disabled={!props.loggedIn}>
                {`${props.user.first} ${props.user.last}`}
            </Button>
            <Button disabled color="secondary">
                {props.postDate}
            </Button>
            </div>
          </Paper>
        </Grid>
  
    );
  }
