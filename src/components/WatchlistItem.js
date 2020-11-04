import React, { useEffect, useState } from 'react';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Favorite from '@material-ui/icons/Favorite';
import FavoriteBorder from '@material-ui/icons/FavoriteBorder';
import { makeStyles } from '@material-ui/core/styles';
import Link from '@material-ui/core/Link';
import Switch from '@material-ui/core/Switch';
import { useAsync, useFetch, IfFulfilled } from 'react-async';

const requestOptions = {
    method: 'GET',
    headers: { 
      'Accept': 'application/json',
      'Content-Type': 'application/json' 
    }
  };

const useStyles = makeStyles((theme) => ({
    paper: {
        padding: theme.spacing(2),
        display: 'flex',
        overflow: 'auto',
        flexDirection: 'column',
    },
    right: {
        textAlign: 'right',
    },
    link: {
        margin: theme.spacing(1, 1.5),
    },
    largeIcon: {
        width: 40,
        height: 40,
    },
}));

export default function WatchlistItem(props) {
    const classes = useStyles();

    const [watched, setWatched] = useState(true);

    const updateWatchlist = useFetch('/rest/user/watchedlist', requestOptions, { defer: true });

    const toggleWatchlist = (event) => {
        if (watched) {
            updateWatchlist.run({
            method: 'DELETE',
            body: JSON.stringify({ movieId: props.movieId})
          });
        } else {
            updateWatchlist.run({
            method: 'POST',
            body: JSON.stringify({ movieId: props.movieId})
          });
        };
    
        setWatched(watched => !watched);
      };

    return (
      <Card style={{width: 1150, margin: 10, backgroundColor: "DarkGrey"}}>
          <CardHeader
          title={<Link href={`/Movie/${props.movieId}`} color="primary" className={classes.link} style={{ fontSize: '30px' }}> {props.title} </Link>}
          action={
            <FormControlLabel
                control={<Switch checked={watched} onChange={toggleWatchlist} name="seen" color="primary"/>}
            />
          }>
          </CardHeader>
      </Card>
    );
}