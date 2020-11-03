import React from 'react';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Favorite from '@material-ui/icons/Favorite';
import FavoriteBorder from '@material-ui/icons/FavoriteBorder';
import { makeStyles } from '@material-ui/core/styles';
import Link from '@material-ui/core/Link';


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

export default function WishlistItem(props) {
    const classes = useStyles();

    const [state, setState] = React.useState({
        seen: false,
        wishlist: false,
      });

    const handleChange = (event) => {
        setState({ ...state, [event.target.name]: event.target.checked });
  
        {/* Call POST to API */}
        const data = {
          [event.target.name]: event.target.checked
        };
        {console.log("POST: ", data)}
      };

    return (
      <Card style={{width: 1150, margin: 10}}>
          <CardHeader
          title={<Link href="/" color="primary" className={classes.link} style={{ fontSize: '30px' }}> {props.title} </Link>}
          action={
            <FormControlLabel
                control={<Checkbox checked={state.wishlist} 
                onChange={handleChange} icon={<FavoriteBorder className={classes.largeIcon}/>} 
                checkedIcon={<Favorite className={classes.largeIcon}/>} name="wishlist" />}
            />
          }>
          </CardHeader>
      </Card>
    );
}