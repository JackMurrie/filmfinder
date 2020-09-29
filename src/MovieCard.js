import './css/Movie.css';

import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardMedia from '@material-ui/core/CardMedia';
import CardHeader from '@material-ui/core/CardHeader';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import Rating from '@material-ui/lab/Rating';
import Box from '@material-ui/core/Box';


export default function MovieCard(props) {
    return (
      <Card style={{width: 268, margin: 20}}>
        <CardActionArea href="/Movie">
          <CardMedia style={{height: 320}} image={props.imageURL}/>
          <CardContent>
          <div className='title'>
              <Typography  variant="h8" component="h9">
                {props.title} ({props.yearReleased})
              </Typography>
              <Box component="fieldset" mb={3} borderColor="transparent">
                  <Rating name="read-only" precision={0.5} value={4.5} readOnly/>
              </Box>
            </div>
          </CardContent>
        </CardActionArea>
      </Card>
    );
  }