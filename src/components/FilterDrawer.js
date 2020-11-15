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
import GradeIcon from '@material-ui/icons/Grade';
import ListItemText from '@material-ui/core/ListItemText';
import FavoriteBorderIcon from '@material-ui/icons/FavoriteBorder';
import TheatersIcon from '@material-ui/icons/Theaters';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import Checkbox from '@material-ui/core/Checkbox';
import FormGroup from '@material-ui/core/FormGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import Rating from '@material-ui/lab/Rating';
import { useHistory } from "react-router-dom";

const useStyles = makeStyles((theme) => ({
    list: {
        width: 250,
        backgroundColor: "#455166",
        color: "white",
    },
    fullList: {
        width: 'auto',
    },
    text: {
      color: theme.palette.text,
    }
}));

const WhiteCheckbox = withStyles({
    root: {
      color: "white",
      '&$checked': {
        color: "white",
      },
    },
    checked: {},
  })((props) => <Checkbox color="default" {...props} />);

export default function Drawer(props) {
    const classes = useStyles();
    const history = useHistory();
    const genres = [
        'Action',
        'Adventure',
        'Comedy',
        'Crime',
        'Documentary',
        'Drama',
        'Fantasy',
        'Horror',
        'Romance',
        'SciFI',
        'Thriller',
        'other',
      ];
    
    const [checked, setChecked] = React.useState({
        Action: false,
        Adventure: false,
        Comedy: false,
        Crime: false,
        Documentary: false,
        Drama: false,
        Fantasy: false,
        Horror: false,
        Romance: false,
        SciFi: false,
        Thriller: false,
        other: false,
        Not_Seen: false,
        Not_Wishlist: false,
        five_stars: false,
        four_stars: false,
        three_stars: false,
        two_stars: false,
        one_stars: false,
    });

    const handleChange = (event) => {
        setChecked({ ...checked, [event.target.name]: event.target.checked });
    };

    const [state, setState] = React.useState({
        right: false,
    });
  
    const toggleDrawer = (anchor, open) => (event) => {
      if (event && event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
        return;
      }
      if (state.right === true) {
        const data = {
          title: props.title,
          filters: checked,
        };
        history.push(`/SearchResults/${props.title}`, data);
      }
      setState({ ...state, [anchor]: open });
    };
  
    const list = (anchor) => (
      <div
        className={clsx(classes.list, {
          [classes.fullList]: anchor === 'top' || anchor === 'bottom',
        })}
        role="presentation"
        onKeyDown={toggleDrawer(anchor, false)}
      >
        <List>
            <ListItem button key={"Unseen"}>
              <ListItemIcon>{<VisibilityOffIcon style={{fill: "white"}}/>}</ListItemIcon>
              <ListItemText primary={"Unseen"} />
              <WhiteCheckbox
                    checked={checked.Not_Seen}
                    onChange={handleChange}
                    name={"Not_Seen"}
                    />
            </ListItem>
        </List>
        <Divider />
        <List>
            <ListItem button key={"Not_Wishlist"}>
              <ListItemIcon>{<FavoriteBorderIcon style={{fill: "white"}}/>}</ListItemIcon>
              <ListItemText primary={"Not in Wishlist"} />
              <WhiteCheckbox
                    checked={checked.Not_Wishlist}
                    onChange={handleChange}
                    name={"Not_Wishlist"}
                    />
            </ListItem>
        </List>
        <Divider />
        <List>
            <ListItem>
              <ListItemIcon>{<TheatersIcon style={{fill: "white"}}/>}</ListItemIcon>
              <ListItemText primary={"Genre"} />
            </ListItem>
            <FormControl component="fieldset">
                <FormGroup column>
                    {genres.map((genre) => (
                        <FormControlLabel
                        value={genre}
                        control={<WhiteCheckbox
                            checked={checked.[genre]}
                            onChange={handleChange}
                            name={genre}
                            />}
                        label={genre}
                        labelPlacement="start"
                        />
                    ))}
                </FormGroup>
            </FormControl>
        </List>
        <Divider />
        <List>
            <ListItem>
              <ListItemIcon>{<GradeIcon style={{fill: "white"}}/>}</ListItemIcon>
              <ListItemText primary={"Rating"} />
            </ListItem>
            <FormControl component="fieldset">
                <FormGroup column>
                        <FormControlLabel
                        control={<WhiteCheckbox
                            checked={checked.five_stars}
                            onChange={handleChange}
                            name={"five_stars"}
                            />}
                        label={<Rating value={5} readOnly size="small" className={classes.ratingColor}/>}
                        labelPlacement="start"
                        />
                        <FormControlLabel
                        control={<WhiteCheckbox
                            checked={checked.four_stars}
                            onChange={handleChange}
                            name={"four_stars"}
                            />}
                        label={<Rating value={4} readOnly size="small" className={classes.ratingColor}/>}
                        labelPlacement="start"
                        />
                        <FormControlLabel
                        control={<WhiteCheckbox
                            checked={checked.three_stars}
                            onChange={handleChange}
                            name={"three_stars"}
                            />}
                        label={<Rating value={3} readOnly size="small" className={classes.ratingColor}/>}
                        labelPlacement="start"
                        />
                        <FormControlLabel
                        control={<WhiteCheckbox
                            checked={checked.two_stars}
                            onChange={handleChange}
                            name={"two_stars"}
                            />}
                        label={<Rating value={2} readOnly size="small" className={classes.ratingColor}/>}
                        labelPlacement="start"
                        />
                        <FormControlLabel
                        control={<WhiteCheckbox
                            checked={checked.one_stars}
                            onChange={handleChange}
                            name={"one_stars"}
                            />}
                        label={<Rating value={1} readOnly size="small" className={classes.ratingColor}/>}
                        labelPlacement="start"
                        />
                </FormGroup>
            </FormControl>
        </List>
        <Divider />
      </div>
    );
  
    return (
          <React.Fragment key={'right'}>
            <Button className={classes.text} onClick={toggleDrawer('right', true)}>{'Filter'} </Button>
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