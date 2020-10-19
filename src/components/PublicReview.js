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

export default function PublicReview() {
    const classes = useStyles();
  
    return (
        <Grid item xs={12}>
            <Paper className={classes.paper}>
                This is a random review. Magical, astounding filmmaking. This could've turned out terribly, 
                oh so terribly, but it didn't. It sure didn't. It's a near-perfect cinematic experience and adaptation, 
                delicately told right from the opening exposition dump that haunts the very marrow of my bones. 
                A world with so much beauty soon becomes tarnished by darkness. It only becomes clear around the 
                45 minute mark why Jackson was chosen for the project; his craft is sweeping and relentlessly classical. 
                Only a splatter and adventure geek could evoke such crumbling destruction and still sell every mythical, 
                at times overwrought, line of dialogue; he believes in it. The world was real to Tolkien, it was real 
                to Jackson, and it's real to the audience. 
                <div className="right">
                <Button href="/PublicProfile" color="primary">
                    User
                </Button>
                </div>
            </Paper>
        </Grid>
  
    );
  }