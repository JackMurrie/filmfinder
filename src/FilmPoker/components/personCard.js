import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import PersonIcon from '@material-ui/icons/Person';
import Typography from '@material-ui/core/Typography';

const useStyles = makeStyles((theme) => ({
    paper: {
      margin: theme.spacing(5),
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      flexWrap: "wrap",
      height: 165,
      width: 165,
    },
    largeIcon: {
        width: 125,
        height: 125,
    },
}));

export default function PersonCard(props) {
    const classes = useStyles();

    return (
        <Paper className={classes.paper}>
            <PersonIcon 
                className={classes.largeIcon}
            />
            <Typography component={'span'}>
                {props.name}
            </Typography>
        </Paper>
    );
}