import React, { useEffect, useState } from 'react';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import { makeStyles } from '@material-ui/core/styles';
import Link from '@material-ui/core/Link';
import Switch from '@material-ui/core/Switch';
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
        width: 40,
        height: 40,
    },
}));

export default function BlacklistCard(props) {
    const classes = useStyles();

    const [blacklist, setBlacklist] = useState(true);

    const requestOptions = {
        method: 'GET',
        headers: { 
          'Accept': 'application/json',
          'Content-Type': 'application/json' 
        }
      };

    const updateBlacklist = useFetch('/rest/user/blacklist', requestOptions, { defer: true });

    const toggleBlacklist = (event) => {
        if (blacklist) {
        updateBlacklist.run({
          method: 'DELETE',
          body: JSON.stringify({ userId: props.userId })
        });
      } else {
        updateBlacklist.run({
          method: 'POST',
          body: JSON.stringify({ userId: props.userId })
        });
      };
      setBlacklist(blacklisted => !blacklisted);
    };

    return (
      <Paper style={{width: 1150, margin: 10, height: 50}}>
            <FormControlLabel
                control={<Switch checked={blacklist} onChange={toggleBlacklist} name="seen" color="primary"/>}
                className={classes.link}
            />
            <Link href={`/Movie/${props.userId}`} color="primary" className={classes.link} style={{ fontSize: '17px' } }> {props.first} {props.last}</Link>
        </Paper>
    );
}