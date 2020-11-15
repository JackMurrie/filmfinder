import React, { useEffect, useState } from 'react';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Favorite from '@material-ui/icons/Favorite';
import FavoriteBorder from '@material-ui/icons/FavoriteBorder';
import { makeStyles } from '@material-ui/core/styles';
import Link from '@material-ui/core/Link';
import { useAsync, useFetch, IfFulfilled } from 'react-async';
import Paper from '@material-ui/core/Paper';

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
        color: theme.palette.text.primary,
    },
    largeIcon: {
        width: 30,
        height: 30,
    },
}));

export default function WishlistItem(props) {
    const classes = useStyles();

    const [wished, setWished] = useState(true);

    const updateWishlist = useFetch('/rest/user/wishlist', requestOptions, { defer: true });

    const toggleWishlist = (event) => {
        if (wished) {
          updateWishlist.run({
            method: 'DELETE',
            body: JSON.stringify({ movieId: props.movieId})
          });
        } else {
          updateWishlist.run({
            method: 'POST',
            body: JSON.stringify({ movieId: props.movieId})
          });
        };
    
        setWished(wished => !wished);
      };

    return (
      <Paper style={{width: 1150, margin: 10, height: 60}}>
            <FormControlLabel
                control={<Checkbox checked={wished} 
                onChange={toggleWishlist} icon={<FavoriteBorder className={classes.largeIcon}/>} 
                checkedIcon={<Favorite className={classes.largeIcon}/>} name="wishlist" />}
                className={classes.link}
            />
            <Link href={`/Movie/${props.movieId}`} color="primary" className={classes.link} style={{ fontSize: '17px' } }> {props.title} </Link>
          </Paper>
    );
}