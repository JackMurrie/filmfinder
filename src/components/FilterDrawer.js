import React from 'react';
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core/styles';
import SwipeableDrawer from '@material-ui/core/SwipeableDrawer';
import Button from '@material-ui/core/Button';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import GradeIcon from '@material-ui/icons/Grade';
import RateReviewIcon from '@material-ui/icons/RateReview';

const useStyles = makeStyles({
    list: {
        width: 250,
    },
    fullList: {
        width: 'auto',
    },
});

export default function Drawer() {
    const classes = useStyles();
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
        onClick={toggleDrawer(anchor, false)}
        onKeyDown={toggleDrawer(anchor, false)}
      >
        <List>
            <ListItem button key={"Rating"}>
              <ListItemIcon>{<GradeIcon />}</ListItemIcon>
              <ListItemText primary={"Rating"} />
            </ListItem>
        </List>
        <Divider />
        <List>
            <ListItem button key={"Number of Reviews"}>
              <ListItemIcon>{<RateReviewIcon />}</ListItemIcon>
              <ListItemText primary={"Number of Reviews"} />
            </ListItem>
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