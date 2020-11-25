import React from 'react';
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core/styles';
import { withStyles } from '@material-ui/core/styles';
import SwipeableDrawer from '@material-ui/core/SwipeableDrawer';
import Button from '@material-ui/core/Button';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import MovieIcon from '@material-ui/icons/Movie';
import ListItemText from '@material-ui/core/ListItemText';
import FavoriteBorderIcon from '@material-ui/icons/FavoriteBorder';
import TheatersIcon from '@material-ui/icons/Theaters';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import Checkbox from '@material-ui/core/Checkbox';
import FormGroup from '@material-ui/core/FormGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import { useHistory } from "react-router-dom";
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import InputBase from '@material-ui/core/InputBase';

const useStyles = makeStyles((theme) => ({
    list: {
        width: 250,
        backgroundColor: theme.palette.backgroundColor,
        color: theme.palette.text,
    },
    fullList: {
        width: 'auto',
    },
    text: {
      color: theme.palette.text,
    },
    resize:{
      fontSize:15,
    },
    textField: {
        width: 200,
        margin: 20,
    },
}));

export default function Drawer(props) {
    const classes = useStyles();
    const history = useHistory();
    const genres = [
        'Action',
        'Adventure',
        'Animation',
        'Comedy',
        'Crime',
        'Documentary',
        'Drama',
        'Family',
        'Fantasy',
        'History',
        'Horror',
        'Music',
        'Mystery',
        'Romance',
        'Science Fiction',
        'Thriller',
        'TV Movie',
        'War',
        'Western'
      ];

    const [genre, setGenre] = React.useState("Action");

    const handleRadioGenre = (event) => {
        setGenre(event.target.checked);
        props.handleGenre(event);
    };

    // Drawer States
    const [state, setState] = React.useState({
        right: false,
    });
  
    const toggleDrawer = (anchor, open) => (event) => {
      if (event && event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
        return;
      }
      if (state.right === true) {
        history.push(`/Browse`);
      }
      setState({ ...state, [anchor]: open });
    };
  
    const list = (anchor) => (
      <div
        className={clsx(classes.list, {
          [classes.fullList]: anchor === 'top' || anchor === 'bottom',
        })}
        role="presentation"
      >
        <Divider />
        <List>
            <ListItem>
              <ListItemIcon>{<TheatersIcon style={{fill: "secondary"}}/>}</ListItemIcon>
              <ListItemText primary={"Genre"} />
            </ListItem>
            <Divider />
            <FormControl component="fieldset">
                <RadioGroup  column onChange={handleRadioGenre}>
                    {genres.map((genre) => (
                        <FormControlLabel
                        value={genre}
                        control={<Radio
                            name={genre}
                            color="white"
                            />}
                        label={genre}
                        labelPlacement="start"
                        />
                    ))}
                </RadioGroup >
            </FormControl>
        </List>
      </div>
    );
  
    return (
          <React.Fragment key={'right'}>
            <Button variant="outlined" className={classes.text} onClick={toggleDrawer('right', true)}>{'Select Genre'}</Button>
            <SwipeableDrawer
              anchor={'right'}
              open={state['right']}
              onClose={toggleDrawer('right', false)}
              onOpen={toggleDrawer('right', true)}
            >
              {list('right')}
            </SwipeableDrawer>
          </React.Fragment>
    );
  }