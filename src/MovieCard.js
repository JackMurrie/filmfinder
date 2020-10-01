import './css/Movie.css';

import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardMedia from '@material-ui/core/CardMedia';
import CardHeader from '@material-ui/core/CardHeader';
import Typography from '@material-ui/core/Typography';
import Rating from '@material-ui/lab/Rating';
import Box from '@material-ui/core/Box';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Favorite from '@material-ui/icons/Favorite';
import FavoriteBorder from '@material-ui/icons/FavoriteBorder';


export default function MovieCard(props) {
    return (
      <Card style={{width: 268, margin: 20}}>
        <CardHeader
            action={
            <div className="right">
              <FormControlLabel
                  control={<Checkbox icon={<FavoriteBorder />} checkedIcon={<Favorite />} name="wishlist" />}
                />
            </div>}
            title={props.title}
            subheader={props.yearReleased}
            />
        <CardActionArea href="/Movie">
          <CardMedia style={{height: 320}} image={props.imageURL}/>
          <CardContent>
            <div className='title'>
              <Box component="fieldset" mb={3} borderColor="transparent">
                  <Rating name="read-only" precision={0.5} value={4.5} readOnly size="large"/>
              </Box>
            </div>
          </CardContent>
        </CardActionArea>
      </Card>
    );
  }