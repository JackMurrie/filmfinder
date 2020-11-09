import React, { useEffect, useState } from 'react';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import { makeStyles } from '@material-ui/core/styles';
import Link from '@material-ui/core/Link';
import { useAsync, useFetch, IfFulfilled } from 'react-async';
import Paper from '@material-ui/core/Paper';
import AddIcon from '@material-ui/icons/Add';
import RemoveIcon from '@material-ui/icons/Remove';
import { useHistory } from "react-router-dom";
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardHeader from '@material-ui/core/CardHeader';
import CardActions from '@material-ui/core/CardActions';
import CardMedia from '@material-ui/core/CardMedia';
import Rating from '@material-ui/lab/Rating';
import Box from '@material-ui/core/Box';

const requestOptions = {
    method: 'GET',
    headers: { 
      'Accept': 'application/json',
      'Content-Type': 'application/json' 
    }
  };

const useStyles = makeStyles((theme) => ({
    largeIcon: {
        width: 40,
        height: 40,
    },
    title: {
      textAlign: "center",
      fontSize: "calc(6px + 2vmin)",
      fontFamily: ['Montserrat', "sans-serif"],
    }
}));

export default function PokerCard(props) {
    const classes = useStyles();
    const history = useHistory();

    const [added, setAdded] = useState(false);

    const updateMovies = useFetch('/rest/', requestOptions, { defer: true }); // /rest/filmPoker/add remove selection

    const toggleAdded = (event) => {
        if (added) {
          updateMovies.run({
            method: 'DELETE',
            body: JSON.stringify({ movieId: props.movieId})
          });
        } else {
          updateMovies.run({
            method: 'POST',
            body: JSON.stringify({ movieId: props.movieId})
          });
        };
    
        setAdded(added => !added);
      };

    return (
      <Card style={{width: 255, margin: 20}} elevation={24}>
      <CardActionArea onClick = {toggleAdded}>
        <CardMedia style={{height: 320}} image={props.imageUrl}/>
        <CardContent>
        <div className="movieTitle">
                {props.title}
          </div>
        <div className='right'>
            <FormControlLabel
            control={<Checkbox checked={added} 
            icon={<AddIcon className={classes.largeIcon}/>} 
            checkedIcon={<RemoveIcon className={classes.largeIcon}/>} name="movie"/>}
            />
        </div>
        </CardContent>
      </CardActionArea>
    </Card>
    );
}