import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import PersonIcon from '@material-ui/icons/Person';
import Typography from '@material-ui/core/Typography';

const useStyles = makeStyles((theme) => ({
    paperWaiting: {
        margin: theme.spacing(5),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        flexWrap: 'wrap',
        height: 165,
        width: 165,
    },
    paperReady: {
        margin: theme.spacing(5),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        flexWrap: 'wrap',
        height: 165,
        width: 165,
        backgroundColor: 'green'
    },
    largeIcon: {
        width: 125,
        height: 125,
    }
}));

export default function PersonCard(props) {
    const classes = useStyles();
    
    const paperStyle = props.ready ? classes.paperReady : classes.paperWaiting;

    return (
        <Paper className={paperStyle}>
            <PersonIcon
                className={classes.largeIcon}
            />
            <Typography component={'span'}>
                {props.name}
            </Typography>
        </Paper>
    );
}