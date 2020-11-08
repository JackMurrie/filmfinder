import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardMedia from '@material-ui/core/CardMedia';
import Rating from '@material-ui/lab/Rating';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import '../css/Home.css';
import React from 'react';
import { useHistory } from "react-router-dom";


export default function MovieCard(props) {
  const history = useHistory();

  const handleClick = (event) => {
    event.preventDefault();
    const data = {
      title: props.title,
      imageUrl: props.imageUrl,
    };
    history.push(`/Movie/${props.movieId}`, data);
  }; 

  return (
    <Card style={{width: 268, margin: 20, backgroundColor: "#404040"}} elevation={24}>
      <CardActionArea onClick = {handleClick}>
        <CardMedia style={{height: 320}} image={props.imageUrl}/>
        <CardContent>
          <div className='title'>
            <Box component="fieldset" mb={-2} borderColor="transparent">
              <Rating name="read-only" precision={0.5} value={4.5} readOnly size="large"/>
            </Box>
          </div>
          <div className="movieTitle">
                {props.title}
          </div>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}