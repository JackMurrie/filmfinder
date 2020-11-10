import React from 'react';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import { makeStyles } from '@material-ui/core/styles';

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
            {props.text}
            <div className="right">
            <Button href={`/user/${props.user}`} color="secondary">
                TODO: Post date and user info
            </Button>
            </div>
          </Paper>
        </Grid>
  
    );
  }