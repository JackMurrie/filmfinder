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
import ListItemText from '@material-ui/core/ListItemText';
import FavoriteBorderIcon from '@material-ui/icons/FavoriteBorder';
import TheatersIcon from '@material-ui/icons/Theaters';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import Checkbox from '@material-ui/core/Checkbox';
import FormGroup from '@material-ui/core/FormGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';

const useStyles = makeStyles((theme) => ({
    list: {
        width: 250,
        backgroundColor: "#455166",
        color: "white",
    },
    fullList: {
        width: 'auto',
    },
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

export default function Drawer() {
    const classes = useStyles();
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
      ];
    
    const [checked, setChecked] = React.useState({
        Action: false,
        Adventure: false,
        Comedy: false,
        Crime: false,
        Documentary: false,
        Drama: false,
        Fantasy: false,
        Horro: false,
        Romance: false,
        SciFi: false,
        Thriller: false,
        Not_Seen: false,
        Not_Wishlist: false,
    });

    const handleChange = (event) => {
        setChecked({ ...state, [event.target.name]: event.target.checked });
    };

    const [state, setState] = React.useState({
        drawer: false,
      });
  
    const toggleDrawer = (anchor, open) => (event) => {
      if (event && event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
        return;
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
                    checked={state.Not_Seen}
                    onChange={handleChange}
                    name={"Seen"}
                    />
            </ListItem>
        </List>
        <Divider />
        <List>
            <ListItem button key={"Not_Wishlist"}>
              <ListItemIcon>{<FavoriteBorderIcon style={{fill: "white"}}/>}</ListItemIcon>
              <ListItemText primary={"Not in Wishlist"} />
              <WhiteCheckbox
                    checked={state.Not_Wishlist}
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
                            checked={state.genre}
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
      </div>
    );
  
    return (
          <React.Fragment key={'right'}>
            <Button onClick={toggleDrawer('right', true)}>{'Filter'}</Button>
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